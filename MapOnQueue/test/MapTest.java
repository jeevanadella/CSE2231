import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Jeevan Nadella
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

    /**
     * test constructor.
     */
    @Test
    public final void testConstructor() {

        Map<String, String> s = this.constructorTest();
        Map<String, String> sExpected = this.constructorRef();

        assertEquals(s, sExpected);

    }

    /**
     * test add.
     */
    @Test
    public final void testAdd() {

        Map<String, String> s = this.createFromArgsTest("red", "white", "blue",
                "purple");

        s.add("orange", "yellow");

        Map<String, String> sExpected = this.createFromArgsRef("red", "white",
                "blue", "purple", "orange", "yellow");

        assertEquals(sExpected, s);
    }

    /**
     * test remove.
     */
    @Test
    public final void testRemove() {

        Map<String, String> s = this.createFromArgsTest("red", "white", "blue",
                "purple");
        Map<String, String> sExpected = this.createFromArgsRef("blue",
                "purple");

        s.remove("red");
        assertTrue(!s.hasKey("red") && s.equals(sExpected));

    }

    /**
     * test removeAny.
     */
    @Test
    public final void testRemoveAny() {

        Map<String, String> s = this.createFromArgsTest("red", "white", "blue",
                "purple");

        int sizeBefore = s.size();

        s.removeAny();

        int sizeAfter = s.size();

        assertTrue(sizeAfter == sizeBefore - 1);
    }

    /**
     * test value.
     */
    @Test
    public final void testValue() {

        Map<String, String> s = this.createFromArgsTest("red", "white", "blue",
                "purple");
        String test = s.value("red");
        String test2 = s.value("blue");
        assertTrue(test.equals("white") && test2.equals("purple"));
    }

    /**
     *
     * test hasKey.
     */
    @Test
    public final void testHasKey() {
        Map<String, String> s = this.createFromArgsTest("red", "white", "blue",
                "purple");

        assertTrue(s.hasKey("red") && s.hasKey("blue") && !s.hasKey("white"));
    }

    /**
     * test size.
     */
    @Test
    public final void testSize() {
        Map<String, String> s = this.createFromArgsTest("red", "white", "blue",
                "purple", "orange", "yellow");

        int sizeTest = s.size();
        int sizeRef = 3;

        assertEquals(sizeTest, sizeRef);
    }

}