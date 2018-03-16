package me.tcpackfrequency.orbitalmc.database.cache;

import redis.clients.jedis.Jedis;

public class Redis {

    private Jedis jedis;

    public void connect(String ip, int port, String password){
        this.jedis = new Jedis(ip, port);
        jedis.auth(password);
        System.out.println("[REDIS] Successfully connected to Redis server.");
    }

}
