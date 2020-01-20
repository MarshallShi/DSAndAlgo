package dsandalgo.bit;


/**
 * Approach is to get gray code of binary number using XOR and Right shift operation.
 *
 * The first bit(MSB) of the gray code is same as the first bit(MSB) of binary number.
 *
 * The second bit(from left side) of the gray code equals to XOR of first bit(MSB) and second bit(2nd MSB) of the binary number.
 *
 * The third bit(from left side) of the gray code equals to XOR of the second bit(2nd MSB) and third bit(3rd MSB) and so on..
 *
 * In this way we can get the gray code of corresponding binary number. So, observation is that to get i’th bit gray code,
 * we have to do XOR operation of i’th bit and previous i-1’th bit. So by doing right shift, i-1’th bit and i’th bit come at same position and
 * the XOR result of i’th bit and i-1’th bit will be the i’th bit gray code.That’s the intuition behind right shift operation.
 *
 */
public class GrayCode {

    // Function to convert
    // decimal to binary
    public static void decimalToBinaryNumber(int x, int n){
        int []binaryNumber = new int[x];
        int i = 0;
        while (x > 0){
            binaryNumber[i] = x % 2;
            x = x / 2;
            i++;
        }

        // leftmost digits are
        // filled with 0
        for (int j = 0; j < n - i; j++)
            System.out.print('0');

        for (int j = i - 1; j >= 0; j--)
            System.out.print(binaryNumber[j]);
    }

    // Function to generate
    // gray code
    static void generateGrayarr(int n)
    {
        int N = 1 << n;
        for (int i = 0; i < N; i++) {
            // generate gray code of
            // corresponding binary
            // number of integer i.
            int x = i ^ (i >> 1);

            // printing gray code
            decimalToBinaryNumber(x, n);

            System.out.println();
        }
    }

    // Driver code
    public static void main (String[] args) {
        int n = 3;
        generateGrayarr(n);
    }
}
