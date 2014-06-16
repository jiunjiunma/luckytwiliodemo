package twiliodemo.rest;

import com.twilio.sdk.verbs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import twiliodemo.service.FortuneService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by jma on 6/4/14.
 */
@Component
@Path("/fortune")
public class FortuneResource {

    @Autowired
    private FortuneService fortuneService;

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getFortune(@DefaultValue("1234") @QueryParam("Digits") String digits) {

        String fortune = fortuneService.getFortune(0L);

        TwiMLResponse twiml = new TwiMLResponse();
        Say say = new Say("Here is your fortune: " + fortune);
        Pause pause = new Pause();
        pause.setLength(1);
        Gather gather = new Gather();
        gather.setAction("/api/lottery?LuckyNumber="+digits);
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
        fortuneService.addFortune(newFortune);
    }

}
