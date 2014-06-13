package twiliodemo.rest;

import com.twilio.sdk.verbs.*;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by jma on 6/3/14.
 */
@Component
@Path("/greeting")
public class GreetingResource {
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getGreeting() {
        // Create a TwiML response and add our friendly message.
        TwiMLResponse twiml = new TwiMLResponse();
        Say greeting = new Say("Hello There, ");

        Gather gather = new Gather();
        gather.setAction("/api/fortune");
        gather.setNumDigits(4);
        gather.setMethod("GET");
        Say sayInGather = new Say("Want to know your fortune? Just enter any 4-digit number, followed by the pound sign.");
        Say tryAgain = new Say("The number you have entered is not valid. Please try again.");
        Redirect retry = new Redirect("/api/greeting");
        retry.setMethod("GET");
        try {
            twiml.append(greeting);
            gather.append(sayInGather);
            twiml.append(gather);
            twiml.append(tryAgain);
            twiml.append(retry);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }


        return twiml.toXML();
    }
}
