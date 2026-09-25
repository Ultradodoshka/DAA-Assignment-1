public class MergeSorter {
    private static final int CUTOFF = 15;
    public long comparisons = 0;
    public int maxRecursionDepth =0;

    public void mergeSort(int[] arr) {
        if(arr==null || arr.length<=1) return;
        int[] temp= new int[arr.length];
        comparisons=0;
        maxRecursionDepth=0;

        sort(arr,temp,0, arr.length-1,1);
    }
    private void sort(int[] arr, int[] temp,int left, int right, int depth) {
        if(depth>maxRecursionDepth){
            maxRecursionDepth=depth;
        }
        if(right-left<CUTOFF){
            insertionSort(arr,left,right);
            return;
        }
        int mid = left + (right-left)/2;

        sort(arr,temp,left,mid,depth+1);
        sort(arr,temp,mid+1,right,depth+1);

        merge(arr,temp,left,mid,right);
    }


    private void insertionSort(int[] arr, int left, int right) {
        for (int i = left; i <= right; i++) {
            int key = arr[i];
            int j = i-1;
            while (j>=left && arr[j]>key){
                arr[j+1] = arr[j];
                j--;
            }
            if(j>=left){
                comparisons++;
            }
            arr[j+1] = key;
        }
    }

    private void merge(int[] arr, int[] temp, int left, int mid, int right) {
        for(int i=left; i<=right; i++){
            temp[i]=arr[i];
        }
        int i = left, j = mid+1,  k = left;

        while(i<=mid && j<=right){
            comparisons++;
            if(temp[i]<=temp[j]){
                arr[k++]=temp[i++];
            } else{
                arr[k++]=temp[j++];
            }
        }
        while(i<=mid){
            arr[k++]=temp[i++];
        }
    }
}

