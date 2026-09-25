import java.util.Arrays;
import java.util.Random;

public class Experiment {
    private static final Random random = new Random();

    public static int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(100000);
        }
        return arr;
    }

    public static int[] generateSortedArray(int size) {
        int[] arr = generateRandomArray(size);
        Arrays.sort(arr);
        return arr;
    }

    public static int[] generateReverseSortedArray(int size) {
        int[] arr = generateSortedArray(size);
        for (int i = 0; i < size / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[size - 1 - i];
            arr[size - 1 - i] = temp;
        }
        return arr;
    }

    public static int[] generateDuplicateHeavyArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(10);
        }
        return arr;
    }

    public static boolean verifySort(int[] original, int[] sortedByUs) {
        int[] reference = Arrays.copyOf(original, original.length);
        Arrays.sort(reference);
        return Arrays.equals(reference, sortedByUs);
    }

    public static Point[] generateRandomPoints(int size) {
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point(random.nextDouble() * 10000, random.nextDouble() * 10000);
        }
        return points;
    }

    public static boolean verifySelect100Times(int[] original) {
        DeterministicSelector selector = new DeterministicSelector();
        int n = original.length;
        int[] reference = Arrays.copyOf(original, n);
        Arrays.sort(reference);

        for (int i = 0; i < 100; i++) {
            int k = random.nextInt(n);
            int[] copyForTest = Arrays.copyOf(original, n);
            int result = selector.select(copyForTest, k);

            if (result != reference[k]) {
                return false;
            }
        }
        return true;
    }
}