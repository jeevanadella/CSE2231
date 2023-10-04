import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.stack.Stack;

/**
 * JUnit test fixture for {@code Stack<String>}'s constructor and kernel
 * methods.
 *
 * @author Jeevan Nadella
 *
 */
public abstract class StackTest {

    /**
     * Invokes the appropriate {@code Stack} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new stack
     * @ensures constructorTest = <>
     */
    protected abstract Stack<String> constructorTest();

    /**
     * Invokes the appropriate {@code Stack} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new stack
     * @ensures constructorRef = <>
     */
    protected abstract Stack<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Stack<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsTest = [entries in args]
     */
    private Stack<String> createFromArgsTest(String... args) {
        Stack<String> stack = this.constructorTest();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    /**
     *
     * Creates and returns a {@code Stack<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsRef = [entries in args]
     */
    private Stack<String> createFromArgsRef(String... args) {
        Stack<String> stack = this.constructorRef();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    // constructor tests

    /**
     * test constructor.
     */
    @Test
    public final void testConstructor() {

        Stack<String> s = this.constructorTest();
        Stack<String> sExpected = this.constructorRef();

        assertEquals(sExpected, s);
    }

    // push tests

    /**
     * test push when empty.
     */
    @Test
    public final void testPush0() {

        Stack<String> s = this.createFromArgsTest();
        Stack<String> sExpected = this.createFromArgsRef("red");

        s.push("red");

        assertEquals(sExpected, s);
    }

    /**
     * test push when not empty.
     */
    @Test
    public final void testPush3() {

        Stack<String> s = this.createFromArgsTest("red", "green", "blue");
        Stack<String> sExpected = this.createFromArgsRef("purple", "red",
                "green", "blue");

        s.push("purple");

        assertEquals(sExpected, s);
    }

    // pop tests

    /**
     * test pop when size 1.
     */
    @Test
    public final void testPop1() {

        Stack<String> s = this.createFromArgsTest("red");
        Stack<String> sExpected = this.createFromArgsRef();

        String ans = s.pop();

        assertEquals(sExpected, s);
        assertEquals("red", ans);
    }

    /**
     * test pop when size 4.
     */
    @Test
    public final void testPop4() {

        Stack<String> s = this.createFromArgsTest("red", "green", "blue",
                "purple");
        Stack<String> sExpected = this.createFromArgsRef("green", "blue",
                "purple");

        String ans = s.pop();

        assertEquals(sExpected, s);
        assertEquals("red", ans);
    }

    // length tests

    /**
     * test length when empty.
     */
    @Test
    public final void testLength0() {
        Stack<String> s = this.createFromArgsTest();

        int l = s.length();

        assertEquals(0, l);
    }

    /**
     * test length when size 5.
     */
    @Test
    public final void testLength5() {
        Stack<String> s = this.createFromArgsTest("red", "green", "blue",
                "purple", "white");

        int l = s.length();

        assertEquals(5, l);
    }

}