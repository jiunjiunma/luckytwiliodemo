package twiliodemo.rest;

import com.twilio.sdk.verbs.*;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by jma on 6/4/14.
 */
@Component
@Path("/fortune")
public class FortuneResource {
    // these are only used if you are using a memory-based data source
    private static String[] bootstrapFortunes = {
        "you will never have to worry about a steady income",
        "a great fortune is coming your way",
        "you will be meeting an old friend very soon",
        "you will have a new adventure in a new city very soon"
    };

    private List<String> fortunes = new ArrayList<>();

    public FortuneResource() {
        for (String s: bootstrapFortunes) {
            fortunes.add(s);
        }
    }

    // setting for redis based data source
    private static final String redisHost = "<redis-host>";
    private static final int redisPort = 1621;
    private static final String redisAuth = "<redis-auth>";
    private static final String redisListName = "<redis-list-name>";

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getGreeting(@DefaultValue("there") @QueryParam("From") String from,
                              @DefaultValue("1234") @QueryParam("Digits") String digits) {
        Random random = new Random();
        Jedis jedis = new Jedis(redisHost, redisPort);
        jedis.auth(redisAuth);
        // Create a TwiML response and add our friendly message.
        int maxFortune = jedis.llen(redisListName).intValue();
        /*
        int maxFortune;
        synchronized(fortunes) {
            maxFortune = fortunes.size();
        }
        */

        long luckyNumber = new Date().getTime();
        random.setSeed(luckyNumber);
        int index = random.nextInt(maxFortune);

        String fortune = jedis.lindex(redisListName, index);
        /*
        String fortune;
        synchronized(fortunes) {
            fortune = fortunes.get(index);
        }
        */

        TwiMLResponse twiml = new TwiMLResponse();
        Say say = new Say("Here is your fortune. " + fortune + ".");
        Pause pause = new Pause();
        pause.setLength(1);
        Gather gather = new Gather();
        gather.setAction("/api/lottery");
        gather.setNumDigits(1);
        gather.setMethod("GET");
        Say sayInGather = new Say("Press 0 to hear your lucky lottery number. " +
                                  "Press 1 to have it text to you.");
        Say goodBye = new Say("Goodbye!");
        try {
            twiml.append(say);
            twiml.append(pause);
            gather.append(sayInGather);
            twiml.append(gather);
            twiml.append(goodBye);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }


        return twiml.toXML();
    }

    @POST
    @Consumes("text/plain")
    public void addFortune(String newFortune) {
        Jedis jedis = new Jedis(redisHost, redisPort);
        jedis.auth(redisAuth);

        if (newFortune != null && !newFortune.isEmpty()) {
            jedis.lpush(redisListName, newFortune);

            /*
            synchronized(fortunes) {
                fortunes.add(newFortune);
            }
            */

        }
    }

}
