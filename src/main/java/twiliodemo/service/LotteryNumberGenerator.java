package twiliodemo.service;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by jma on 6/6/14.
 */
@Component
public class LotteryNumberGenerator {
    //private final Random random = new Random(System.currentTimeMillis());
    private static final int maxLotteryNumber = 59; // based on CA Power Ball
    private static final int sizeOfPooledPicks = 5;
    private static final int sizeOfPowerPicks = 1;
    private static final int sizeOfTotalPicks = sizeOfPooledPicks + sizeOfPowerPicks;

    private int getRandomPick(Random random) {
        int generated = random.nextInt(maxLotteryNumber)+1;
        return generated;
    }

    public int[] generateLottery(int luckyNumber) {
        Random random = new Random(System.currentTimeMillis() + luckyNumber);
        Set<Integer> pooledPicks = new HashSet<>();
        while (pooledPicks.size() < sizeOfPooledPicks) {
            pooledPicks.add(getRandomPick(random));
        }
        int[] totalPicks = new int[sizeOfTotalPicks];
        int index = 0;
        for (int pooledPick : pooledPicks) {
            totalPicks[index++] = pooledPick;
        }
        for (int i=0; i<sizeOfPowerPicks; ++i) {
            totalPicks[index++] = getRandomPick(random);
        }
        return totalPicks;
    }

    public String generateLotteryInString(int luckyNumber) {
        StringBuilder sb = new StringBuilder();
        for (int i: generateLottery(luckyNumber)) {
            sb.append(i + " ");

        }
        return sb.toString();
    }
}
