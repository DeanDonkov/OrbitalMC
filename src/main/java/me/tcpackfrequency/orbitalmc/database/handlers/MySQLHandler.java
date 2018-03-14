package me.tcpackfrequency.orbitalmc.database.handlers;

import com.zaxxer.hikari.HikariDataSource;
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

    private Connection connection;


    private HikariDataSource hikari;

    private ProfileManager pm = new ProfileManager();

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
            this.close(this.connection, ps, null);
        }
    }

    @Override
    public void connect(ConfigurationSection cs) {
        this.Host = cs.getString("Host");
        this.Database = cs.getString("Database");
        this.Username = cs.getString("Username");
        this.Password = cs.getString("Password");
        this.Port = cs.getInt("Port");
        //HikariConfig config = new HikariConfig();
        //config.setDataSourceClassName("com.microsoft.sqlserver.jdbc.SQLServerDataSource");
        //config.setJdbcUrl("jdbc:mysql//" + this.Host + ":" + this.Port + "/" + this.Database);
        // config.setDriverClassName("com.mysql.jdbc.Driver");
        //config.setUsername(this.Username);
        //config.setPassword(this.Password);
        //hikari = new HikariDataSource(config);

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
    public void stopDB() {
        if(hikari != null && !hikari.isClosed()){
            hikari.close();
        }
    }

    public Connection getConnection() throws SQLException {
        return hikari.getConnection();
    }

    @Override
    public void saveStats(UUID u) {
        // do in a while.
    }
}
