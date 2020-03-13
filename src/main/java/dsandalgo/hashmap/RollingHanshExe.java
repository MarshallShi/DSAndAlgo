package dsandalgo.hashmap;

public class RollingHanshExe {


    public static void main(String[] args) {
        RollingHanshExe exe = new RollingHanshExe();
        System.out.println(exe.karpRabin("abc", "bsadabcef"));
    }

    /**
     * https://www.quora.com/What-is-a-rolling-hash-and-when-is-it-useful
     * https://www.quora.com/How-do-I-use-rolling-hash-and-binary-search-to-find-the-longest-common-sub-string
     *
     * Simple implementation of rolling hash
     *
     * First you calculate the hash of first three letter substring (abc) in string.
     * To keep matters simple, lets say we use base 7 for this calculation (in actual scenario we should mod it with a prime to avoid overflow):
     *
     * substring1 = a*7^0+ b*7^1+c*7^2=97*1+98*7+99*49=5634
     * pattern = b*7^0+ c*7^1+d*7^2=98*1+99*7+100*49=5691
     *
     * So you compare two hash values and since they are different, you move forward. Now you reach to second substring "bcd" in string.
     * Here is where the fun begins. We can calculate the hash of this string without rehashing everything.
     * As you can see, the window has moved forward only by dropping one character and adding another:
     * a<- bc<-d
     *
     * From hash value we drop the first character value, divide the left out value with the base number and add the new char to the
     * rightmost position with appropriate power of base: Here are the steps:
     *
     * drop a => (a*7^0+ b*7^1+c*7^2) - a*7^0
     * divide everything by 7 => (b*7^1+c*7^2)/7 => b*7^0 + c*7^1
     * add d => b*7^0 + c*7^1 + d*7^2    [the power of base for d is (pattern-length-1)]
     *
     * Thus new hash for "bcd" in input string would be => (5634 - 97)/7+ 100*49 = 5691 which matches pattern hash value.
     * Now we can go ahead and compare the strings to verify if they are in fact same.
     *
     * Visualize doing this for a large string which comprising of lets say, 30 lines. You will be able to get new hash value in constant time
     * without much effort by just dropping and adding new character(s).
     *
     * In real use cases, to avoid overflow because of large power, we will have to do modulo with large prime.
     * This technique is often used for multi-pattern string search and forms the core of algorithm like Karp-Rabin algorithm.
     * This algorithm is an excellent choice to detect plagiarism.
     */
    public boolean karpRabin(String p, String s) {
        int MOD = 1000000007;
        int primeBase = 7;
        int len = p.length();
        int toCompareHash = 0;
        for (int i=0; i<p.length(); i++) {
            toCompareHash += (p.charAt(i) * Math.pow(primeBase, i)) % MOD;
        }
        int rollingHash = 0;

        int j = 0;
        for (int i=0; i<s.length(); i++) {
            if (i < len) {
                rollingHash += (s.charAt(i) * Math.pow(primeBase, i)) % MOD;
            } else {
                if (rollingHash == toCompareHash && p.equals(s.substring(i-len, i))) {
                    return true;
                }
                rollingHash = rollingHash - (int)(s.charAt(j) * Math.pow(primeBase, j)) % MOD;
                rollingHash = rollingHash/primeBase;
                rollingHash = rollingHash + (int)(s.charAt(i) * Math.pow(primeBase, len-1)) % MOD;
            }
        }
        return false;
    }


    /**
     * https://leetcode.com/problems/longest-chunked-palindrome-decomposition/
     * @param text
     * @return
     */
    public int longestDecomposition(String text) {
        int count = 0;
        int i = 0, j = text.length() - 1;
        long h1 = 0, h2 = 0;
        long base1 = 26, base2 = 1;

        while (i <= j) {
            char c1 = text.charAt(i), c2 = text.charAt(j);
            h1 = h1 * base1 + (c1 - 'a' + 1);
            h2 = h2 + (c2 - 'a' + 1) * base2;
            base2 *= base1;
            if (h1 == h2) {
                if (i == j) count += 1;
                else count += 2;
                h1 = 0;
                h2 = 0;
                base2 = 1;
            }
            i++;
            j--;
        }
        if (h1 != 0) count++;
        return count;
    }
}
