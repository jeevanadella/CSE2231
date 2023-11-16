import components.map.Map;
import components.map.Map.Pair;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Jeevan Nadella, Adam Khalil
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        //making an array of the primitive instructions
        String[] primInstr = { "turnleft", "turnright", "infect", "move",
                "skip" };

        String instruction = tokens.dequeue();
        //dequeuing what should be "INSTRUCTION"
        //testing to see if instruction = "INSTRUCTION"
        Reporter.assertElseFatalError(instruction.equals("INSTRUCTION"),
                "ERROR FOUND: Expected String INSTRUCTION but found "
                        + instruction);

        String instructionName = tokens.dequeue();
        //dequeuing the instruction name

        //using a for each loop to go through primitive calls
        for (String i : primInstr) {
            Reporter.assertElseFatalError(!i.equals(instructionName),
                    "ERROR FOUND: Instruction name can not be name of"
                            + "primitive instruction");
        }

        String is = tokens.dequeue();
        //dequeuing what should be "IS"
        //testing to see if is = "IS"
        Reporter.assertElseFatalError(is.equals("IS"),
                "ERROR FOUND: Expected word IS");

        body.parseBlock(tokens);

        String end = tokens.dequeue();
        //dequeuing what should be "END"
        //testing to see if end = "END"
        Reporter.assertElseFatalError(end.equals("END"),
                "ERROR FOUND: Expected word END");

        String lastInstructionName = tokens.dequeue();
        //testing to see if end instruction name is = to instructionName
        Reporter.assertElseFatalError(
                lastInstructionName.equals(instructionName),
                "ERROR FOUND: Instruction name at end does not match instruction name");

        return instructionName;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        String program = tokens.dequeue();
        //dequeuing what should be "PROGRAM"
        //testing if program = "PROGRAM"
        Reporter.assertElseFatalError(program.equals("PROGRAM"),
                "ERROR FOUND: Expected word PROGRAM");

        String programName = tokens.dequeue();
        this.setName(programName);

        String is = tokens.dequeue();
        //dequeuing what should be "IS"
        //testing to see if is = "IS"
        Reporter.assertElseFatalError(is.equals("IS"),
                "ERROR FOUND: Expected word is");

        Map<String, Statement> ctxt = this.newContext();

        while (tokens.front().equals("INSTRUCTION")) {
            Statement body = this.newBody();
            String instructionName = parseInstruction(tokens, body);
            //testing to see if instruction name is unique
            for (Pair<String, Statement> i : ctxt) {
                Reporter.assertElseFatalError(!i.key().equals(instructionName),
                        "ERROR FOUND: All instructions names be unique");
            }
            ctxt.add(instructionName, body);
        }
        this.swapContext(ctxt);

        String begin = tokens.dequeue();
        //dequeuing what should be "BEGIN"
        //testing to see if begin = "BEGIN"
        Reporter.assertElseFatalError(begin.equals("BEGIN"),
                "ERROR FOUND: Expected word BEGIN");

        Statement s = this.newBody();

        s.parseBlock(tokens);

        this.swapBody(s);

        String end = tokens.dequeue();
        //dequeuing what should be "END"
        //testing to see if end = "END"
        Reporter.assertElseFatalError(end.equals("END"),
                "ERROR FOUND: Expected word END");

        String lastProgramName = tokens.dequeue();
        //testing to see if program names match
        Reporter.assertElseFatalError(lastProgramName.equals(programName),
                "ERROR FOUND: Program name at begining and end do not match");

        //testing if end of program has been reached
        Reporter.assertElseFatalError(
                tokens.front().equals("### END OF INPUT ###"),
                "ERROR FOUND: Expected EOI");

    }

    /*
     * Main test method -------------------------------------------------------
     */

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
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
