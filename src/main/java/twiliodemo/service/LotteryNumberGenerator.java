package twiliodemo.service;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by jma on 6/6/14.
 */
@Component
public class LotteryNumberGenerator {
    private final Random random = new Random(System.currentTimeMillis());
    private final int maxLotteryNumber = 46; // ???

    public int[] generateLottery() {
        int[] generated = { 0, 0, 0, 0, 0, 0, 0 };
        for (int i=0; i< generated.length; ++i) {
            generated[i] = random.nextInt(maxLotteryNumber)+1;
        }
        return generated;
    }

    public String generateLotteryInString() {
        StringBuilder sb = new StringBuilder();
        for (int i: generateLottery()) {
            sb.append(i + " ");

        }
        return sb.toString();
    }
}
