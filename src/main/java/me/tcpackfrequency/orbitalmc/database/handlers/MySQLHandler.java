package me.tcpackfrequency.orbitalmc.database.handlers;

import com.zaxxer.hikari.HikariDataSource;
import me.tcpackfrequency.orbitalmc.OrbitalMC;
import me.tcpackfrequency.orbitalmc.managers.ProfileManager;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class MySQLHandler implements Handler {

    private String Host, Database, Username, Password;
    private int Port;

    private HikariDataSource hikari = new HikariDataSource();

    private OrbitalMC pl;

    private ProfileManager pm;

    private Connection connection;

    @Override
    public void init() {
        PreparedStatement ps = null;
        try {
            this.connection = hikari.getConnection();
            ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Profile(UUID varchar(36), money FLOAT)");
            ps.executeUpdate();

        } catch(SQLException ex){
            ex.printStackTrace();
        } finally {
            System.out.println("Made table successfully!");
            try {
                this.close(hikari.getConnection(), ps, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    private void close(Connection con, PreparedStatement ps, ResultSet rs){
        if (con != null) try { con.close(); } catch (SQLException ignored) {}
        if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
        if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    }


    @Override
    public void saveStats(UUID u) {
        PreparedStatement ps;
        try {
            ps = hikari.getConnection().prepareStatement("INSERT into Profile(UUID, money) VALUES(?, ?) ON DUPLICATE KEY UPDATE money = ?;");
            ps.setString(1, String.valueOf(u));
            ps.setDouble(2, pm.getOrCreateProfile(u).getMoney());
            ps.setDouble(3, pm.getOrCreateProfile(u).getMoney());

            ps.executeUpdate();
            System.out.println("Successfully updated profile!");
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void stopDB() {
        if(!this.hikari.isClosed()) {
            this.hikari.close();
        }
    }
}
