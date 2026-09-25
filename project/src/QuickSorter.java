import java.util.Random;

public class QuickSorter {
    private static final Random RANDOM = new Random();
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        sort(arr, 0, arr.length - 1);
    }
    private static void sort(int[] arr, int low, int high) {
        while (low < high) {
            int pivotIndex = partition(arr, low, high);

            int leftSize = pivotIndex - low;
            int rightSize = high - pivotIndex;

            if (leftSize < rightSize) {
                sort(arr, low, pivotIndex - 1);
                low = pivotIndex + 1;
            } else {
                sort(arr, pivotIndex + 1, high);
                high = pivotIndex - 1;
            }
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int randomPivotIndex = low + RANDOM.nextInt(high - low + 1);
        swap(arr, randomPivotIndex, high);

        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);

        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}