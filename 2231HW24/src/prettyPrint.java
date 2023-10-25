import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;

/**
 * Put a short phrase describing the program here.
 *
 * @author Jeevan Nadella
 *
 */
public final class prettyPrint {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private prettyPrint() {
    }

    /**
     * Pretty prints {@code this} to the given stream {@code out} {@code offset}
     * spaces from the left margin using
     * {@link components.program.Program#INDENT_SIZE Program.INDENT_SIZE} spaces
     * for each indentation level.
     *
     * @param out
     *            the output stream
     * @param offset
     *            the number of spaces to be placed before every nonempty line
     *            of output; nonempty lines of output that are indented further
     *            will, of course, continue with even more spaces
     * @updates out.content
     * @requires out.is_open and 0 <= offset
     * @ensures <pre>
     * out.content =
     *   #out.content * [this pretty printed offset spaces from the left margin
     *                   using Program.INDENT_SIZE spaces for indentation]
     * </pre>
     */
    public void prettyPrint(SimpleWriter out, int offset) {
        switch (this.kind()) {
            case BLOCK: {

                int length = this.lengthOfBlock();

                for (int i = 0; i < length; i++) {
                    Statement subTree = this.removeFromBlock(i);
                    subTree.prettyPrint(out, offset);
                    this.addToBlock(i, subTree);
                }

                break;
            }
            case IF: {

                Statement subTree = this.newInstance();
                Condition ifCondition = this.disassembleIf(subTree);

                printSpaces(out, offset);

                out.println("IF " + toStringCondition(ifCondition));
                subTree.prettyPrint(out, offset + indent);

                for (int i = 0; i < offset; i++) {
                    out.print(" ");
                }

                out.println("END IF");
                this.assembleIf(ifCondition, subTree);

                break;
            }
            case IF_ELSE: {

                Statement subTreeIf = this.newInstance();
                Statement subTreeElse = this.newInstance();

                Condition ifElseCondition = this.disassembleIfElse(subTreeIf,
                        subTreeElse);

                printSpaces(out, offset);
                out.println(
                        "IF " + toStringCondition(ifElseCondition) + " THEN");
                subTreeIf.prettyPrint(out, offset + indent);

                printSpaces(out, offset);
                out.println("ELSE");
                subTreeElse.prettyPrint(out, offset + indent);

                printSpaces(out, offset);
                out.println("END IF");
                this.assembleIfElse(ifElseCondition, subTreeIf, subTreeElse);

                break;
            }
            case WHILE: {

                Statement subTree = this.newInstance();
                Condition whileCondition = this.disassembleWhile(subTree);

                printSpaces(out, offset);
                out.println(
                        "WHILE " + toStringCondition(whileCondition) + " DO");
                subTree.prettyPrint(out, offset + indent);

                printSpaces(out, offset);
                out.println("END WHILE");
                this.assembleWhile(whileCondition, subTree);

                break;
            }
            case CALL: {

                String call = this.disassembleCall();

                printSpaces(out, offset);
                out.println(call);
                this.assembleCall(call);

                break;
            }
            default: {

                // this will never happen...
                break;
            }
        }
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
