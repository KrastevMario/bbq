package bbqRemake.util;

import java.util.Random;

public class Randomizer {
    public static int getRandomNumber(int min, int max){
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}
