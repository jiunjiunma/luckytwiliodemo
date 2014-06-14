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
    private final Random random = new Random(System.currentTimeMillis());
    private static final int maxLotteryNumber = 59; // based on CA Power Ball
    private static final int sizeOfPooledPicks = 5;
    private static final int sizeOfPowerPicks = 1;
    private static final int sizeOfTotalPicks = sizeOfPooledPicks + sizeOfPowerPicks;

    private int getRandomPick() {
        int generated = random.nextInt(maxLotteryNumber)+1;
        return generated;
    }

    public int[] generateLottery() {
        Set<Integer> pooledPicks = new HashSet<>();
        while (pooledPicks.size() < sizeOfPooledPicks) {
            pooledPicks.add(getRandomPick());
        }
        int[] totalPicks = new int[sizeOfTotalPicks];
        int index = 0;
        for (int pooledPick : pooledPicks) {
            totalPicks[index++] = pooledPick;
        }
        for (int i=0; i<sizeOfPowerPicks; ++i) {
            totalPicks[index++] = getRandomPick();
        }
        return totalPicks;
    }

    public String generateLotteryInString() {
        StringBuilder sb = new StringBuilder();
        for (int i: generateLottery()) {
            sb.append(i + " ");

        }
        return sb.toString();
    }
}
