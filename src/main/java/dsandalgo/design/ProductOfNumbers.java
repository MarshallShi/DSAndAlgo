package dsandalgo.design;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/product-of-the-last-k-numbers/
 *
 * Same idea of the presum, this is for preproduct.
 */
public class ProductOfNumbers {

    List<Integer> preprod;
    public ProductOfNumbers() {
        add(0);
    }

    public void add(int a) {
        if (a > 0)
            preprod.add(preprod.get(preprod.size() - 1) * a);
        else {
            preprod = new ArrayList<Integer>();
            preprod.add(1);
        }
    }

    public int getProduct(int k) {
        int n = preprod.size();
        if (k<n) {
            return preprod.get(n - 1) / preprod.get(n - k - 1);
        } else {
            return 0;
        }
    }
}
