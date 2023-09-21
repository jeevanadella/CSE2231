import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumberSecondary;

/**
 * {@code NaturalNumber} represented as a {@code String} with implementations of
 * primary methods.
 *
 * @convention <pre>
 * [all characters of $this.rep are '0' through '9']  and
 * [$this.rep does not start with '0']
 * </pre>
 * @correspondence <pre>
 * this = [if $this.rep = "" then 0
 *         else the decimal number whose ordinary depiction is $this.rep]
 * </pre>
 *
 * @author Jeevan Nadella, Adam Khalil
 *
 */
public class NaturalNumber3 extends NaturalNumberSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Representation of {@code this}.
     */
    private String rep;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        this.rep = "";

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public NaturalNumber3() {
        this.createNewRep();

    }

    /**
     * Constructor from {@code int}.
     *
     * @param i
     *            {@code int} to initialize from
     */
    public NaturalNumber3(int i) {
        assert i >= 0 : "Violation of: i >= 0";

        // creating vars
        char c = ' ';
        String rS = "";

        this.createNewRep();

        // if input is >1, turn into string and sets as value of rep
        if (i > 0) {
            String s = Integer.toString(i);
            for (int j = 0; j < s.length(); j++) {
                c = s.charAt(j); //extracts character
                rS = c + rS; //concatenates each character in front
                this.rep = rS;
            }
        } else {
            // if value is not >1, leaves rep blank
            this.rep = "";
        }

    }

    /**
     * Constructor from {@code String}.
     *
     * @param s
     *            {@code String} to initialize from
     */
    public NaturalNumber3(String s) {
        assert s != null : "Violation of: s is not null";
        assert s.matches("0|[1-9]\\d*") : ""
                + "Violation of: there exists n: NATURAL (s = TO_STRING(n))";

        //creating variables
        char c = ' ';
        String rS = "";

        this.createNewRep();
        //if the string is 0 leave empty otherwise
        if (!s.equals("0")) {
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i); //extracts character
                rS = c + rS; //concatenates each character in front
            }
        }
        //save reversed string as the natural number
        this.rep = rS;

    }

    /**
     * Constructor from {@code NaturalNumber}.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     */
    public NaturalNumber3(NaturalNumber n) {
        assert n != null : "Violation of: n is not null";

        // creating vars
        char c = ' ';
        String rS = "";

        this.createNewRep();

        // if input is >1, than converts to string and assigns to rep
        if (!n.isZero()) {
            String s = n.toString();
            for (int j = 0; j < s.length(); j++) {
                c = s.charAt(j); //extracts character
                rS = c + rS; //concatenates each character in front
                this.rep = rS;
            }
        } else {
            // if input is not >1, then leaves rep blank
            this.rep = "";
        }

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final NaturalNumber newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(NaturalNumber source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof NaturalNumber3 : ""
                + "Violation of: source is of dynamic type NaturalNumberExample";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case.
         */
        NaturalNumber3 localSource = (NaturalNumber3) source;
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void multiplyBy10(int k) {

        if (k > 0) {
            String s = Integer.toString(k);
            this.rep = s.concat(this.rep);
        } else if (this.rep != "") {
            String s = Integer.toString(k);
            this.rep = s.concat(this.rep);
        }

    }

    @Override
    public final int divideBy10() {

        //Saving the first digit of the naturalnumber
        char first = this.rep.charAt(0);
        char c;
        String s = "";
        for (int i = 0; i < (this.rep.length() - 1); i++) {
            c = this.rep.charAt(i + 1); //extracts characters
            s = s + c; //concatenates each character in front
        }
        //updating natural number
        this.rep = s;
        //turning the first digit into a int and returning it
        int value = Character.getNumericValue(first);
        return value;
    }

    @Override
    public final boolean isZero() {

        boolean value = false;
        //if the string is empty then the value is zero
        if (this.rep.length() == 0) {
            value = true;
        }
        return value;
    }

}
