import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Jeevan Nadella, Adam Khalil
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // constructor test

    /**
     * test constructor.
     */
    @Test
    public final void testConstructor() {

        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.constructorRef();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    // add tests

    /**
     * Tests add when set is empty.
     */
    @Test
    public final void testAddToEmpty() {
        /*
         * Set up variables
         */
        Set<String> q = this.createFromArgsTest();
        Set<String> qExpected = this.createFromArgsRef("hi");
        /*
         * Call method under test
         */
        q.add("hi");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(q, qExpected);

    }

    /**
     * Tests add when set has one thing inside it already.
     */
    @Test
    public final void testAdd1() {
        /*
         * Set up variables
         */
        Set<String> q = this.createFromArgsTest("hi");
        Set<String> qExpected = this.createFromArgsRef("hi", "bye");
        /*
         * Call method under test
         */
        q.add("bye");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(q, qExpected);

    }

    /**
     * Tests add when set has 4 thing inside it already.
     */
    @Test
    public final void testAdd4() {
        /*
         * Set up variables
         */
        Set<String> q = this.createFromArgsTest("hi", "bye", "cry", "lie");
        Set<String> qExpected = this.createFromArgsRef("hi", "bye", "cry",
                "lie", "fly");
        /*
         * Call method under test
         */
        q.add("fly");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(q, qExpected);

    }

    /**
     * Tests add when set has 7 thing inside it already.
     */
    @Test
    public final void testAdd7() {
        /*
         * Set up variables
         */
        Set<String> q = this.createFromArgsTest("hi", "bye", "cry", "lie",
                "fly", "water", "ph");
        Set<String> qExpected = this.createFromArgsRef("hi", "bye", "cry",
                "lie", "fly", "water", "ph", "chem");
        /*
         * Call method under test
         */
        q.add("chem");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(q, qExpected);

    }

    // remove tests

    /**
     * test remove when size 1.
     */
    @Test
    public final void testRemove1() {

        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest("red");
        Set<String> ref = this.createFromArgsRef();

        /*
         * Call method under test
         */
        String removed = test.remove("red");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ref, test);
        assertEquals("red", removed);
    }

    /**
     * test remove when size 2.
     */
    @Test
    public final void testRemove2() {

        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest("red", "a");
        Set<String> ref = this.createFromArgsRef("red");

        /*
         * Call method under test
         */
        String removed = test.remove("a");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ref, test);
        assertEquals("a", removed);
    }

    /**
     * test remove when size 5.
     */
    @Test
    public final void testRemove5() {

        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest("red", "purple", "123",
                "green", "yellow");
        Set<String> ref = this.createFromArgsRef("red", "purple", "green",
                "yellow");

        /*
         * Call method under test
         */
        String removed = test.remove("123");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ref, test);
        assertEquals("123", removed);
    }

    /**
     * test remove when size 7.
     */
    @Test
    public final void testRemove7() {

        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest("red", "purple", "a", "123",
                "green", "yellow", "1a2b");
        Set<String> ref = this.createFromArgsRef("red", "purple", "a", "123",
                "green", "yellow");

        /*
         * Call method under test
         */
        String removed = test.remove("1a2b");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ref, test);
        assertEquals("1a2b", removed);
    }

    // removeAny tests
    /**
     * test removeAny when size 1.
     */
    @Test
    public final void testRemoveAny1() {

        /*
         * Set up variables
         */
        Set<String> a = this.createFromArgsTest("red");
        Set<String> aExpected = this.createFromArgsRef("red");
        boolean t = true;
        /*
         * Call method under test
         */
        String b = a.removeAny();
        /*
         * Assert variable values meet expectations
         */
        assertEquals(t, aExpected.contains(b));
        String bExpected = aExpected.remove(b);
        assertEquals(b, bExpected);
        assertEquals(aExpected, a);
    }

    /**
     * test removeAny when size 4.
     */
    @Test
    public final void testRemoveAny4() {

        /*
         * Set up variables
         */
        Set<String> a = this.createFromArgsTest("red", "hi", "high", "hello");
        Set<String> aExpected = this.createFromArgsRef("red", "hi", "high",
                "hello");
        boolean t = true;
        /*
         * Call method under test
         */
        String b = a.removeAny();
        /*
         * Assert variable values meet expectations
         */
        assertEquals(t, aExpected.contains(b));
        String bExpected = aExpected.remove(b);
        assertEquals(b, bExpected);
        assertEquals(aExpected, a);
    }

    /**
     * test removeAny when size 7.
     */
    @Test
    public final void testRemoveAny7() {

        /*
         * Set up variables
         */
        Set<String> a = this.createFromArgsTest("red", "hi", "high", "hello",
                "night", "knight", "immaculate");
        Set<String> aExpected = this.createFromArgsRef("red", "hi", "high",
                "hello", "night", "knight", "immaculate");
        boolean t = true;
        /*
         * Call method under test
         */
        String b = a.removeAny();
        /*
         * Assert variable values meet expectations
         */
        assertEquals(t, aExpected.contains(b));
        String bExpected = aExpected.remove(b);
        assertEquals(b, bExpected);
        assertEquals(aExpected, a);
    }

    // contains tests
    /**
     * test contains when size 0.
     */
    @Test
    public final void testContains0() {

        /*
         * Set up variables
         */
        Set<String> a = this.createFromArgsTest();

        /*
         * Call method under test
         */
        boolean b = a.contains("hi");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, b);
    }

    /**
     * test contains when size 1 and false.
     */
    @Test
    public final void testContains1False() {

        /*
         * Set up variables
         */
        Set<String> a = this.createFromArgsTest("blue");

        /*
         * Call method under test
         */
        boolean b = a.contains("hi");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, b);
    }

    /**
     * test contains when size 1 and true.
     */
    @Test
    public final void testContains1True() {

        /*
         * Set up variables
         */
        Set<String> a = this.createFromArgsTest("hi");

        /*
         * Call method under test
         */
        boolean b = a.contains("hi");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, b);
    }

    /**
     * test contains when size 7 and true.
     */
    @Test
    public final void testContains7True() {

        /*
         * Set up variables
         */
        Set<String> a = this.createFromArgsTest("red", "hi", "high", "hello",
                "night", "knight", "immaculate");

        /*
         * Call method under test
         */
        boolean b = a.contains("hi");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, b);
    }

    /**
     * test contains when size 7 and false.
     */
    @Test
    public final void testContains7False() {

        /*
         * Set up variables
         */
        Set<String> a = this.createFromArgsTest("red", "hi", "high", "hello",
                "night", "knight", "immaculate");

        /*
         * Call method under test
         */
        boolean b = a.contains("blue");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, b);
    }

    // size tests

    /**
     * test size when size 0.
     */
    @Test
    public final void testSize0() {

        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest();

        /*
         * Call method under test
         */
        int testSize = test.size();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(0, testSize);
    }

    /**
     * test size when size 1.
     */
    @Test
    public final void testSize1() {

        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest("red");

        /*
         * Call method under test
         */
        int testSize = test.size();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(1, testSize);
    }

    /**
     * test size when size 2.
     */
    @Test
    public final void testSize2() {

        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest("red", "a");

        /*
         * Call method under test
         */
        int testSize = test.size();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(2, testSize);
    }

    /**
     * test size when size 5.
     */
    @Test
    public final void testSize5() {

        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest("red", "purple", "123",
                "green", "yellow");

        /*
         * Call method under test
         */
        int testSize = test.size();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(5, testSize);
    }

    /**
     * test size when size 7.
     */
    @Test
    public final void testSize7() {

        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest("red", "purple", "a", "123",
                "green", "yellow", "1a2b");

        /*
         * Call method under test
         */
        int testSize = test.size();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(7, testSize);
    }

}
