public class DeterministicSelector {
    public long comparisons = 0;
    public int maxRecursionDepth = 0;

    public int select(int[] arr, int k) {
        if (arr==null||arr.length ==0||k<0||k>=arr.length) {
            throw new IllegalArgumentException("Wrong input data");
        }
        comparisons = 0;
        maxRecursionDepth = 0;
        return selectRecursive(arr, 0, arr.length-1, k, 1);
    }

    private int selectRecursive(int[] arr, int left, int right, int k, int depth) {
        if (depth>maxRecursionDepth) maxRecursionDepth = depth;
        if (left==right) return arr[left];

        int pivotValue = medianOfMedian(arr, left, right, depth);
        int lt = left;
        int gt = right;
        int i = left;

        while (i<=gt) {
            comparisons++;
            if (arr[i] < pivotValue) {
                swap(arr,lt++,i++);
            } else if (arr[i]>pivotValue) {
                swap(arr,i,gt--);
            } else {
                i++;
            }
        }

        if (k>=lt&&k<=gt) {
            return pivotValue;
        } else if (k < lt) {
            return selectRecursive(arr, left, lt - 1, k, depth + 1);
        } else {
            return selectRecursive(arr, gt + 1, right, k, depth + 1);
        }
    }

    private int medianOfMedian(int[] arr, int left, int right, int depth) {
        int n = right-left+1;
        int numGroups = (n+4)/5;
        int[] medians = new int[numGroups];

        for (int i = 0; i < numGroups; i++) {
            int groupLeft = left+i*5;
            int groupRight = Math.min(groupLeft + 4, right);
            medians[i] = findMedian(arr, groupLeft, groupRight);
        }
        if (numGroups == 1) return medians[0];

        return selectRecursive(medians, 0, numGroups - 1, numGroups / 2, depth);
    }

    private int findMedian(int[] arr, int left, int right) {
        for (int i = left+1; i<=right; i++) {
            int key = arr[i];
            int j = i-1;

            while (j>=left&&arr[j]>key) {
                comparisons++;
                arr[j+1] = arr[j];
                j--;
            }
            if (j>=left) comparisons++;
            arr[j+1] = key;
        }
        return arr[left+(right-left)/2];
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}