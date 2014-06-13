package twiliodemo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by jma on 6/13/14.
 */
public class MemoryFortuneService implements FortuneService {

    private static String[] bootstrapFortunes = {
        "you will never have to worry about a steady income",
        "a great fortune is coming your way",
        "you will be meeting an old friend very soon",
        "you will have a new adventure in a new city very soon"
    };

    private List<String> fortunes = new ArrayList<>();

    public MemoryFortuneService() {
        for (String s: bootstrapFortunes) {
            fortunes.add(s);
        }
    }

    public void addFortune(String newFortune) {
        if (newFortune != null && !newFortune.isEmpty()) {
            synchronized(fortunes) {
                fortunes.add(newFortune);
            }
        }

    }

    public String getFortune(Long luckyNumberHint) {
        int maxFortune;
        synchronized(fortunes) {
            maxFortune = fortunes.size();
        }

        long luckyNumber = new Date().getTime();
        Random random = new Random();
        random.setSeed(luckyNumber);
        int index = random.nextInt(maxFortune);
        String fortune;
        synchronized(fortunes) {
            fortune = fortunes.get(index);
        }
        return fortune;
    }


}
