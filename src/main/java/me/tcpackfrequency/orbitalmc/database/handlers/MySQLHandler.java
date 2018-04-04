package me.tcpackfrequency.orbitalmc.database.handlers;

import com.zaxxer.hikari.HikariDataSource;
import me.tcpackfrequency.orbitalmc.managers.ProfileManager;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.*;
import java.util.HashSet;
import java.util.UUID;


public class MySQLHandler implements Handler {

    private String Host, Database, Username, Password;
    private int Port;

    private HikariDataSource hikari = new HikariDataSource();

    private ProfileManager pm = new ProfileManager();


    @Override
    public void init() {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = hikari.getConnection();
            ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Profile(UUID varchar(36), money FLOAT)");

            ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Permissions(`UUID` varchar(36) NOT NULL, FOREIGN KEY(`UUID`) REFERENCES Profile(`UUID`), permissions varchar(100), UNIQUE KEY `UUID` (`UUID`, Permissions))");
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("CRITICAL ERROR: ORBITAL-MC CORE COULD NOT CONNECT TO THE MYSQL TABLE! PLEASE ENSURE IT IS SET UP CORRECTLY!");
                this.close(conn, ps, null);
        }
    }

    @Override
    public void connect(ConfigurationSection cs) {
        this.Host = cs.getString("Host");
        this.Database = cs.getString("Database");
        this.Username = cs.getString("Username");
        this.Password = cs.getString("Password");
        this.Port = cs.getInt("Port");

        hikari = new HikariDataSource();
        hikari.setMaximumPoolSize(10);
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", this.Host);
        hikari.addDataSourceProperty("port", this.Port);
        hikari.addDataSourceProperty("databaseName", this.Database);
        hikari.addDataSourceProperty("user", this.Username);
        hikari.addDataSourceProperty("password", this.Password);
    }

    private void close(Connection con, PreparedStatement ps, ResultSet rs) {
        if (con != null) try {
            con.close();
        } catch (SQLException ignored) {
        }
        if (ps != null) try {
            ps.close();
        } catch (SQLException ignored) {
        }
        if (rs != null) try {
            rs.close();
        } catch (SQLException ignored) {
        }
    }


    @Override
    public void saveStats(UUID u) {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = hikari.getConnection();
            ps = conn.prepareStatement("INSERT into Profile(UUID, money) VALUES(?, ?) ON DUPLICATE KEY UPDATE money = ?;");
            ps.setString(1, String.valueOf(u));
            ps.setDouble(2, pm.getOrCreateProfile(u).getMoney());
            ps.setDouble(3, pm.getOrCreateProfile(u).getMoney());
            ps.executeUpdate();
            System.out.println("Successfully updated profile!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                this.close(conn, ps, null);
        }
    }

    @Override
    public void stopDB() {
        if (!this.hikari.isClosed()) {
            this.hikari.close();
        }
    }

    @Override
    public HashSet<String> getPermissions(UUID u) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = hikari.getConnection();
            ps = conn.prepareStatement("SELECT * FROM Permissions WHERE player = ?");
            ps.setString(1, String.valueOf(u));
            rs = ps.executeQuery();

            HashSet<String> permissions = new HashSet<>();
            while (rs.next()) {
                permissions.add(rs.getString("permissions"));
            }
            return permissions;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                this.close(conn, ps, rs);
        }
        return null;
    }

    @Override
    public void addPermission(String permission, UUID u) {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = hikari.getConnection();
            ps = conn.prepareStatement("INSERT into Permissions(`UUID`, permissions) VALUES(?,?) ON DUPLICATE KEY UPDATE permissions = permissions");
            ps.setString(1, String.valueOf(u));
            ps.setString(2, permission);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                this.close(conn, ps, null);
        }
    }

    @Override
    public void addPermission(HashSet<String> permissions, UUID u) {
        PreparedStatement batch = null;
        Connection conn = null;
        try {
            conn = hikari.getConnection();
            batch = conn.prepareStatement("INSERT into Permissions(`UUID`, permissions) VALUES(?,?) ON DUPLICATE KEY UPDATE permissions = permissions");
            for (String permission : permissions) {
                batch.setString(1, String.valueOf(u));
                batch.setString(2, permission);
                batch.addBatch();
            }
            batch.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.close(conn, batch, null);
        }
    }
}
