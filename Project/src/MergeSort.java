public class MergeSort {

    private static final int CUTOFF = 15;
    public static void sort(int[] arr){
        if(arr==null||arr.length<1){
            return;
        }
        int[] aux = new int[arr.length];
        sort(arr,aux,0,arr.length-1);
    }
    private static void sort(int[] arr, int[] aux, int low, int high){
        if(high-low<=CUTOFF-1){
            insertionSort(arr);
            return;
        }
        int mid = low+(high-low)/2;

        sort(arr,aux, low, mid);
        sort(arr,aux,mid+1, high);

        if(arr[mid]<=arr[mid+1]){
            return;
        }
        merge(arr, aux, low, mid, high);

    }
    private static void merge(int[] arr, int[] aux, int low, int mid, int high){
        for (int k = low; k <= high; k++) {
            aux[k] = arr[k];
        }
        int j = low;
        int i = mid+1;
        for (int k = low; k<=high;k++){
            if(mid>i){
                arr[k] = aux[j++];
            } else if (high<j) {
                arr[k]=aux[i++];
            } else if (aux[j]<arr[i]) {
                arr[k]=aux[j++];
            } else {
                arr[k]=aux[i++];
            }

        }
    }
    private static void insertionSort(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            int key=arr[i];
            int j = i-1;
            while(j>=0 && arr[j]>key){
                arr[j+1]=arr[j];
                j--;
            }
            arr[j+1]=key;
        }
    }

}

