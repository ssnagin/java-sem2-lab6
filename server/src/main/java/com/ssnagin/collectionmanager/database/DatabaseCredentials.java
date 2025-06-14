package com.ssnagin.collectionmanager.database;


import com.ssnagin.collectionmanager.commands.commands.CommandLogin;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.cli.*;

@Data
@AllArgsConstructor
public class DatabaseCredentials {
    String username;
    String password;

    String host;
    Integer port;
    String databaseName;

    public DatabaseCredentials(String[] args) throws ParseException {
        Options options = new Options();

        CommandLine commandLine;

        options.addOption(Option.builder()
                .longOpt("username")
                .hasArg()
                .desc("Database username")
                .required(false)
                .build());

        options.addOption(Option.builder()
                .longOpt("password")
                .hasArg()
                .desc("Database password")
                .required(false)
                .build());

        options.addOption(Option.builder()
                .longOpt("host")
                .hasArg()
                .desc("Database host")
                .required(false)
                .build());

        options.addOption(Option.builder()
                .longOpt("port")
                .hasArg()
                .type(Integer.class)
                .desc("Database port")
                .required(false)
                .build());

        options.addOption(Option.builder()
                .longOpt("databaseName")
                .hasArg()
                .desc("Database name")
                .required(false)
                .build());

        commandLine = new DefaultParser().parse(options, args);

        setUsername(commandLine.getOptionValue("username", ""));
        setPassword(commandLine.getOptionValue("password", ""));
        setHost(commandLine.getOptionValue("host", "localhost"));
        setPort(Integer.valueOf(commandLine.getOptionValue("port", "5432")));
        setDatabaseName(commandLine.getOptionValue("databaseName", "database"));
    }
}
