import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Jeevan Nadella, Adam Khalil
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    // add tests
    /**
     * test add when size 0.
     */
    @Test
    public final void testAddEmpty() {
        SortingMachine<String> q = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> qExpected = this.createFromArgsRef(ORDER, true,
                "green");
        q.add("green");
        assertEquals(qExpected, q);
    }

    /**
     * test add when size 1.
     */
    @Test
    public final void testAdd1() {

        /*
         * Set up variables
         */
        SortingMachine<String> q = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> qExpected = this.createFromArgsRef(ORDER, true,
                "green", "blue");

        /*
         * Call method under test
         */
        q.add("blue");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(q, qExpected);
    }

    /**
     * test add when size 7.
     */
    @Test
    public final void testAdd7() {

        /*
         * Set up variables
         */
        SortingMachine<String> q = this.createFromArgsTest(ORDER, true, "red",
                "orange", "yellow", "green", "blue", "indigo", "violet");
        SortingMachine<String> qExpected = this.createFromArgsRef(ORDER, true,
                "red", "orange", "yellow", "green", "blue", "indigo", "violet",
                "grey");

        /*
         * Call method under test
         */
        q.add("grey");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(q, qExpected);
    }

    // changeToExtractionMode tests
    /**
     * test changeToExtractionMode when size 0.
     */
    @Test
    public final void testChangeToExtractionModeEmpty() {

        /*
         * Set up variables
         */
        SortingMachine<String> q = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> qExpected = this.createFromArgsTest(ORDER,
                false);

        /*
         * Call method under test
         */
        q.changeToExtractionMode();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(q, qExpected);
    }

    /*
     * test changeToExtractionMode when size 7.
     */
    @Test
    public final void testChangeToExtractionMode7() {

        /*
         * Set up variables
         */
        SortingMachine<String> q = this.createFromArgsTest(ORDER, true, "red",
                "orange", "yellow", "green", "blue", "indigo", "violet");
        SortingMachine<String> qExpected = this.createFromArgsTest(ORDER, false,
                "red", "orange", "yellow", "green", "blue", "indigo", "violet");

        /*
         * Call method under test
         */
        q.changeToExtractionMode();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(q, qExpected);
    }

    // removeFirst tests
    /**
     * test removeFirst when size 1.
     */
    @Test
    public final void testRemoveFirst1() {

        /*
         * Set up variables
         */
        SortingMachine<String> q = this.createFromArgsTest(ORDER, false, "red");
        SortingMachine<String> qExpected = this.createFromArgsRef(ORDER, false,
                "red");

        /*
         * Call method under test
         */
        q.removeFirst();
        qExpected.removeFirst();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /**
     * test removeFirst when size 2.
     */
    @Test
    public final void testRemoveFirst2() {

        /*
         * Set up variables
         */
        SortingMachine<String> q = this.createFromArgsTest(ORDER, false, "red",
                "orange");
        SortingMachine<String> qExpected = this.createFromArgsRef(ORDER, false,
                "red", "orange");

        /*
         * Call method under test
         */
        q.removeFirst();
        qExpected.removeFirst();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /**
     * test removeFirst when size 7.
     */
    @Test
    public final void testRemoveFirst7() {

        /*
         * Set up variables
         */
        SortingMachine<String> q = this.createFromArgsTest(ORDER, false, "red",
                "orange", "yellow", "green", "blue", "indigo", "violet");
        SortingMachine<String> qExpected = this.createFromArgsRef(ORDER, false,
                "red", "orange", "yellow", "green", "blue", "indigo", "violet");

        /*
         * Call method under test
         */
        q.removeFirst();
        qExpected.removeFirst();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    // isInInsertionMode tests
    /**
     * Test case for isInInsertionMode false.
     */
    @Test
    public final void testInsertModeF() {
        SortingMachine<String> q = this.createFromArgsTest(ORDER, false);

        assertEquals(q.isInInsertionMode(), false);
    }

    /**
     * Test case for isInInsertionMode true.
     */
    @Test
    public final void testInsertModeT() {
        SortingMachine<String> q = this.createFromArgsTest(ORDER, true);

        assertEquals(q.isInInsertionMode(), true);
    }

    /**
     * Test case for isInInsertionMode false with size 9.
     */
    @Test
    public final void testInsertModeF9() {
        SortingMachine<String> q = this.createFromArgsTest(ORDER, false, "1",
                "2", "3", "4", "5", "6", "7", "8", "9");

        assertEquals(q.isInInsertionMode(), false);
    }

    /**
     * Test case for isInInsertionMode true with size 9.
     */
    @Test
    public final void testInsertModeT9() {
        SortingMachine<String> q = this.createFromArgsTest(ORDER, true, "1",
                "2", "3", "4", "5", "6", "7", "8", "9");

        assertEquals(q.isInInsertionMode(), true);
    }

    // order tests
    /**
     * Test case for order method.
     */
    @Test
    public final void testOrder() {
        SortingMachine<String> q = this.createFromArgsTest(ORDER, true, "1",
                "2", "3", "4", "5", "6", "7", "8", "9");

        assertEquals(q.order(), ORDER);
    }

    // size tests

    /**
     * test size when size 2 true.
     */
    @Test
    public final void testSizeEmpty() {

        /*
         * Set up variables
         */
        SortingMachine<String> q = this.createFromArgsTest(ORDER, true);

        /*
         * Call method under test
         */
        int size = q.size();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(0, size);
    }

    /**
     * test size when size 2 true.
     */
    @Test
    public final void testSize2T() {

        /*
         * Set up variables
         */
        SortingMachine<String> q = this.createFromArgsTest(ORDER, true, "red",
                "orange");

        /*
         * Call method under test
         */
        int size = q.size();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(2, size);
    }

    /**
     * test size when size 2 false.
     */
    @Test
    public final void testSize2F() {

        /*
         * Set up variables
         */
        SortingMachine<String> q = this.createFromArgsTest(ORDER, false, "red",
                "orange");

        /*
         * Call method under test
         */
        int size = q.size();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(2, size);
    }

    /**
     * test size when size 7 true.
     */
    @Test
    public final void testSize7T() {

        /*
         * Set up variables
         */
        SortingMachine<String> q = this.createFromArgsTest(ORDER, false, "red",
                "orange", "yellow", "green", "blue", "indigo", "violet");

        /*
         * Call method under test
         */
        int size = q.size();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(7, size);
    }

    /**
     * test size when size 7 false.
     */
    @Test
    public final void testSize7F() {

        /*
         * Set up variables
         */
        SortingMachine<String> q = this.createFromArgsTest(ORDER, false, "red",
                "orange", "yellow", "green", "blue", "indigo", "violet");

        /*
         * Call method under test
         */
        int size = q.size();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(7, size);
    }

}
