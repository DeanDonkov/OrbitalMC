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


    /* TODO: TAKES CARE OF INITIALISATION OF THE DATABASE.*/
    @Override
    public void init() {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = hikari.getConnection();
            /* TODO: THIS IS WHERE THE PLAYER INFORMATION IS STORED. */
            ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Profile(int id NOT NULL PRIMARY AUTO_INCREMENT, UUID varchar(36), money FLOAT)");
            /*TODO: THIS IS WHERE THE PERMISSIONS ARE STORED */
            ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Permissions(int id NOT NULL PRIMARY AUTO_INCREMENT, permission (VARCHAR 100)");
            //ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Permissions(`UUID` varchar(36) NOT NULL, FOREIGN KEY(`UUID`) REFERENCES Profile(`UUID`), permissions varchar(100), UNIQUE KEY `UUID` (`UUID`, Permissions))");
            /* TODO: THIS IS WHERE THE GROUPS ARE STORED */
            ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Group(int id NOT NULL PRIMARY AUTO_INCREMENT, name (varchar 100)");
            /* TODO: THIS IS WHERE THE GROUPS` PERMISSIONS ARE STORED */
            ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS GroupPermissions(int id NOT NULL PRIMARY FOREIGN KEY(id) REFERENCES Group(id), permission(varchar 100)");
            /* TODO: THIS IS WHERE THE PLAYER PERMISSIONS ARE STORED */
            ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS ProfilePerms(int id NOT NULL PRIMARY FOREIGN KEY(id) REFERENCES Profile(id), int permission NOT NULL PRIMARY FOREIGN KEY(permission) REFERENCES Permissions(permission)");
            ps.executeUpdate();


        } catch (SQLException ex) {
            System.out.println("CRITICAL ERROR: ORBITAL-MC CORE COULD NOT CONNECT TO THE MYSQL TABLE! PLEASE ENSURE IT IS SET UP CORRECTLY!");
                this.close(conn, ps, null);
        }
    }

    // TODO: CONNECTS TO THE DATABASE.
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

    // TODO: CLOSES UNNECESSARY RESOURCES.
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



    // TODO: SAVES STATS
    @Override
    public void saveStats(UUID u) {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = hikari.getConnection();
            ps = conn.prepareStatement("INSERT into Profile(id, UUID, money) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE money = ?;");
            ps.setInt(1, pm.getOrCreateProfile(u).getId());
            ps.setString(2, String.valueOf(u));
            ps.setDouble(3, pm.getOrCreateProfile(u).getMoney());
            ps.setDouble(4, pm.getOrCreateProfile(u).getMoney());
            ps.executeUpdate();
            System.out.println("Successfully updated profile!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                this.close(conn, ps, null);
        }
    }

    // TODO: STOPS THE DATABASE
    @Override
    public void stopDB() {
        if (!this.hikari.isClosed()) {
            this.hikari.close();
        }
    }

    // TODO: GETS ID BY USER
    @Override
    public int getUserId(UUID u) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = hikari.getConnection();
            ps = conn.prepareStatement("SELECT id FROM Profile WHERE `UUID` = ?");
            ps.setString(1, String.valueOf(u));
            rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt("id");
            }
            return 0;
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            this.close(conn, ps, rs);
        }
        return 0;
    }

    // TODO: GETS PERMISSIONS OF USER
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

    // TODO: ADDS A PERMISSION TO AN USER
    @Override
    public void addPermission(String permission, UUID u) {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = hikari.getConnection();
            ps = conn.prepareStatement("INSERT INTO Permissions(id, permission) VALUES(?,?) ON DUPLICATE KEY UPDATE ");
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

    // TODO: ADDS A BATCH OF PERMISSIONS TO USER
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
