import java.util.Random;

public class QuickSorter {
    public long comparisons = 0;
    public int maxRecursionDepth = 0;
    private final Random random = new Random();

    public void quickSort(int[] arr) {
        if (arr==null||arr.length<=1) return;
        comparisons = 0;
        maxRecursionDepth = 0;
        quickSort(arr, 0, arr.length - 1, 1);
    }

    public void quickSort(int[] arr, int start, int end, int depth) {
        while (start<end) {
            if (depth > maxRecursionDepth) {
                maxRecursionDepth = depth;
            }

            int randomIndex = start + random.nextInt(end-start+1);
            int pivot = arr[randomIndex];
            int lt = start;
            int gt = end;
            int i = start;

            while (i <= gt) {
                comparisons++;
                if (arr[i]<pivot) {
                    swap(arr, lt++, i++);
                } else if (arr[i]>pivot) {
                    swap(arr, i, gt--);
                } else {
                    i++;
                }
            }
            int leftSize = lt - start;
            int rightSize = end - gt;
            if (leftSize < rightSize) {
                quickSort(arr, start, lt-1, depth+1);
                start = gt+1;
            } else {
                quickSort(arr, gt+1, end, depth+1);
                end = lt-1;
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}