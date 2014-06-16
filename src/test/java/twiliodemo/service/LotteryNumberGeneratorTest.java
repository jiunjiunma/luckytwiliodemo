package twiliodemo.service;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by jma on 6/14/14.
 */
public class LotteryNumberGeneratorTest {
    @Test
    public void testLotteryNumberGenerator() {
        LotteryNumberGenerator generator = new LotteryNumberGenerator();
        int[] generated = generator.generateLottery(1357);
        // should generate 6 lottery numbers
        Assert.assertEquals(6, generated.length);

        // numbers should be between 1 and 59 (inclusive)
        for (int i=0; i<generated.length; ++i) {
            Assert.assertTrue(generated[i] <= 59 && generated[i] >= 1);
        }
    }
}
