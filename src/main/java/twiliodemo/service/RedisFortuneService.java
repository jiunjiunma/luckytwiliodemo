package twiliodemo.service;

import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Random;

/**
 * Created by jma on 6/13/14.
 */
public class RedisFortuneService implements FortuneService {
    private String redisHost;
    private int redisPort;
    private String redisAuth;
    private String redisListName;

    public RedisFortuneService(String host, int port, String auth, String listName) {
        this.redisHost = host;
        this.redisPort = port;
        this.redisAuth = auth;
        this.redisListName = listName;
    }

    @Override
    public void addFortune(String newFortune) {
        Jedis jedis = new Jedis(redisHost, redisPort);
        jedis.auth(redisAuth);

        if (newFortune != null && !newFortune.isEmpty()) {
            jedis.lpush(redisListName, newFortune);
        }
    }

    @Override
    public String getFortune(Long luckyNumberHint) {
        Random random = new Random();
        Jedis jedis = new Jedis(redisHost, redisPort);
        jedis.auth(redisAuth);
        int maxFortune = jedis.llen(redisListName).intValue();

        long luckyNumber = new Date().getTime();
        random.setSeed(luckyNumber);
        int index = random.nextInt(maxFortune);

        String fortune = jedis.lindex(redisListName, index);
        return fortune;
    }
}
