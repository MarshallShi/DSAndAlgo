package dsandalgo;

public class GreedyExe{

    public static void main(String[] args) {
        GreedyExe exe = new GreedyExe();

        int[] aa = {10,10};
        System.out.println(exe.lemonadeChange(aa));
    }
    /**
     * Input: [5,5,10,10,20]
     * Output: false
     * Explanation:
     * From the first two customers in order, we collect two $5 bills.
     * For the next two customers in order, we collect a $10 bill and give back a $5 bill.
     * For the last customer, we can't give change of $15 back because we only have two $10 bills.
     * Since not every customer received correct change, the answer is false.
     *
     * https://leetcode.com/problems/lemonade-change/
     * @param bills
     * @return
     */
    public boolean lemonadeChange(int[] bills) {
        int five = 0, ten = 0, twenty = 0;
        for (int i=0; i<bills.length; i++) {
            if (bills[i] == 5) {
                five++;
            } else {
                if (bills[i] == 10) {
                    five--;
                    ten++;
                } else {
                    if (bills[i] == 20) {
                        if (ten > 0) {
                            ten--;
                            five--;
                        } else {
                            five = five - 3;
                        }
                    }
                }
            }
            if (five < 0) {
                return false;
            }
        }
        return true;
    }
}
