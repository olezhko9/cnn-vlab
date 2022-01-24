package vlab.server_java;

import java.util.Random;

public class Utils {
    public static int getRandomIntInRange(int min, int max) {
        return min + new Random().nextInt(max - min + 1);
    }
}
