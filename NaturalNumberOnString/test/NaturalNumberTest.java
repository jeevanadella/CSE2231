import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Jeevan Nadella, Adam Khalil
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    // TODO - add test cases for four constructors, multiplyBy10, divideBy10, isZero

    /*
     * Test empty constructor
     */

    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber q = this.constructorTest();
        NaturalNumber qExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /*
     * Test int constructor
     */

    @Test
    public final void testIntConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber q = this.constructorTest(1234);
        NaturalNumber qExpected = this.constructorRef("1234");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /*
     * Test int constructor with zero
     */

    @Test
    public final void testIntConstructorZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber q = this.constructorTest(0);
        NaturalNumber qExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /*
     * Test string constructor
     */

    @Test
    public final void testStringConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber q = this.constructorTest("1234");
        NaturalNumber qExpected = this.constructorRef("1234");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /*
     * Test string constructor with zero
     */

    @Test
    public final void testStringConstructorZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber q = this.constructorTest("0");
        NaturalNumber qExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /*
     * Test NN constructor
     */

    @Test
    public final void testNNConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber val = new NaturalNumber1L(1234);

        NaturalNumber q = this.constructorTest(val);
        NaturalNumber qExpected = this.constructorRef("1234");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /*
     * Test NN constructor with zero
     */

    @Test
    public final void testNNConstructorZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber val = new NaturalNumber1L(0);

        NaturalNumber q = this.constructorTest(val);
        NaturalNumber qExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    // TEST MULTIPLY BY 10

    /*
     * Test multiplyBy10 w/ int
     */

    @Test
    public final void testMultiplyBy10Int() {

        NaturalNumber n = this.constructorTest(123);
        NaturalNumber nExpected = this.constructorRef(1234);
        /*
         * Call method under test
         */
        n.multiplyBy10(4);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test multiplyBy10 w/ string
     */

    @Test
    public final void testMultiplyBy10String() {

        NaturalNumber n = this.constructorTest("123");
        NaturalNumber nExpected = this.constructorRef("1234");
        /*
         * Call method under test
         */
        n.multiplyBy10(4);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test multiplyBy10 w/ NN
     */

    @Test
    public final void testMultiplyBy10NN() {

        NaturalNumber val = new NaturalNumber1L(123);

        NaturalNumber n = this.constructorTest(val);
        NaturalNumber nExpected = this.constructorRef(1234);
        /*
         * Call method under test
         */
        n.multiplyBy10(4);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test divideBy10
     */

    @Test
    public final void testDivideBy10() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber q = this.constructorTest("1234");
        int dExpected = 4;
        NaturalNumber qExpected = this.constructorRef("123");
        /*
         * Call method under test
         */
        int d = q.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals(dExpected, d);
    }

    /*
     * Test divideBy10 with zero at the end
     */

    @Test
    public final void testDivideBy10Num2() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber q = this.constructorTest("1230");
        int dExpected = 0;
        NaturalNumber qExpected = this.constructorRef("123");
        /*
         * Call method under test
         */
        int d = q.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals(dExpected, d);
    }

    /*
     * Test is zero with zero
     */

    @Test
    public final void testIsZeroTrue() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber q = this.constructorTest("0");
        boolean dExpected = true;
        NaturalNumber qExpected = this.constructorRef("0");
        /*
         * Call method under test
         */
        boolean d = q.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals(dExpected, d);
    }

    /*
     * Test is zero with not zero
     */

    @Test
    public final void testIsZeroFalse() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber q = this.constructorTest("120");
        boolean dExpected = false;
        NaturalNumber qExpected = this.constructorRef("120");
        /*
         * Call method under test
         */
        boolean d = q.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals(dExpected, d);
    }

}
