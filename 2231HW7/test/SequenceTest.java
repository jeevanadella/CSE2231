import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.sequence.Sequence;

/**
 * JUnit test fixture for {@code Sequence<String>}'s constructor and kernel
 * methods.
 *
 * @author Jeevan Nadella
 *
 */
public abstract class SequenceTest {

    /**
     * Invokes the appropriate {@code Sequence} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new sequence
     * @ensures constructorTest = <>
     */
    protected abstract Sequence<String> constructorTest();

    /**
     * Invokes the appropriate {@code Sequence} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new sequence
     * @ensures constructorRef = <>
     */
    protected abstract Sequence<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsTest = [entries in args]
     */
    private Sequence<String> createFromArgsTest(String... args) {
        Sequence<String> sequence = this.constructorTest();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsRef = [entries in args]
     */
    private Sequence<String> createFromArgsRef(String... args) {
        Sequence<String> sequence = this.constructorRef();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    /**
     * test constructor.
     */
    @Test
    public void testConstructor() {

        Sequence<String> s = this.constructorTest();
        Sequence<String> sExpected = this.constructorRef();

        assertEquals(s, sExpected);
    }

    /**
     * test add.
     */
    @Test
    public void testAdd() {

        Sequence<String> s = this.createFromArgsTest("a", "b");
        Sequence<String> sExpected = this.createFromArgsRef("c", " a ", " b ");

        s.add(0, " c ");

        assertEquals(sExpected, s);
    }

    /**
     * test length empty.
     */
    @Test
    public final void testLengthEmpty() {

        Sequence<String> s = this.createFromArgsTest();
        Sequence<String> sExpected = this.createFromArgsRef();

        int i = s.length();

        assertEquals(sExpected, s);
        assertEquals(0, i);
    }

    /**
     * test length not empty.
     */
    @Test
    public final void testLengthNotEmpty() {

        Sequence<String> s = this.createFromArgsTest("hello");
        Sequence<String> sExpected = this.createFromArgsRef("hello");

        int i = s.length();

        assertEquals(sExpected, s);
        assertEquals(1, i);
    }

    /**
     * test for length more than one value.
     */
    @Test
    public final void testLengthNonEmptyMoreThanOne() {

        Sequence<String> s = this.createFromArgsTest("hello", "hi",
                "greetings");
        Sequence<String> sExpected = this.createFromArgsRef("hello", "hi",
                "greetings");

        int i = s.length();

        assertEquals(sExpected, s);
        assertEquals(3, i);
    }

    /**
     * test remove.
     *
     */
    @Test
    public final void testRemove() {

        Sequence<String> s = this.createFromArgsTest("hello", "hi",
                "greetings");
        Sequence<String> sExpected = this.createFromArgsRef("hi", "greetings");

        String digit = s.remove(0);

        assertEquals(sExpected, s);
        assertEquals(digit, "hello");
    }

}