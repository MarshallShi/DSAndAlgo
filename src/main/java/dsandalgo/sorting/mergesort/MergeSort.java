package dsandalgo.sorting.mergesort;

public class MergeSort {

    private void merge(int arr[], int low, int mid, int high) {
        // Find sizes of two sub arrays to be merged
        int leftLen = mid - low + 1;
        int rightLen = high - mid;
        /* Create temp arrays */
        int[] leftArr = new int[leftLen];
        int[] rightArr = new int[rightLen];
        /*Copy data to temp arrays*/
        for (int i = 0; i < leftLen; ++i) leftArr[i] = arr[low + i];
        for (int j = 0; j < rightLen; ++j) rightArr[j] = arr[mid + 1 + j];
        /* Merge the temp arrays */
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
        // Initial index of merged subarry array
        int k = low;
        while (i < leftLen && j < rightLen) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        /* Copy remaining elements of L[] if any */
        while (i < leftLen) arr[k++] = leftArr[i++];
        /* Copy remaining elements of R[] if any */
        while (j < rightLen) arr[k++] = rightArr[j++];
    }

    private void mergeSort(int arr[], int l, int r) {
        if (l < r) {
            // Find the middle point
            int m = l + (r - l) / 2;
            // Sort first and second halves
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

    /* A utility function to print array of size n */
    public static void printArray(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n; ++i) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String args[]) {
        int arr[] = {12, 11, 13, 5, 6, 7};

        System.out.println("Given Array");
        printArray(arr);

        MergeSort ob = new MergeSort();
        ob.mergeSort(arr, 0, arr.length - 1);

        System.out.println("\nSorted array");
        printArray(arr);
    }

}
