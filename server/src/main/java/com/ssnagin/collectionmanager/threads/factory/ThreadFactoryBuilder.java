package com.ssnagin.collectionmanager.threads.factory;


import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadFactoryBuilder {
    private String namePrefix;
    private boolean daemon = false;

    public ThreadFactoryBuilder setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
        return this;
    }

    public ThreadFactoryBuilder setDaemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    public ThreadFactory build() {
        return new ThreadFactory() {
            private final AtomicInteger count = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName(namePrefix + count.getAndIncrement());
                thread.setDaemon(daemon);
                return thread;
            }
        };
    }
}
