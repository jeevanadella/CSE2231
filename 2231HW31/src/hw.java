import java.util.Map;
import components.program.Program.Instruction;
import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.StatementKernel.Condition;

/**
 * Put a short phrase describing the program here.
 *
 * @author Jeevan Nadella
 *
 */
public final class hw {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private hw() {
    }

    /**
     * Converts {@code Condition} into corresponding conditional jump
     * instruction byte code.
     *
     * @param c
     *            the {@code Condition} to be converted
     * @return the conditional jump instruction byte code corresponding to
     *         {@code c}
     * @ensures <pre>
     * conditionalJump =
     *  [conditional jump instruction byte code corresponding to c]
     * </pre>
     */
    private static Instruction conditionalJump(Condition c) {...}

    /**
     * Generates the sequence of virtual machine instructions ("byte codes")
     * corresponding to {@code s} and appends it at the end of {@code cp}.
     *
     * @param s
     *            the {@code Statement} for which to generate code
     * @param context
     *            the {@code Context} in which to find user defined instructions
     * @param cp
     *            the {@code Sequence} containing the generated code
     * @updates cp
     * @ensures <pre>
     * if [all instructions called in s are either primitive or
     *     defined in context]  and
     *    [context does not include any calling cycles, i.e., recursion] then
     *  cp = #cp * [sequence of virtual machine "byte codes" corresponding to s]
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void generateCodeForStatement(Statement s,
            Map<String, Statement> context, Sequence<Integer> cp) {

        final int dummy = 0;

        switch (s.kind()) {
            case BLOCK: {

                Statement current = s.newInstance();

                for (int index = 0; index < s.lengthOfBlock(); index++) {
                    current = s.removeFromBlock(index);
                    generateCodeForStatement(current, context, cp);
                    s.addToBlock(index, current);
                }

                break;

            }
            case IF: {
                Statement b = s.newInstance();
                Condition c = s.disassembleIf(b);
                cp.add(cp.length(), conditionalJump(c).byteCode());
                int jump = cp.length();
                cp.add(cp.length(), dummy);
                generateCodeForStatement(b, context, cp);
                cp.replaceEntry(jump, cp.length());
                s.assembleIf(c, b);
                break;
            }
            case IF_ELSE: {

                Statement body1 = s.newInstance();
                Statement body2 = s.newInstance();

                Condition c = s.disassembleIfElse(body1, body2);
                cp.add(cp.length(), conditionalJump(c).byteCode());

                int condJump = cp.length();
                cp.add(cp.length(), dummy);
                generateCodeForStatement(body1, context, cp);
                cp.add(cp.length(), Instruction.valueOf("JUMP").byteCode());

                int jump = cp.length();
                cp.add(cp.length(), dummy);
                cp.replaceEntry(condJump, cp.length());
                generateCodeForStatement(body2, context, cp);

                cp.replaceEntry(jump, cp.length());
                s.assembleIfElse(c, body1, body2);

                break;

            }
            case WHILE: {

                Statement body = s.newInstance();
                Condition cond = s.disassembleWhile(body);

                int test = cp.length();
                cp.add(cp.length(), conditionalJump(cond).byteCode());
                int jump = cp.length();
                cp.add(cp.length(), dummy);
                generateCodeForStatement(body, context, cp);

                cp.add(cp.length(), Instruction.valueOf("JUMP").byteCode());
                cp.add(cp.length(), test);
                s.assembleWhile(cond, body);
                cp.replaceEntry(jump, cp.length());

                break;

            }
            case CALL: {

                String label = s.disassembleCall();

                if (context.hasKey(label)) {
                    generateCodeForStatement(context.value(label),
                            context.newInstance(), cp);
                } else {
                    label = label.toUpperCase();
                    cp.add(cp.length(), Instruction.valueOf(label).byteCode());
                    label = label.toLowerCase();
                }

                s.assembleCall(label);

                break;

            }
            default: {
                // nothing to do here: this will never happen...
                break;
            }
        }
    }

    /**
     * Generates and returns the sequence of virtual machine instructions ("byte
     * codes") corresponding to {@code this}.
     *
     * @return the compiled program
     * @ensures <pre>
     * if [all instructions called in this are either primitive or
     *     defined in this.context]  and
     *    [this does not include any calling cycles, i.e., recursion] then
     *  generatedCode =
     *   [the sequence of virtual machine "byte codes" corresponding to this]
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    public Sequence<Integer> generatedCode() {

        Sequence<Integer> p = new Sequence1L<Integer>();
        Map<String, Statement> q = this.newContext();

        q = this.replaceContext(q);
        Statement r = this.newBody();

        r = this.replaceBody(r);
        generateCodeForStatement(r, q, p);

        q = this.replaceContext(q);
        r = this.replaceBody(r);

        return p;
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
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
