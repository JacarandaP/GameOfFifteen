/**
 * Created by Jacaranda Perez
 * Date: 2020-10-23
 * Project: GameOfFifteen
 */

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

class Test {
    public static void main(String args[]) {
        int[] solutionArray = {1, 2, 3, 4, 5, 6, 16, 15, 14, 13, 12, 11};

        shuffleArray(solutionArray);
        for (int i = 0; i < solutionArray.length; i++) {
            System.out.print(solutionArray[i] + " ");
        }
        System.out.println();


        int [][] tempArr = {{11, 22, 14,},
                            {78, 99,  1,},
                            {4,   8,  2}} ;


        shuffleDubleArray(tempArr);
        for (int i = 0; i < 3; i++)
        for (int j = 0; j < 3; j++){
            System.out.print(tempArr[i][j] + " ");
        }
        System.out.println();
    }


    // Implementing Fisher–Yates shuffle
    static void shuffleArray(int[] ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }


    public static void shuffleDubleArray(int[][] buttons) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = 2; i > 0; i--)
            for (int j = 2; j > 0; j--) {
                int indexX = rnd.nextInt(i + 1);
                int indexY = rnd.nextInt(j + 1);

                int[] a = buttons[indexX];
                int[] b = buttons[indexY];
                buttons[indexX] = buttons[i];
                buttons[indexY] = buttons[j];
                buttons[i] = a;
                buttons[j] = b;
            }
    }
}