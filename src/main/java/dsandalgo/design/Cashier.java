package dsandalgo.design;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/apply-discount-every-n-orders/
 */
public class Cashier {

    private int customerCount = 0;
    private int custN = 0;
    private Map<Integer, Integer> prodPrices;
    private int dis = 0;

    public Cashier(int n, int discount, int[] products, int[] prices) {
        prodPrices = new HashMap<>();
        for (int i=0; i<products.length; i++) {
            prodPrices.put(products[i], prices[i]);
        }
        custN = n;
        dis = discount;
    }

    public double getBill(int[] product, int[] amount) {
        double ret = 0;
        customerCount++;
        if (customerCount == custN) {
            //apply discount
            for (int i=0; i<product.length; i++) {
                ret += amount[i]*(prodPrices.get(product[i]) - (double)dis*prodPrices.get(product[i])/100);
            }
            customerCount = 0;
        } else {
            for (int i=0; i<product.length; i++) {
                ret += amount[i]*(double)prodPrices.get(product[i]);
            }
        }
        return ret;
    }
}
