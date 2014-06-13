package twiliodemo.rest;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.verbs.Pause;
import com.twilio.sdk.verbs.Say;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;
import twiliodemo.service.LotteryNumberGenerator;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jma on 6/5/14.
 */
@Component
@Path("/lottery")
public class LotteryResource {

    private int[] getLuckyLottery() {
        return lotteryGenerator.generateLottery();
    }

    @Autowired
    private LotteryNumberGenerator lotteryGenerator;

    @Autowired
    private TwilioRestClient twilioClient;


    private void generateMessage(String from, String to, String body) throws TwilioRestException {

        Account account = twilioClient.getAccount();
        MessageFactory messageFactory = account.getMessageFactory();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("To", to));
        params.add(new BasicNameValuePair("From", from));
        params.add(new BasicNameValuePair("Body", body));
        Message sms = messageFactory.create(params);

    }



    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getGreeting(@QueryParam("From") String from,
                              @QueryParam("To") String to,
                              @DefaultValue("0") @QueryParam("Digits") String digits) {

        TwiMLResponse twiml = new TwiMLResponse();
        Say goodBye = new Say("Good luck and good bye!");
        Pause pause = new Pause();
        pause.setLength(1);
        try {
            if ("0".equals(digits)) {
                Say lotteryNumber = new Say("Your lucky lottery numbers are ");
                twiml.append(lotteryNumber);
                for (Integer num: getLuckyLottery()) {
                    twiml.append(pause);
                    twiml.append(new Say(num.toString()));
                }
                twiml.append(pause);
            } else if ("1".equals(digits)) {
                String lotteryString =
                    lotteryGenerator.generateLotteryInString();
                try {
                    System.out.println("from=" + from  + " to=" + to);
                    generateMessage(to, from, lotteryString);
                    Say genLottery = new Say ("Your lucky lottery numbers are sent to you");
                    twiml.append(genLottery);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            twiml.append(goodBye);

        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        return twiml.toXML();
    }
}
