package me.tcpackfrequency.orbitalmc.database.handlers;

import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLHandler implements Handler {

    private String Host, Database, Username, Password;
    private int Port;

    private Connection connection;

    @Override
    public void init(ConfigurationSection cs) {
        this.Host = cs.getString("Host");
        this.Database = cs.getString("Database");
        this.Username = cs.getString("Username");
        this.Password = cs.getString("Password");
        this.Port = cs.getInt("Port");
    }

    @Override
    public void connect() {
        try {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            synchronized (this) {
                if (connection != null && !connection.isClosed()) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + this.Host + ":" + this.Port + "/" + this.Database, this.Username, this.Password);
                System.out.println("Connected successfully!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
