package dsandalgo.bit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class BitExe {

    public static void main(String[] args) {
        BitExe exe = new BitExe();
        int[][] input = {{0,0,1,1},
                {1,0,1,0},
                {1,1,0,0}};

        int[] arr1 = {0,1,2,3,4,5,6,7,8};
        int[] arr2 = {1,0,1};
        int[][] queries = {{2,3},{1,3},{0,0},{0,3}};
        String[] words = {"apple","pleas","please"};
        String[] puzzles = {"aelwxyz","aelpxyz","aelpsxy","saelpxy","xaelpsy"};
        System.out.println(exe.convertToTitle(28));
    }

    /**
     * https://leetcode.com/problems/excel-sheet-column-title/
     * @param n
     * @return
     */
    public String convertToTitle(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 26) {
            if (n % 26 == 0) {
                sb.append('Z');
                n = n / 26;
                n--;
            } else {
                sb.append((char) ('A' + n % 26 - 1));
                n = n / 26;
            }
        }
        return sb.append((char)('A' + n - 1)).reverse().toString();
    }

    /**
     * https://leetcode.com/problems/add-binary/
     * @param a
     * @param b
     * @return
     */
    public String addBinary(String a, String b) {
        int i = a.length() - 1, j = b.length() - 1;
        StringBuilder sb = new StringBuilder();
        int carrier = 0;
        while (!(i<0 && j<0 && carrier == 0)) {
            int tempA = i < 0 ? 0 : Character.getNumericValue(a.charAt(i));
            int tempB = j < 0 ? 0 : Character.getNumericValue(b.charAt(j));
            if (tempA + tempB + carrier> 1) {
                sb.append((tempA + tempB + carrier)%2);
                carrier = 1;
            } else {
                sb.append(tempA + tempB + carrier);
                carrier = 0;
            }
            i--;
            j--;
        }
        return sb.reverse().toString();
    }


    /**
     * https://leetcode.com/problems/number-of-steps-to-reduce-a-number-in-binary-representation-to-one/
     *
     * Given a number s in their binary representation. Return the number of steps to reduce it to 1 under the following rules:
     *
     * If the current number is even, you have to divide it by 2.
     *
     * If the current number is odd, you have to add 1 to it.
     *
     * It's guaranteed that you can always reach to one for all testcases.
     *
     *
     *
     * Example 1:
     *
     * Input: s = "1101"
     * Output: 6
     * Explanation: "1101" corressponds to number 13 in their decimal representation.
     * Step 1) 13 is odd, add 1 and obtain 14.
     * Step 2) 14 is even, divide by 2 and obtain 7.
     * Step 3) 7 is odd, add 1 and obtain 8.
     * Step 4) 8 is even, divide by 2 and obtain 4.
     * Step 5) 4 is even, divide by 2 and obtain 2.
     * Step 6) 2 is even, divide by 2 and obtain 1.
     * Example 2:
     *
     * Input: s = "10"
     * Output: 1
     * Explanation: "10" corressponds to number 2 in their decimal representation.
     * Step 1) 2 is even, divide by 2 and obtain 1.
     * Example 3:
     *
     * Input: s = "1"
     * Output: 0
     *
     *
     * Constraints:
     *
     * 1 <= s.length <= 500
     * s consists of characters '0' or '1'
     * s[0] == '1'
     *
     * We have three phases here:
     *
     * We haven't encountered any 1. Every char adds one operation.
     * We encounter our first 1. We set carry to 1 and add two operations.
     * The rest:
     * 3A. Every 1 needs one operation (carry makes it 0). carry is still 1 due to addition.
     * 3B. Every 0 needs two operations (carry makes it 1). carry is still 1 as we need to add 1 in this case.
     */
    public int numSteps(String s) {
        int res = 0, carry = 0;
        for (int i = s.length() - 1; i > 0; --i) {
            ++res;
            if (s.charAt(i) - '0' + carry == 1) {
                carry = 1;
                ++res;
            }
        }
        return res + carry;
    }

    /**
     * https://leetcode.com/problems/number-of-valid-words-for-each-puzzle/
     * With respect to a given puzzle string, a word is valid if both the following conditions are satisfied:
     * word contains the first letter of puzzle.
     * For each letter in word, that letter is in puzzle.
     * For example, if the puzzle is "abcdefg", then valid words are "faced", "cabbage", and "baggage"; while invalid words are "beefed" (doesn't include "a") and "based" (includes "s" which isn't in the puzzle).
     * Return an array answer, where answer[i] is the number of words in the given word list words that are valid with respect to the puzzle puzzles[i].
     *
     *
     * Example :
     *
     * Input:
     * words = ["aaaa","asas","able","ability","actt","actor","access"],
     * puzzles = ["aboveyz","abrodyz","abslute","absoryz","actresz","gaswxyz"]
     * Output: [1,1,3,2,4,0]
     * Explanation:
     * 1 valid word for "aboveyz" : "aaaa"
     * 1 valid word for "abrodyz" : "aaaa"
     * 3 valid words for "abslute" : "aaaa", "asas", "able"
     * 2 valid words for "absoryz" : "aaaa", "asas"
     * 4 valid words for "actresz" : "aaaa", "asas", "actt", "access"
     * There're no valid words for "gaswxyz" cause none of the words in the list contains letter 'g'.
     *
     *
     * Constraints:
     *
     * 1 <= words.length <= 10^5
     * 4 <= words[i].length <= 50
     * 1 <= puzzles.length <= 10^4
     * puzzles[i].length == 7
     * words[i][j], puzzles[i][j] are English lowercase letters.
     * Each puzzles[i] doesn't contain repeated characters.
     */
    public List<Integer> findNumOfValidWords(String[] words, String[] puzzles) {
        Map<Integer, Integer> map = new HashMap<>();

        for (String w : words) {
            int mask = 0;
            for (int i = 0; i < w.length(); i++) {
                mask |= 1 << (w.charAt(i) - 'a');
            }
            map.put(mask, map.getOrDefault(mask, 0) + 1);
        }

        List<Integer> res = new ArrayList<>();

        for (String p : puzzles) {
            int mask = 0;
            for (int i = 0; i < p.length(); i++) {
                mask |= 1 << (p.charAt(i) - 'a');
            }
            int c = 0;
            int sub = mask;
            int first = 1 << (p.charAt(0) - 'a');
            while (true) {
                if ((sub & first) == first && map.containsKey(sub)) {
                    c += map.get(sub);
                }

                if (sub == 0) break;

                sub = (sub - 1) & mask; // get the next substring
            }

            res.add(c);
        }

        return res;
    }

    /**
     * https://leetcode.com/problems/sort-integers-by-the-number-of-1-bits/
     * Given an integer array arr. You have to sort the integers in the array in ascending order by the number of 1's in their
     * binary representation and in case of two or more integers have the same number of 1's you have to sort them in ascending order.
     *
     * Return the sorted array.
     *
     * Example 1:
     * Input: arr = [0,1,2,3,4,5,6,7,8]
     * Output: [0,1,2,4,8,3,5,6,7]
     * Explantion: [0] is the only integer with 0 bits.
     * [1,2,4,8] all have 1 bit.
     * [3,5,6] have 2 bits.
     * [7] has 3 bits.
     * The sorted array by bits is [0,1,2,4,8,3,5,6,7]
     */
    public int[] sortByBits(int[] arr) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] == o2[1]) {
                    return o1[0] - o2[0];
                }
                return o1[1] - o2[1];
            }
        });
        for (int i=0; i<arr.length; i++) {
            int[] val = new int[2];
            val[0] = arr[i];
            val[1] = Integer.bitCount(arr[i]);
            pq.offer(val);
        }
        int[] ret = new int[arr.length];
        int idx = 0;
        while (!pq.isEmpty()) {
            ret[idx++] = pq.poll()[0];
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/bitwise-and-of-numbers-range/
     *
     * Given a range [m, n] where 0 <= m <= n <= 2147483647, return the bitwise AND of all numbers in this range, inclusive.
     * Example 1:
     * Input: [5,7]
     * Output: 4
     * Example 2:
     * Input: [0,1]
     * Output: 0
     */
    //TRICK: As long as the two number not equal, we can't keep the bits, as they differ, the last digit will have zero.
    public int rangeBitwiseAnd(int m, int n) {
        if(m == 0){
            return 0;
        }
        int moveFactor = 1;
        while (m != n) {
            m >>= 1;
            n >>= 1;
            moveFactor <<= 1;
        }
        return m * moveFactor;
    }

    /**
     * https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/
     * Given a non-empty array of numbers, a0, a1, a2, … , an-1, where 0 ≤ ai < 231.
     *
     * Find the maximum result of ai XOR aj, where 0 ≤ i, j < n.
     *
     * Could you do this in O(n) runtime?
     *
     * Example:
     *
     * Input: [3, 10, 5, 25, 2, 8]
     *
     * Output: 28
     *
     * Explanation: The maximum result is 5 ^ 25 = 28.
     */
    //https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/discuss/91049/Java-O(n)-solution-using-bit-manipulation-and-HashMap
    //if A ^ B = C, then A ^ B ^ B = C ^ B, then A = C ^ B
    //to iteratively determine what would be each bit of the final result from left to right.
    // And it narrows down the candidate group iteration by iteration. e.g. assume input are a,b,c,d,...z, 26 integers in total.
    // In first iteration, if you found that a, d, e, h, u differs on the MSB(most significant bit), so you are sure your final result's MSB is set.
    // Now in second iteration, you try to see if among a, d, e, h, u there are at least two numbers make the 2nd MSB differs, if yes, then definitely, the 2nd MSB will be set in the final result.
    // And maybe at this point the candidate group shinks from a,d,e,h,u to a, e, h.
    // Implicitly, every iteration, you are narrowing down the candidate group, but you don't need to track how the group is shrinking, you only cares about the final result.
    public int findMaximumXOR(int[] nums) {
        int max = 0, mask = 0;
        for (int i = 31; i >= 0; i--) {
            mask = mask | (1 << i);
            Set<Integer> set = new HashSet<>();
            for (int num : nums) {
                set.add(num & mask);
            }
            int tmp = max | (1 << i);
            for (int prefix : set) {
                if (set.contains(tmp ^ prefix)) {
                    max = tmp;
                    break;
                }
            }
        }
        return max;
    }

    /**
     * https://leetcode.com/problems/bitwise-ors-of-subarrays/
     * @param A
     * @return
     */
    public int subarrayBitwiseORs(int[] A) {
        //if we get all result for 0..i, then for next i+1,
        //we apply it to all previous result with the bitwise or, and plus itself.
        Set<Integer> res = new HashSet<>(), cur = new HashSet<>(), cur2;
        for (Integer i: A) {
            cur2 = new HashSet<>();
            cur2.add(i);
            for (Integer j: cur) {
                cur2.add(i|j);
            }
            cur = cur2;
            res.addAll(cur);
        }
        return res.size();
    }
	
	/**
     * https://leetcode.com/problems/k-th-symbol-in-grammar/
     */
    public int kthGrammar(int N, int K) {
        if (N == 1 && K == 1) {
            return 0;
        }
        if (N == 2 && K == 1) {
            return 0;
        }
        if (N == 2 && K == 2) {
            return 1;
        }
        int prevPos = K/2 + K%2;
        int prevVal = kthGrammar(N-1, prevPos);
        if (K%2 == 0) {
            if (prevVal == 1) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if (prevVal == 1) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * https://leetcode.com/problems/maximum-length-of-a-concatenated-string-with-unique-characters/
     *
     * Given an array of strings arr. String s is a concatenation of a sub-sequence of arr which have unique characters.
     * Return the maximum possible length of s.
     * Example 1:
     * Input: arr = ["un","iq","ue"]
     * Output: 4
     * Explanation: All possible concatenations are "","un","iq","ue","uniq" and "ique".
     * Maximum length is 4.
     * Example 2:
     * Input: arr = ["cha","r","act","ers"]
     * Output: 6
     * Explanation: Possible solutions are "chaers" and "acters".
     * Example 3:
     * Input: arr = ["abcdefghijklmnopqrstuvwxyz"]
     * Output: 26
     *
     * Constraints:
     * 1 <= arr.length <= 16
     * 1 <= arr[i].length <= 26
     * arr[i] contains only lower case English letters.
     */
    public int maxLength(List<String> arr) {
        List<Integer> dp = new ArrayList<>();
        dp.add(0);
        int res = 0;
        for (String s : arr) {
            int a = 0, dup = 0;
            for (char c : s.toCharArray()) {
                dup |= a & (1 << (c - 'a'));
                a |= 1 << (c - 'a');
            }
            if (dup > 0) {
                continue;
            }
            for (int i = dp.size() - 1; i >= 0; --i) {
                if ((dp.get(i) & a) > 0) {
                    continue;
                }
                dp.add(dp.get(i) | a);
                res = Math.max(res, Integer.bitCount(dp.get(i) | a));
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/adding-two-negabinary-numbers/
     * Given two numbers arr1 and arr2 in base -2, return the result of adding them together.
     *
     * Each number is given in array format:  as an array of 0s and 1s, from most significant bit to least significant bit.
     * For example, arr = [1,1,0,1] represents the number (-2)^3 + (-2)^2 + (-2)^0 = -3.  A number arr in array format is also
     * guaranteed to have no leading zeros: either arr == [0] or arr[0] == 1.
     *
     * Return the result of adding arr1 and arr2 in the same format: as an array of 0s and 1s with no leading zeros.
     *
     * Example 1:
     *
     * Input: arr1 = [1,1,1,1,1], arr2 = [1,0,1]
     * Output: [1,0,0,0,0]
     * Explanation: arr1 represents 11, arr2 represents 5, the output represents 16.
     *
     *
     * Note:
     *
     * 1 <= arr1.length <= 1000
     * 1 <= arr2.length <= 1000
     * arr1 and arr2 have no leading zeros
     * arr1[i] is 0 or 1
     * arr2[i] is 0 or 1
     */
    public int[] addNegabinary(int[] arr1, int[] arr2) {
        int i = arr1.length - 1, j = arr2.length - 1, carry = 0;
        Stack<Integer> stack = new Stack<Integer>();
        while (i >= 0 || j >= 0 || carry != 0) {
            int v1 = i >= 0 ? arr1[i--] : 0;
            int v2 = j >= 0 ? arr2[j--] : 0;
            carry = v1 + v2 + carry;
            stack.push(carry & 1);
            carry = -(carry >> 1);
        }
        //Remove the leading 0.
        while (!stack.isEmpty() && stack.peek() == 0) {
            stack.pop();
        }
        int[] res = new int[stack.size()];
        int index = 0;
        while (!stack.isEmpty()) {
            res[index++] = stack.pop();
        }
        //if stack length 0, return 0.
        return res.length == 0 ? new int[1] : res;
    }

    /**
     * https://leetcode.com/problems/total-hamming-distance/
     * The Hamming distance between two integers is the number of positions at which the corresponding bits are different.
     *
     * Now your job is to find the total Hamming distance between all pairs of the given numbers.
     *
     * Example:
     * Input: 4, 14, 2
     *
     * Output: 6
     *
     * Explanation: In binary representation, the 4 is 0100, 14 is 1110, and 2 is 0010 (just
     * showing the four bits relevant in this case). So the answer will be:
     * HammingDistance(4, 14) + HammingDistance(4, 2) + HammingDistance(14, 2) = 2 + 2 + 2 = 6.
     * Note:
     * Elements of the given array are in the range of 0 to 10^9
     * Length of the array will not exceed 10^4.
     *
     */
    //For each bit position 1-32 in a 32-bit integer, we count the number of integers
    // in the array which have that bit set. Then, if there are n integers in the array
    // and k of them have a particular bit set and (n-k) do not,
    // then that bit contributes k*(n-k) hamming distance to the total.
    public int totalHammingDistance(int[] nums) {
        int total = 0, n = nums.length;
        for (int j=0; j<32; j++) {
            int bitCount = 0;
            for (int i=0;i<n;i++) {
                bitCount += (nums[i] >> j) & 1;
            }
            //every 1 in the bitcount, will contribute n-bitcount to the total distance
            total += bitCount*(n - bitCount);
        }
        return total;
    }

    /**
     * https://leetcode.com/problems/divide-two-integers/
     *
     * Given two integers dividend and divisor, divide two integers without using multiplication, division and mod operator.
     *
     * Return the quotient after dividing dividend by divisor.
     *
     * The integer division should truncate toward zero, which means losing its fractional part. For example, truncate(8.345) = 8 and truncate(-2.7335) = -2.
     */
    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        int a = Math.abs(dividend);
        int b = Math.abs(divisor);
        int res = 0;
        while (a - b >= 0) {
            int x = 0;
            //increase the x, to multiply b till it is bigger than a.
            while (a - (b << 1 << x) >= 0) {
                x++;
            }
            res += 1 << x;
            //subtract the b << x in a, start over again.
            a -= b << x;
        }
        return (dividend >= 0) == (divisor >= 0) ? res : -res;
    }

    /**
     * https://leetcode.com/problems/encode-number/
     */
    public String encode(int num) {
        if (num == 0) {
            return "";
        }
        int c = 0;
        int temp = 1;
        int total = 0;
        while (total + temp <= num) {
            total = total + temp;
            c++;
            temp = temp * 2;
        }
        int decimalVal = num - total;
        String result = String.valueOf(Integer.toBinaryString(decimalVal));
        StringBuilder sb = new StringBuilder();
        if (c > result.length()) {
            for (int i=0; i<c-result.length(); i++) {
                sb.append("0");
            }
        }
        return sb.append(result).toString();
    }

    /**
     * https://leetcode.com/problems/utf-8-validation/
     *
     * A character in UTF8 can be from 1 to 4 bytes long, subjected to the following rules:
     *
     * For 1-byte character, the first bit is a 0, followed by its unicode code.
     * For n-bytes character, the first n-bits are all one's, the n+1 bit is 0, followed by n-1 bytes with most significant 2 bits being 10.
     * This is how the UTF-8 encoding would work:
     *
     *    Char. number range  |        UTF-8 octet sequence
     *       (hexadecimal)    |              (binary)
     *    --------------------+---------------------------------------------
     *    0000 0000-0000 007F | 0xxxxxxx
     *    0000 0080-0000 07FF | 110xxxxx 10xxxxxx
     *    0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx
     *    0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
     * Given an array of integers representing the data, return whether it is a valid utf-8 encoding.
     *
     * Note:
     * The input is an array of integers. Only the least significant 8 bits of each integer is used to store the data.
     * This means each integer represents only 1 byte of data.
     *
     * Example 1:
     *
     * data = [197, 130, 1], which represents the octet sequence: 11000101 10000010 00000001.
     *
     * Return true.
     * It is a valid utf-8 encoding for a 2-bytes character followed by a 1-byte character.
     *
     * Example 2:
     *
     * data = [235, 140, 4], which represented the octet sequence: 11101011 10001100 00000100.
     *
     * Return false.
     *
     * The first 3 bits are all one's and the 4th bit is 0 means it is a 3-bytes character.
     * The next byte is a continuation byte which starts with 10 and that's correct.
     * But the second continuation byte does not start with 10, so it is invalid.
     * @param data
     * @return
     */
    public boolean validUtf8(int[] data) {
        int cnt = 0; //count of invalid input number
        for (int d : data) {
            if (cnt == 0) {
                if((d>>5) == 0b110){
                    cnt = 1; //110 pattern, which need next 1 number to complete the char.
                } else {
                    if((d>>4) == 0b1110) {
                        cnt=2; //1110 pattern, which need next 2 number to complete the char.
                    } else {
                        if((d>>3) == 0b11110) {
                            cnt=3; //11110 pattern, which need next 3 number to complete the char.
                        } else {
                            if((d>>7) != 0) {
                                return false; //Even not matching with first one, fail.
                            }
                        }
                    }
                }
            } else {
                if((d>>6) != 0b10) { //must be the supplement of incomplete char, pattern be 10.
                    return false;
                }
                cnt--;
            }
        }
        return cnt == 0;
    }

    /**
     * https://leetcode.com/problems/ip-to-cidr/
     *
     * https://leetcode.com/problems/ip-to-cidr/discuss/151348/Java-Solution-with-Very-Detailed-Explanation-8ms
     *
     * Given a start IP address ip and a number of ips we need to cover n,
     * return a representation of the range as a list (of smallest possible length) of CIDR blocks.
     *
     * A CIDR block is a string consisting of an IP, followed by a slash, and then the prefix length.
     * For example: "123.45.67.89/20". That prefix length "20" represents the number of common prefix bits in the specified range.
     *
     * @param ip
     * @param n
     * @return
     */
    public List<String> ipToCIDR(String ip, int n) {
        int cur = toInt(ip);
        List<String> res = new ArrayList<>();
        while(n>0){
            int maxBits = Integer.numberOfTrailingZeros(cur);
            int maxAmount = 1<<maxBits;
            int bitVal = 1;
            int count = 0;
            while(bitVal< n && count< maxBits){
                bitVal<<=1;
                ++count;
            }
            if(bitVal>n){
                bitVal>>=1;
                --count;
            }
            res.add(toString(cur,32-count));
            n-= bitVal;
            cur+=(bitVal);
        }
        return res;
    }
    private String toString(int number, int range){
        //convert every 8 into an integer
        final int WORD_SIZE = 8;
        StringBuilder sb = new StringBuilder();
        for(int i=3; i>=0; --i){
            sb.append(Integer.toString(((number>>(i*WORD_SIZE))&255)));
            sb.append(".");
        }
        sb.setLength(sb.length()-1);
        sb.append("/");
        sb.append(Integer.toString(range));
        return sb.toString();
    }
    private int toInt(String ip){
        String [] sep = ip.split("\\.");
        int sum = 0;
        for(int i=0; i<sep.length;++i){
            sum*=256;
            sum+=Integer.parseInt(sep[i]);
        }
        return sum;
    }

    /**
     * https://leetcode.com/problems/similar-rgb-color/
     * @param color
     * @return
     */
    Map<Character, Integer> values = new HashMap<Character, Integer>();
    Map<Integer, Character> reverse = new HashMap<Integer, Character>();
    public String similarRGB(String color) {
        values.put('0', 0);
        values.put('1', 1);
        values.put('2', 2);
        values.put('3', 3);
        values.put('4', 4);
        values.put('5', 5);
        values.put('6', 6);
        values.put('7', 7);
        values.put('8', 8);
        values.put('9', 9);
        values.put('a', 10);
        values.put('b', 11);
        values.put('c', 12);
        values.put('d', 13);
        values.put('e', 14);
        values.put('f', 15);
        reverse.put(0, '0');
        reverse.put(1, '1');
        reverse.put(2, '2');
        reverse.put(3, '3');
        reverse.put(4, '4');
        reverse.put(5, '5');
        reverse.put(6, '6');
        reverse.put(7, '7');
        reverse.put(8, '8');
        reverse.put(9, '9');
        reverse.put(10, 'a');
        reverse.put(11, 'b');
        reverse.put(12, 'c');
        reverse.put(13, 'd');
        reverse.put(14, 'e');
        reverse.put(15, 'f');
        String sub1 = "";
        String sub2 = "";
        String sub3 = "";
        if (color.length() < 7) {
            sub1 = color.substring(1,2) + color.substring(1,2);
            sub2 = color.substring(2,3) + color.substring(2,3);
            sub3 = color.substring(3,4) + color.substring(3,4);
        } else {
            sub1 = color.substring(1,3);
            sub2 = color.substring(3,5);
            sub3 = color.substring(5, 7);
        }
        String ret1 = getClosest(sub1);
        String ret2 = getClosest(sub2);
        String ret3 = getClosest(sub3);
        return "#"+ret1+ret2+ret3;
    }

    private String getClosest(String s) {
        if (s.charAt(0) == s.charAt(1)) {
            return s;
        }
        int s1 = values.get(s.charAt(0)) * 16 + values.get(s.charAt(1));
        int toChange = 1;
        while ((s1 - toChange)/16 != (s1 - toChange)%16 && (s1 + toChange)/16 != (s1 + toChange)%16) {
            toChange++;
        }
        if ((s1 - toChange)/16 == (s1 - toChange)%16) {
            return String.valueOf(reverse.get((s1 - toChange)/16)) + String.valueOf(reverse.get((s1 - toChange)/16));
        } else {
            return String.valueOf(reverse.get((s1 + toChange)/16)) + String.valueOf(reverse.get((s1 + toChange)/16));
        }
    }


    /**
     * https://leetcode.com/problems/single-number-iii/
     * Given an array of numbers nums, in which exactly two elements appear only
     * once and all the other elements appear exactly twice. Find the two elements that appear only once.
     *
     * Example:
     *
     * Input:  [1,2,1,3,2,5]
     * Output: [3,5]
     * Note:
     *
     * The order of the result is not important. So in the above example, [5, 3] is also correct.
     * Your algorithm should run in linear runtime complexity.
     * Could you implement it using only constant space complexity?
     *
     * Let a and b be the two unique numbers
     * XORing all numbers gets you (a xor b)
     * (a xor b) must be non-zero otherwise they are equal
     * If bit_i in (a xor b) is 1, bit_i at a and b are different.
     * Find bit_i using the low bit formula m & -m
     * Partition the numbers into two groups: one group with bit_i == 1 and the other group with bit_i == 0.
     * a is in one group and b is in the other.
     * a is the only single number in its group.
     * b is also the only single number in its group.
     * XORing all numbers in a's group to get a
     * XORing all numbers in b's group to get b
     * Alternatively, XOR (a xor b) with a gets you b.
     *
     * The two numbers that appear only once must differ at some bit, this is how we can distinguish between them. Otherwise, they will be one of the duplicate numbers.
     *
     * One important point is that by XORing all the numbers, we actually get the XOR of the two target numbers (because XORing two duplicate numbers always results in 0).
     *
     * Consider the XOR result of the two target numbers; if some bit of the XOR result is 1, it means that the two target numbers differ at that location.
     *
     * Let’s say the at the ith bit, the two desired numbers differ from each other. which means one number has bit i equaling: 0, the other number has bit i equaling 1.
     *
     * Thus, all the numbers can be partitioned into two groups according to their bits at location i.
     * the first group consists of all numbers whose bits at i is 0.
     * the second group consists of all numbers whose bits at i is 1.
     *
     * Notice that, if a duplicate number has bit i as 0, then, two copies of it will belong to the first group. Similarly, if a duplicate number has bit i as 1,
     * then, two copies of it will belong to the second group.
     *
     * by XoRing all numbers in the first group, we can get the first number.
     * by XoRing all numbers in the second group, we can get the second number.
     *
     */
    public int[] singleNumber(int[] nums) {
        // Pass 1 :
        // Get the XOR of the two numbers we need to find
        int diff = 0;
        for (int num : nums) {
            diff ^= num;
        }
        int counter = 1;
        while (diff != 0) {
            if (diff%2 == 0) {
                diff = diff / 2;
                counter = counter*2;
            } else {
                break;
            }
        }
        // Get its last set bit
        //diff &= -diff;

        // Pass 2 :
        int[] rets = {0, 0}; // this array stores the two numbers we will return
        for (int num : nums){
            if ((num & counter) == 0) { // the bit is not set
                rets[0] ^= num;
            } else{// the bit is set
                rets[1] ^= num;
            }
        }
        return rets;
    }

    /**
     * https://leetcode.com/problems/convert-a-number-to-hexadecimal/
     */
    public String toHex(int num) {
        if (num == 0) return "0";
        StringBuilder res = new StringBuilder();

        while (num != 0) {
            int digit = num & 0xf;
            res.append(digit < 10 ? (char)(digit + '0') : (char)(digit - 10 + 'a'));
            num >>>= 4;
        }

        return res.reverse().toString();
    }
    /**
     * https://leetcode.com/problems/circular-permutation-in-binary-representation/
     *
     * NOTE: GRAY CODE
     * https://www.geeksforgeeks.org/generate-n-bit-gray-codes-set-2/
     *
     * @param n
     * @param start
     * @return
     */
    public List<Integer> circularPermutation(int n, int start) {
        List<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i < 1 << n; ++i)
            res.add(start ^ i ^ i >> 1);
        return res;
    }

    /**
     * https://leetcode.com/problems/minimum-flips-to-make-a-or-b-equal-to-c/
     * Given 3 positives numbers a, b and c. Return the minimum flips required in
     * some bits of a and b to make ( a OR b == c ). (bitwise OR operation).
     * Flip operation consists of change any single bit 1 to 0 or change the bit
     * 0 to 1 in their binary representation.
     *
     * Example 1:
     * Input: a = 2, b = 6, c = 5
     * Output: 3
     * Explanation: After flips a = 1 , b = 4 , c = 5 such that (a OR b == c)
     * Example 2:
     *
     * Input: a = 4, b = 2, c = 7
     * Output: 1
     * Example 3:
     *
     * Input: a = 1, b = 2, c = 3
     * Output: 0
     * @param a
     * @param b
     * @param c
     * @return
     */
    public int minFlips(int a, int b, int c) {
        int ans = 0, ab = a | b, equal = ab ^ c;
        for (int i = 0; i < 31; ++i) { // to 32 bit, maximum number tested.
            int mask = 1 << i;
            // i-th bits of a | b and c are not same, need at least 1 flip.
            if ((equal & mask) > 0) {
                // if i-th bits of a | b and c are not same, then only if ith bits of a and b are both 1 and that of c is 0, we need 2 flips;
                // otherwise only 1 flip needed.
                ans += (a & mask) == (b & mask) && (c & mask) == 0 ? 2 : 1;
            }
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/xor-queries-of-a-subarray/
     * Given the array arr of positive integers and the array queries where queries[i] = [Li, Ri],
     * for each query i compute the XOR of elements from Li to Ri (that is, arr[Li] xor arr[Li+1] xor ... xor arr[Ri] ).
     * Return an array containing the result for the given queries.
     *
     *
     * Example 1:
     *
     * Input: arr = [1,3,4,8], queries = [[0,1],[1,2],[0,3],[3,3]]
     * Output: [2,7,14,8]
     * Explanation:
     * The binary representation of the elements in the array are:
     * 1 = 0001
     * 3 = 0011
     * 4 = 0100
     * 8 = 1000
     * The XOR values for queries are:
     * [0,1] = 1 xor 3 = 2
     * [1,2] = 3 xor 4 = 7
     * [0,3] = 1 xor 3 xor 4 xor 8 = 14
     * [3,3] = 8
     *
     * Example 2:
     *
     * Input: arr = [4,8,2,10], queries = [[2,3],[1,3],[0,0],[0,3]]
     * Output: [8,0,4,4]
     * @param arr
     * @param queries
     * @return
     */
    public int[] xorQueries(int[] arr, int[][] queries) {
        int[] ret = new int[queries.length];
        //n ^ n = 0
        //n ^ 0 = n
        //-> x ^ x ^ y = y
        int[] xorSum = new int[arr.length];
        xorSum[0] = arr[0];
        for (int i=1; i<arr.length; i++) {
            xorSum[i] = arr[i] ^ xorSum[i-1];
        }
        for (int i=0; i<ret.length; i++) {
            if (queries[i][0] == queries[i][1]) {
                ret[i] = arr[queries[i][0]];
                continue;
            }
            if (queries[i][0] == 0) {
                ret[i] = xorSum[queries[i][1]];
            } else {
                ret[i] = xorSum[queries[i][0] - 1] ^ xorSum[queries[i][1]];;
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/score-after-flipping-matrix/
     * We have a two dimensional matrix A where each value is 0 or 1.
     *
     * A move consists of choosing any row or column, and toggling each value in that row or column: changing all 0s to 1s, and all 1s to 0s.
     *
     * After making any number of moves, every row of this matrix is interpreted as a binary number, and the score of the matrix is the sum of these numbers.
     *
     * Return the highest possible score.
     *
     *
     *
     * Example 1:
     *
     * Input: [
     * [0,0,1,1],
     * [1,0,1,0],
     * [1,1,0,0]]
     * Output: 39
     * Explanation:
     * Toggled to [
     * [1,1,1,1],
     * [1,0,0,1],
     * [1,1,1,1]].
     * 0b1111 + 0b1001 + 0b1111 = 15 + 9 + 15 = 39
     *
     *
     * Note:
     *
     * 1 <= A.length <= 20
     * 1 <= A[0].length <= 20
     * A[i][j] is 0 or 1.
     *
     * @param A
     * @return
     */
    public int matrixScore(int[][] A) {
        //Flip all first position to 1.
        int m = A.length;
        int n = A[0].length;
        for (int i=0; i<m; i++) {
            if (A[i][0] == 0) {
                for (int j = 0; j<n; j++) {
                    A[i][j] = A[i][j] == 0 ? 1 : 0;
                }
            }
        }
        for (int j=1; j<n; j++) {
            int countZero = 0;
            for (int i=0; i<m; i++) {
                if (A[i][j] == 0) {
                    countZero++;
                }
            }
            if (countZero > m/2) {
                for (int i=0; i<m; i++) {
                    A[i][j] = A[i][j] == 0 ? 1 : 0;
                }
            }
        }
        int ret = 0;
        for (int j=0; j<n; j++) {
            for (int i=0; i<m; i++) {
                ret = ret + A[i][j] * (int)Math.pow(2, n-1-j);
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/game-of-life/
     *
     * To solve it in place, we use 2 bits to store 2 states:
     *
     * [2nd bit, 1st bit] = [next state, current state]
     *
     * - 00  dead (next) <- dead (current)
     * - 01  dead (next) <- live (current)
     * - 10  live (next) <- dead (current)
     * - 11  live (next) <- live (current)
     * In the beginning, every cell is either 00 or 01.
     * Notice that 1st state is independent of 2nd state.
     * Imagine all cells are instantly changing from the 1st to the 2nd state, at the same time.
     * Let's count # of neighbors from 1st state and set 2nd state bit.
     * Since every 2nd state is by default dead, no need to consider transition 01 -> 00.
     * In the end, delete every cell's 1st state by doing >> 1.
     */
    public void gameOfLife(int[][] board) {
        if (board == null || board.length == 0) return;
        int m = board.length, n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int lives = liveNeighbors(board, m, n, i, j);
                // In the beginning, every 2nd bit is 0;
                // So we only need to care about when will the 2nd bit become 1.
                if (board[i][j] == 1 && lives >= 2 && lives <= 3) {
                    board[i][j] = 3; // Make the 2nd bit 1: 01 ---> 11
                }
                if (board[i][j] == 0 && lives == 3) {
                    board[i][j] = 2; // Make the 2nd bit 1: 00 ---> 10
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] >>= 1;  // Get the 2nd state.
            }
        }
    }

    public int[][] dirs = {{0,1},{1,0},{-1,0},{0,-1},{1,1},{-1,-1},{1,-1},{-1,1}};

    public int liveNeighbors(int[][] board, int m, int n, int i, int j) {
        int lives = 0;
        for (int[] dir : dirs) {
            int nx = dir[0] + i;
            int ny = dir[1] + j;
            if (nx >=0 && nx < m && ny >= 0 && ny < n) {
                lives += board[nx][ny] & 1;
            }
        }
        return lives;
    }

    /**
     * https://leetcode.com/problems/gray-code/
     *
     * The gray code is a binary numeral system where two successive values differ in only one bit.
     *
     * Given a non-negative integer n representing the total number of bits in the code, print the sequence of gray code. A gray code sequence must begin with 0.
     *
     * Example 1:
     *
     * Input: 2
     * Output: [0,1,3,2]
     * Explanation:
     * 00 - 0
     * 01 - 1
     * 11 - 3
     * 10 - 2
     * @param n
     * @return
     */
    public List<Integer> grayCode(int n) {
        List<Integer> rs=new ArrayList<Integer>();
        rs.add(0);
        //A key logic jump: every next range of numbers can be concluded by reversely sequentially to add 1 to the leftmost for all existing numbers.
        //Here the i represents the number of bits.
        for(int i=0;i<n;i++){
            int size=rs.size();
            int in = 1<<i;
            for(int k=size-1;k>=0;k--) {
                int next = rs.get(k) | in;
                rs.add(next);
            }
        }
        return rs;
    }

    /**
     * https://leetcode.com/problems/binary-prefix-divisible-by-5/
     *
     * https://leetcode.com/problems/binary-prefix-divisible-by-5/discuss/265601/Detailed-Explanation-using-Modular-Arithmetic-O(n)
     *
     * @param A
     * @return
     */
    public List<Boolean> prefixesDivBy5(int[] A) {
        List<Boolean> ret = new ArrayList<Boolean>();
        int val = A[0];
        ret.add(val%5==0);
        for (int i=1; i<A.length; i++) {
            val = (val*2 + A[i])%5;
            ret.add(val==0);
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/prime-number-of-set-bits-in-binary-representation/
     * @param L
     * @param R
     * @return
     */
    public int countPrimeSetBits(int L, int R) {
        //32 bit, in range of 10^6, so maxium we have these possible number of bits as prime number.
        Set<Integer> primeSet = new HashSet<Integer>(Arrays.asList(2,3,5,7,11,13,17,19,23));
        int ret = 0;
        for (int n = L; n <= R; n++) {
            int counter = 0;
            int num = n;
            while (num != 0) {
                if ((num&1) == 1) {
                    counter++;
                }
                num = num>>1;
            }
            if (primeSet.contains(counter)) {
                ret++;
            }
        }
        return ret;
    }

    /**
     * Given a positive integer N, find and return the longest distance between two consecutive 1's in the binary
     * representation of N.
     *
     * If there aren't two consecutive 1's, return 0.
     *
     *
     *
     * Example 1:
     *
     * Input: 22
     * Output: 2
     * Explanation:
     * 22 in binary is 0b10110.
     *
     * https://leetcode.com/problems/binary-gap/
     * @param n
     * @return
     */
    public int binaryGap(int n) {
        int counter = 0;
        int previousOneIdx = -1;
        int ret = 0;
        while (n != 0) {
            if ((n&1) == 1) {
                if (previousOneIdx != -1) {
                    ret = Math.max(counter - previousOneIdx, ret);
                }
                previousOneIdx = counter;
            }
            n = n>>1;
            counter++;
        }
        return ret;
    }

    /**
     * Given a positive integer, check whether it has alternating bits: namely,
     * if two adjacent bits will always have different values.
     *
     * Example 1:
     * Input: 5
     * Output: True
     * Explanation:
     * The binary representation of 5 is: 101
     *
     * https://leetcode.com/problems/binary-number-with-alternating-bits/
     * @param n
     * @return
     */
    public boolean hasAlternatingBits(int n) {
        boolean previousIsOne = false, previousIsZero = false;
        while (n != 0) {
            if ((n&1) == 1) {
                if (previousIsOne) {
                    return false;
                }
                previousIsOne = true;
                previousIsZero = false;
            } else {
                if (previousIsZero) {
                    return false;
                }
                previousIsZero = true;
                previousIsOne = false;
            }
            n = n>>1;
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/number-complement/
     * @param num
     * @return
     */
    public int findComplement(int num) {
        int ret = 0;
        int counter = 0;
        while (num != 0) {
            if ((num&1) == 0) {
                ret = ret + (int)Math.pow(2, counter);
            }
            num = num>>1;
            counter++;
        }
        return ret;
    }

    /**
     * Reverse bits of a given 32 bits unsigned integer.
     *
     *
     *
     * Example 1:
     *
     * Input: 00000010100101000001111010011100
     * Output: 00111001011110000010100101000000
     * Explanation: The input binary string 00000010100101000001111010011100 represents the unsigned
     * integer 43261596, so return 964176192 which its binary representation is 00111001011110000010100101000000.
     *
     * https://leetcode.com/problems/reverse-bits/
     * @param n
     * @return
     */
    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result += n & 1;
            n >>>= 1;   // CATCH: must do unsigned shift
            if (i < 31) // CATCH: for last digit, don't shift!
                result <<= 1;
        }
        return result;
    }

    /**
     * Given a non negative integer number num. For every numbers i in the range 0 ≤ i ≤ num
     * calculate the number of 1's in their binary representation and return them as an array.
     *
     * Example 1:
     *
     * Input: 2
     * Output: [0,1,1]
     * https://leetcode.com/problems/counting-bits/
     * @param num
     * @return
     */
    public int[] countBits(int num) {
        int[] f = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            //example: 000110, 000111, they can be based on 00011 + (i&1)
            f[i] = f[i >> 1] + (i & 1);
        }
        return f;
    }

    public int[] countBits_BruteForce(int num) {
        int[] ret = new int[num+1];
        if (num == 0) {
            ret[0] = 0;
            return ret;
        }
        for (int i=1; i<=num; i++) {
            int n = i;
            int count = 0;
            while (n != 0) {
                if ((n&1) == 1) {
                    count++;
                }
                n = n>>1;
            }
            ret[i] = count;
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/power-of-four/
     * Example 1:
     *
     * Input: 16
     * Output: true
     *
     * Example 2:
     *
     * Input: 5
     * Output: false
     * @param num
     * @return
     */
    public boolean isPowerOfFour(int num) {
        if (num == 1) {
            return true;
        }
        int zeroCount = 0;
        while (num != 0) {
            if (num != 1) {
                int temp = ~num;
                int re = temp&1;
                if (re == 0) {
                    return false;
                } else {
                    zeroCount++;
                }
            }
            num = num >> 1;
        }
        if (zeroCount > 0 && zeroCount%2 == 0) {
            return true;
        }
        return false;
    }

    public int bitwiseComplement(int N) {
        if (N == 0) {
            return 1;
        }
        int ans = 0;
        int curr = 1;
        while(N != 0){
            if(N % 2 == 0) {
                ans = ans + curr;
            }
            N = N/2;
            curr = curr * 2;
        }
        return ans;
    }
}
