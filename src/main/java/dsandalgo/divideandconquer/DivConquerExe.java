package dsandalgo.divideandconquer;

import java.util.List;

public class DivConquerExe {


    /**
     * https://leetcode.com/problems/beautiful-array/
     *
     * For some fixed N, an array A is beautiful if it is a permutation of the integers 1, 2, ..., N, such that:
     *
     * For every i < j, there is no k with i < k < j such that A[k] * 2 = A[i] + A[j].
     *
     * Given N, return any beautiful array A.  (It is guaranteed that one exists.)
     *
     * Example 1:
     *
     * Input: 4
     * Output: [2,1,4,3]
     * Example 2:
     *
     * Input: 5
     * Output: [3,1,2,5,4]
     *
     *
     * Note:
     *
     * 1 <= N <= 1000
     */
    public int[] beautifulArray(int N) {
        int[] arr = new int[N];
        for (int i=0; i<N; i++) {
            arr[i] = i;
        }
        return null;
    }

//    private List<Integer> helper(int[] arr) {
//    }

    //var beautifulArray = function(N) {
    //    let arr=Array.from({length:N}, (x,i)=>i+1);
    //    return helper(arr);
    //
    //    function helper(arr){
    //    	if(arr.length===1) return arr;
    //    	let o=[], e=[]; //odd index and even index
    //    	for(let i=0; i<arr.length; i++){
    //    		if(i%2===0) e.push(arr[i]);
    //    		else o.push(arr[i]);
    //    	}
    //    	return helper(e).concat(helper(o));
    //    }
    //};
}
