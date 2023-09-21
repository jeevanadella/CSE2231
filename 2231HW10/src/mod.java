import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Jeevan Nadella
 *
 */
public final class mod {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private mod() {
    }

    /**
     * Computes {@code a} mod {@code b} as % should have been defined to work.
     *
     * @param a
     *            the number being reduced
     * @param b
     *            the modulus
     * @return the result of a mod b, which satisfies 0 <= {@code mod} < b
     * @requires b > 0
     * @ensures <pre>
     * 0 <= mod  and  mod < b  and
     * there exists k: integer (a = k * b + mod)
     * </pre>
     */
    public static int mod(int a, int b) {

        int aHold = a, bHold = b;

        while (a > b) {
            a -= b;
        }

        int mod = a;
        a = aHold;
        b = bHold;

        if (mod < 0) {
            mod *= -1;
        }

        return mod;
    }

    /**
     * counts digits of a number
     *
     * @param a
     *            the number being reduced
     * @param b
     *            the modulus
     * @return the result of a mod b, which satisfies 0 <= {@code mod} < b
     * @requires b > 0
     * @ensures <pre>
     * 0 <= mod  and  mod < b  and
     * there exists k: integer (a = k * b + mod)
     * </pre>
     */
    public static int hashCode(int a) {

        int count = 0, num = a;

        if (num < 0) {
            num *= -1;
        }

        while (num > 0) {
            num /= 10;
            count++;
        }

        return count;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Put your main program code here; it may call myMethod as shown
         */
        mod(a, b);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
