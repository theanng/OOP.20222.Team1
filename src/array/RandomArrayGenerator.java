package array;

import java.util.Random;

public class RandomArrayGenerator {
    public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        int previousNumber = -1; // Giá trị ban đầu của số trước đó
        for (int i = 0; i < size; i++) {
            int randomNumber = random.nextInt(100);
            while (randomNumber == previousNumber) {
                randomNumber = random.nextInt(100);
            }
            array[i] = randomNumber;
            previousNumber = randomNumber;
        }
        return array;
    }
}
