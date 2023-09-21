import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Jeevan Nadella
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
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

    /**
     * test constructor.
     */
    @Test
    public void testConstructor() {

        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.constructorRef();

        assertEquals(s, sExpected);
    }

    /**
     * test add.
     */
    @Test
    public final void testAdd() {

        Set<String> s = this.createFromArgsTest("hi", "hello");
        Set<String> sExpected = this.createFromArgsRef("greetings", "hi",
                "hello");

        s.add("greetings");

        assertEquals(s, sExpected);
    }

    /**
     * test remove.
     */
    @Test
    public final void testRemove() {

        Set<String> s = this.createFromArgsTest("greetings", "hi", "hello");
        Set<String> sExpected = this.createFromArgsRef("hi", "hello");

        s.remove("greetings");

        assertEquals(s, sExpected);
    }

    /**
     * test removeAny.
     */
    @Test
    public final void testRemoveAny() {

        Set<String> s = this.createFromArgsTest("greetings", "hi", "hello");
        Set<String> sExpected = this.createFromArgsRef("greetings", "hi",
                "hello");

        String entry = s.removeAny();

        assertTrue(
                sExpected.contains(entry) && s.size() == sExpected.size() - 1);

    }

    /**
     * test contains.
     */
    @Test
    public final void testContains() {

        Set<String> s = this.createFromArgsTest("greetings", "hi", "hello");
        Set<String> sExpected = this.createFromArgsRef("greetings", "hi",
                "hello");

        String entry = "hi";

        assertTrue(sExpected.contains(entry) && s.contains(entry));

    }

    /**
     * test size.
     */
    @Test
    public final void testSize() {

        Set<String> s = this.createFromArgsTest("green", "red", "blue");
        Set<String> sExpected = this.createFromArgsRef("green", "red", "blue");

        int size1 = s.size();
        int size2 = sExpected.size();

        assertTrue(size1 == size2);
    }

}