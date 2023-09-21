import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Jeevan Nadella, Adam Khalil
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // test constructor

    /*
     * Test constructor
     */
    @Test
    public final void testConstructor() {
        /*
         * Set up variables
         */
        Map<String, String> test = this.constructorTest();
        Map<String, String> expect = this.constructorRef();
        /*
         * Assert variable values meet expectations
         */
        assertEquals(expect, test);
    }

    // test add

    /*
     * test add on empty map
     */
    @Test
    public final void addEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> a = this.createFromArgsTest();
        Map<String, String> aExpected = this.createFromArgsRef("a", "b");
        /*
         * Call method under test
         */
        a.add("a", "b");
        /*
         * Assert variable values meet expectations
         */
        assertEquals(aExpected, a);
    }

    /*
     * test add on map length one
     */
    @Test
    public final void addOne() {
        /*
         * Set up variables
         */
        Map<String, String> a = this.createFromArgsTest("a", "b");
        Map<String, String> aExpected = this.createFromArgsRef("a", "b", "c",
                "d");
        /*
         * Call method under test
         */
        a.add("c", "d");
        /*
         * Assert variable values meet expectations
         */
        assertEquals(aExpected, a);
    }

    /*
     * test add on map length two
     */
    @Test
    public final void addThree() {
        /*
         * Set up variables
         */
        Map<String, String> a = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> aExpected = this.createFromArgsRef("a", "b", "c",
                "d", "e", "g");
        /*
         * Call method under test
         */
        a.add("e", "g");
        /*
         * Assert variable values meet expectations
         */
        assertEquals(aExpected, a);
    }

    /*
     * test add on map length four
     */
    @Test
    public final void addFive() {
        /*
         * Set up variables
         */
        Map<String, String> a = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f", "g", "h");
        Map<String, String> aExpected = this.createFromArgsRef("a", "b", "c",
                "d", "e", "f", "g", "h", "i", "j");
        /*
         * Call method under test
         */
        a.add("i", "j");
        /*
         * Assert variable values meet expectations
         */
        assertEquals(aExpected, a);
    }

    // test remove

    /*
     * test remove when one map
     */
    @Test
    public final void remove1() {
        /*
         * Set up variables
         */
        Map<String, String> test = this.createFromArgsTest("a", "b");
        Map<String, String> expect = this.createFromArgsRef();
        /*
         * Call method under test
         */
        test.remove("a");
        /*
         * Assert variable values meet expectations
         */
        assertEquals(expect, test);
    }

    /*
     * test remove when two maps
     */
    @Test
    public final void remove2() {
        /*
         * Set up variables
         */
        Map<String, String> test = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> expect = this.createFromArgsRef("a", "b");
        /*
         * Call method under test
         */
        test.remove("c");
        /*
         * Assert variable values meet expectations
         */
        assertEquals(expect, test);
    }

    /*
     * test remove when three maps
     */
    @Test
    public final void remove3() {
        /*
         * Set up variables
         */
        Map<String, String> test = this.createFromArgsTest("a", "b", "c", "d",
                "1", "2");
        Map<String, String> expect = this.createFromArgsRef("a", "b", "c", "d");
        /*
         * Call method under test
         */
        test.remove("1");
        /*
         * Assert variable values meet expectations
         */
        assertEquals(expect, test);
    }

    // test removeAny

    /*
     * test removeAny when one map
     */
    @Test
    public final void removeAny1() {
        /*
         * Set up variables
         */
        Map<String, String> a = this.createFromArgsTest("a", "b");
        Map<String, String> aExpected = this.createFromArgsRef("a", "b");
        boolean t = true;
        /*
         * Call method under test
         */
        Map.Pair<String, String> b = a.removeAny();
        /*
         * Assert variable values meet expectations
         */
        assertEquals(t, aExpected.hasKey(b.key()));
        Map.Pair<String, String> bExpected = aExpected.remove(b.key());
        assertEquals(b, bExpected);
        assertEquals(aExpected, a);
    }

    /*
     * test removeAny when two maps
     */
    @Test
    public final void removeAnyTwo() {
        /*
         * Set up variables
         */
        Map<String, String> a = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> aExpected = this.createFromArgsRef("a", "b", "c",
                "d");
        boolean t = true;
        /*
         * Call method under test
         */
        Map.Pair<String, String> b = a.removeAny();
        /*
         * Assert variable values meet expectations
         */
        assertEquals(t, aExpected.hasKey(b.key()));
        Map.Pair<String, String> bExpected = aExpected.remove(b.key());
        assertEquals(b, bExpected);
        assertEquals(aExpected, a);
    }

    /*
     * test removeAny when three maps
     */
    @Test
    public final void removeAnyThree() {
        /*
         * Set up variables
         */
        Map<String, String> a = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f");
        Map<String, String> aExpected = this.createFromArgsRef("a", "b", "c",
                "d", "e", "f");
        boolean t = true;
        /*
         * Call method under test
         */
        Map.Pair<String, String> b = a.removeAny();
        /*
         * Assert variable values meet expectations
         */
        assertEquals(t, aExpected.hasKey(b.key()));
        Map.Pair<String, String> bExpected = aExpected.remove(b.key());
        assertEquals(b, bExpected);
        assertEquals(aExpected, a);
    }

    /*
     * test removeAny when five maps
     */
    @Test
    public final void removeAnyFive() {
        /*
         * Set up variables
         */
        Map<String, String> a = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f", "g", "h", "i", "j");
        Map<String, String> aExpected = this.createFromArgsRef("a", "b", "c",
                "d", "e", "f", "g", "h", "i", "j");
        boolean t = true;
        /*
         * Call method under test
         */
        Map.Pair<String, String> b = a.removeAny();
        /*
         * Assert variable values meet expectations
         */
        assertEquals(t, aExpected.hasKey(b.key()));
        Map.Pair<String, String> bExpected = aExpected.remove(b.key());
        assertEquals(b, bExpected);
        assertEquals(aExpected, a);
    }

    // test value

    /*
     * test value when one map
     */
    @Test
    public final void value1() {
        /*
         * Set up variables
         */
        Map<String, String> test = this.createFromArgsTest("a", "b");
        Map<String, String> expect = this.createFromArgsRef("a", "b");
        /*
         * Call method under test
         */
        String value = test.value("a");
        /*
         * Assert variable values meet expectations
         */
        assertEquals(value, expect.value("a"));
    }

    /*
     * test value when two maps
     */
    @Test
    public final void value2() {
        /*
         * Set up variables
         */
        Map<String, String> test = this.createFromArgsTest("a", "b", "1", "2");
        Map<String, String> expect = this.createFromArgsRef("a", "b", "1", "2");
        /*
         * Call method under test
         */
        String value = test.value("1");
        /*
         * Assert variable values meet expectations
         */
        assertEquals(value, expect.value("1"));
    }

    /*
     * test value when three maps
     */
    @Test
    public final void value3() {
        /*
         * Set up variables
         */
        Map<String, String> test = this.createFromArgsTest("a", "b", "1", "2",
                "hi", "hello");
        Map<String, String> expect = this.createFromArgsRef("a", "b", "1", "2",
                "hi", "hello");
        /*
         * Call method under test
         */
        String value = test.value("hi");
        /*
         * Assert variable values meet expectations
         */
        assertEquals(value, expect.value("hi"));
    }

    // test hasKey

    /*
     * test hasKey when three maps not true
     */
    @Test
    public final void hasKeyNotTrue3() {
        /*
         * Set up variables
         */
        Map<String, String> a = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f");
        boolean t = false;
        /*
         * Call method under test
         */
        boolean tExpected = a.hasKey("z");
        /*
         * Assert variable values meet expectations
         */
        assertEquals(t, tExpected);
    }

    /*
     * test hasKey when three maps true
     */
    @Test
    public final void hasKeyTrue3() {
        /*
         * Set up variables
         */
        Map<String, String> a = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f");
        boolean t = true;
        /*
         * Call method under test
         */
        boolean tExpected = a.hasKey("c");
        /*
         * Assert variable values meet expectations
         */
        assertEquals(t, tExpected);
    }

    /*
     * test hasKey when 0 maps false
     */
    @Test
    public final void hasKeyFalse0() {
        /*
         * Set up variables
         */
        Map<String, String> a = this.createFromArgsTest();
        boolean t = false;
        /*
         * Call method under test
         */
        boolean tExpected = a.hasKey("c");
        /*
         * Assert variable values meet expectations
         */
        assertEquals(t, tExpected);
    }

    // test size

    /*
     * test size when no map
     */
    @Test
    public final void size0() {
        /*
         * Set up variables
         */
        Map<String, String> test = this.createFromArgsTest();
        /*
         * Assert variable values meet expectations
         */
        assertEquals(0, test.size());
    }

    /*
     * test size when one map
     */
    @Test
    public final void size1() {
        /*
         * Set up variables
         */
        Map<String, String> test = this.createFromArgsTest("a", "b");
        /*
         * Assert variable values meet expectations
         */
        assertEquals(1, test.size());
    }

    /*
     * test size when two maps
     */
    @Test
    public final void size2() {
        /*
         * Set up variables
         */
        Map<String, String> test = this.createFromArgsTest("a", "b", "1", "2");
        /*
         * Assert variable values meet expectations
         */
        assertEquals(2, test.size());
    }

    /*
     * test size when three maps
     */
    @Test
    public final void size3() {
        /*
         * Set up variables
         */
        Map<String, String> test = this.createFromArgsTest("a", "b", "1", "2",
                "hi", "hello");
        /*
         * Assert variable values meet expectations
         */
        assertEquals(3, test.size());
    }

}
