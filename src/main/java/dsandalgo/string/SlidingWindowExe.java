package dsandalgo.string;

public class SlidingWindowExe {

    public static void main(String[] args) {
        SlidingWindowExe exe = new SlidingWindowExe();
        exe.numKLenSubstrNoRepeats("havefunonleetcode", 5);
    }
    /**
     * https://leetcode.com/problems/find-k-length-substrings-with-no-repeated-characters/
     *
     * @param S
     * @param K
     * @return
     */
    public int numKLenSubstrNoRepeats(String S, int K) {
        if (K > S.length()) {
            return 0;
        }
        int[] charCount = new int[26];
        for (int i=0; i<K; i++) {
            charCount[S.charAt(i) - 'a']++;
        }
        int ret = isUnique(charCount, K) ? 1 : 0;
        for (int i=K; i<S.length(); i++) {
            charCount[S.charAt(i - K) - 'a']--;
            charCount[S.charAt(i) - 'a']++;
            if (isUnique(charCount, K)) {
                ret++;
            }
        }
        return ret;
    }

    private boolean isUnique(int[] charcnt, int K) {
        for (int i=0; i<26; i++) {
            if (charcnt[i] > 0) {
                K--;
            }
        }
        return K == 0;
    }

}
