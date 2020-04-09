Important DP problem pattern

Distinct ways
https://leetcode.com/problems/decode-ways/
https://leetcode.com/problems/fibonacci-number/
https://leetcode.com/problems/climbing-stairs/
https://leetcode.com/problems/unique-paths/


DP on String, with HashMap

Longest String Chain
https://leetcode.com/problems/longest-string-chain/
Sort the string by length first, then we can use a map as DP to store for each word, the longest str chain.

    public int longestStrChain(String[] words) {
        //dp map store for each word, the longest str chain.
        Map<String, Integer> dp = new HashMap<>();
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        int res = 0;
        for (String word : words) {
            int best = 0;
            for (int i = 0; i < word.length(); ++i) {
                String prev = word.substring(0, i) + word.substring(i + 1);
                best = Math.max(best, dp.getOrDefault(prev, 0) + 1);
            }
            dp.put(word, best);
            res = Math.max(res, best);
        }
        return res;
    }
    
    

Word break
https://leetcode.com/problems/word-break/
https://leetcode.com/problems/concatenated-words/
