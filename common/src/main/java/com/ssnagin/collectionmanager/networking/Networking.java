package com.ssnagin.collectionmanager.networking;

import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;
import com.ssnagin.collectionmanager.networking.serlialization.DataStream;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Networking {
    private static final int BUFFER_SIZE = 65536;

    @Setter
    @Getter
    private int connectionTimeout;
    @Setter
    private DatagramChannel datagramChannel;
    @Getter
    private InetAddress inetAddress;
    @Getter
    @Setter
    private int port;

    private Selector selector;
    private ExecutorService executorService;
    private volatile boolean running;

    public Networking() throws IOException {
        this.datagramChannel = DatagramChannel.open();
        this.datagramChannel.configureBlocking(false);
        this.selector = Selector.open();
        this.executorService = Executors.newSingleThreadExecutor();
        this.running = true;
    }

    public Networking(String host, int port) throws IOException {
        this(host, port, 3000);
    }

    public Networking(String host, int port, int connectionTimeout) throws IOException {
        this();
        setInetAddress(host);
        setPort(port);
        setConnectionTimeout(connectionTimeout);
    }

    public void setInetAddress(String address) throws IOException {
        setInetAddress(InetAddress.getByName(address));
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public void sendClientRequest(ClientRequest request, ResponseHandler handler) throws IOException {
        byte[] requestData = DataStream.serialize(request);
        ByteBuffer buffer = ByteBuffer.wrap(requestData);

        SocketAddress target = new InetSocketAddress(inetAddress, port);
        datagramChannel.send(buffer, target);

        // Register for read operations to get response
        datagramChannel.register(selector, SelectionKey.OP_READ, handler);

        // Start selector thread if not already running
        if (!executorService.isShutdown()) {
            executorService.submit(this::selectorLoop);
        }
    }

    public void sendServerResponse(ServerResponse serverResponse, SocketAddress clientAddress) throws IOException {
        byte[] responseData = DataStream.serialize(serverResponse);
        ByteBuffer buffer = ByteBuffer.wrap(responseData);
        datagramChannel.send(buffer, clientAddress);
    }

    private void selectorLoop() {
        while (running) {
            try {
                if (selector.select(connectionTimeout) > 0) {
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        keyIterator.remove();

                        if (key.isReadable()) {
                            handleRead(key);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleRead(SelectionKey key) {
        DatagramChannel channel = (DatagramChannel) key.channel();
        ResponseHandler handler = (ResponseHandler) key.attachment();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        try {
            SocketAddress sender = channel.receive(buffer);
            if (sender != null) {
                buffer.flip();
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);

                ServerResponse response = DataStream.deserialize(data);
                handler.handleResponse(response);

                // Unregister after receiving response
                key.cancel();
            }
        } catch (IOException | ClassNotFoundException e) {
            handler.handleError(e);
            key.cancel();
        }
    }

    public void close() {
        running = false;
        executorService.shutdown();
        if (datagramChannel != null && datagramChannel.isOpen()) {
            try {
                datagramChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (selector != null && selector.isOpen()) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FunctionalInterface
    public interface ResponseHandler {
        void handleResponse(ServerResponse response);
        default void handleError(Exception e) {
            e.printStackTrace();
        }
    }
}