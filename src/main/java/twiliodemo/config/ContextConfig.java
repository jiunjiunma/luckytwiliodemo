package twiliodemo.config;

import com.twilio.sdk.TwilioRestClient;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import twiliodemo.service.FortuneService;
import twiliodemo.service.MemoryFortuneService;
import twiliodemo.service.RedisFortuneService;

/**
 * Date: 6/10/13
 * Time: 5:54 PM
 */
@Configuration
@ComponentScan(basePackages={"twiliodemo.rest", "twiliodemo.service"})
public class ContextConfig {
    private static final Config config = ConfigFactory.load();

    @Bean
    static public FortuneService fortuneService() {
        String provider = config.getString("fortuneServiceProvider");
        if ("redis".equals(provider)) {
            Config redisConfig = config.getConfig("redis");
            return new RedisFortuneService(
                redisConfig.getString("host"),
                redisConfig.getInt("port"),
                redisConfig.getString("auth"),
                redisConfig.getString("listName")
            );
        } else {
            return new MemoryFortuneService();
        }
    }

    @Bean
    static public TwilioRestClient twilioClient() {
        Config twilioConfig = config.getConfig("twilio");

        TwilioRestClient client = new TwilioRestClient(
            twilioConfig.getString("accountSid"),
            twilioConfig.getString("authToken"));

        return client;
    }
}
