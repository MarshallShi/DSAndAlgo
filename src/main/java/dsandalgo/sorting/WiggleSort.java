package dsandalgo.sorting;

import java.util.Arrays;
import java.util.Random;

public class WiggleSort {

    public static void main(String[] args){
        WiggleSort ws = new WiggleSort();
        int[] nums = {3,5,2,1,6,4};
        ws.wiggleSort(nums);
    }
    /**
     * https://leetcode.com/problems/wiggle-sort/
     *
     * Given an unsorted array nums, reorder it in-place such that nums[0] <= nums[1] >= nums[2] <= nums[3]....
     *
     * Example:
     *
     * Input: nums = [3,5,2,1,6,4]
     * Output: One possible answer is [3,5,1,6,2,4]
     * @param nums
     */
    public void wiggleSort(int[] nums) {
        for (int i=0; i<nums.length; i++){
            if(i%2 == 1){
                if(nums[i-1] > nums[i]){
                    int tmp = nums[i-1];
                    nums[i-1] = nums[i];
                    nums[i] = tmp;
                }
            } else {
                if(i > 0 && nums[i-1] < nums[i]){
                    int tmp = nums[i-1];
                    nums[i-1] = nums[i];
                    nums[i] = tmp;
                }
            }
        }
    }

    //https://leetcode.com/problems/wiggle-sort-ii/

    //https://leetcode.com/problems/wiggle-sort-ii/discuss/77684/Summary-of-the-various-solutions-to-Wiggle-Sort-for-your-reference

    //O(nlogn) time and O(n) space solution by sorting
    public void wiggleSort_1(int[] nums) {
        int n = nums.length, m = (n + 1) >> 1;
        int[] copy = Arrays.copyOf(nums, n);
        Arrays.sort(copy);

        for (int i = m - 1, j = 0; i >= 0; i--, j += 2) nums[j] = copy[i];
        for (int i = n - 1, j = 1; i >= m; i--, j += 2) nums[j] = copy[i];
    }


    //O(n) time and O(n) space solution by median partition
    //Sorting the whole array is overkill for the partition part, since all we need are the S and L groups.
    // As for elements within each group, we don't really care whether they are sorted or not. Suppose m_ele is the m-th smallest element in the array.
    // We partition the array such that all elements less than m_ele go to its left and those greater than it end up in its right (or the other way around).
    // After partition, the first m elements will form the S group while the rest will be the L group.
    // To get the m-th smallest element, we will use the randomized quick-sort subroutine (refer to problem leetcode 215 for more details).
    // All the three parts (obtaining the m-th smallest element, partition and placement) can be done in O(n) time,
    // therefore the total time complexity will be O(n). And again we will use an extra array to simplify the index mapping process. Here is the java code:
    public void wiggleSort_2(int[] nums) {
        int n = nums.length, m = (n + 1) >> 1;
        int[] copy = Arrays.copyOf(nums, n);
        int median = kthSmallestNumber(nums, m);

        for (int i = 0, j = 0, k = n - 1; j <= k;) {
            if (copy[j] < median) {
                swap(copy, i++, j++);
            } else if (copy[j] > median) {
                swap(copy, j, k--);
            } else {
                j++;
            }
        }

        for (int i = m - 1, j = 0; i >= 0; i--, j += 2) nums[j] = copy[i];
        for (int i = n - 1, j = 1; i >= m; i--, j += 2) nums[j] = copy[i];
    }

    private int kthSmallestNumber(int[] nums, int k) {
        Random random = new Random();

        for (int i = nums.length - 1; i >= 0; i--) {
            swap(nums, i, random.nextInt(i + 1));
        }

        int l = 0, r = nums.length - 1;
        k--;

        while (l < r) {
            int m = getMiddle(nums, l, r);

            if (m < k) {
                l = m + 1;
            } else if (m > k) {
                r = m - 1;
            } else {
                break;
            }
        }

        return nums[k];
    }

    private int getMiddle(int[] nums, int l, int r) {
        int i = l;

        for (int j = l + 1; j <= r; j++) {
            if (nums[j] < nums[l]) swap(nums, ++i, j);
        }

        swap(nums, l, i);
        return i;
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    //O(n) time and O(1) space solution by combining partition and placement
    //To have O(1) space, we have to drop the extra array. Then both the partition and placement will be done on the input array itself.
    // If these two parts are carried out separately, i.e., placement is done after partition is completely finished,
    // there will be no nice way to do the index mapping without keeping track of mapped and unmapped elements (as far as I know).
    // It turns out the two parts can be combined into a single one.
    //
    //To see how, let's examine the whole partition and placement process more carefully.
    // For partition, its core function is to subject each element in the array to the partition rule (i.e., compared with median) exactly
    // once and distribute the element to the proper position based on the comparison result. Traditionally we will add it to either the left
    // or right half of the array and deal with it later after the partition is over. This is of course unnecessary.
    // The element should be ready for consumption (do whatever you like with it) once the distribution for it is over.
    // We might as well map it to a new position according to the above placement rule (for example, j = (2 * i + 1) % (n | 1) for
    // descending order), right before going to the next element. The traditional way of disposing the elements corresponds to the identity mapping (i.e., j = i).
    //
    //One important issue here for the index mapping is the order for traversing the array in the partition process.
    // The traditional way of linear scan from left to right works well with the identity mapping, but obviously will violate the constraint that each
    // element will be partitioned exactly once if the mapping takes the partitioned element to the unscanned area. The correct traversing order typically
    // depends on the mapping we want to do. Here I prove that the partition constraint will not be violated if the mapping is bijective and the traversing
    // order follows that of the linear scan but with the same mapping applied at each position.
    //
    //Each mapping f will be characterized by three parts: domain, codomain and mapping rules. For our case, both the domain and codomain will be the set S = [0, n)
    // (i.e., integers from 0 up to n - 1). If f is bijective, we have:
    //
    //for any i1, i2 that belong to S, i1 != i2 <==> f(i1) != f(i2).
    //if i covers all the elements in S exactly once, f(i) will do the same.
    //If our traversing order follows that of the linear scan with f applied for each position i in the linear scan, the first property will guarantee each element
    // will be visited at most once while the second will guarantee each element be visited at least once. Therefore, at the end each element will be visited exactly once.
    //
    //By the way, this index mapping idea is called "virtual index" in stefanpochmann's post and further explained in shuoshankou's post. I will follow the
    // same notation for the index mapping function (which is called A here). We can do different mappings as needed. The following implementation is for descending
    // order. And just for fun, a rotation mapping j = (i + k) % n will rotate the resulted array after partition, check it out! Anyway, here is the java code:
    public void wiggleSort_3(int[] nums) {
        int n = nums.length, m = (n + 1) >> 1;
        int median = kthSmallestNumber(nums, m);

        for (int i = 0, j = 0, k = n - 1; j <= k;) {
            if (nums[A(j, n)] > median) {
                swap(nums, A(i++, n), A(j++, n));
            } else if (nums[A(j, n)] < median) {
                swap(nums, A(j, n), A(k--, n));
            } else {
                j++;
            }
        }
    }

    private int A(int i, int n) {
        return (2 * i + 1) % (n | 1);
    }
}