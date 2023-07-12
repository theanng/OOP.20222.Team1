package array;

import java.util.Arrays;
import java.util.Random;

public class RandomArrayGenerator {
    public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1,100); // Giới hạn từ 1 đến 99
        }
        return array;
    }
    public static void main(String[] args) {
        int size = 5; // Số phần tử trong mảng
        int[] randomArray = generateRandomArray(size);
        System.out.println(Arrays.toString(randomArray));
    }
}
