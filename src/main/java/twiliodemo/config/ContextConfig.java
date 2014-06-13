package twiliodemo.config;

import com.twilio.sdk.TwilioRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Date: 6/10/13
 * Time: 5:54 PM
 */
@Configuration
@ComponentScan(basePackages={"twiliodemo.rest", "twiliodemo.service"})
public class ContextConfig {
    private static final String ACCOUNT_SID = "your-twilio-sid";
    private static final String AUTH_TOKEN = "your-twilio-auth-token";

    @Bean
    static public TwilioRestClient twilioClient() {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        return client;
    }
}
