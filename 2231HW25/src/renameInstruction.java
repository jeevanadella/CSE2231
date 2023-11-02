import java.util.Map;

import components.program.Program;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class renameInstruction {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private renameInstruction() {
    }

    /**
     * Refactors the given {@code Statement} by renaming every occurrence of
     * instruction {@code oldName} to {@code newName}. Every other statement is
     * left unmodified.
     *
     * @param s
     *            the {@code Statement}
     * @param oldName
     *            the name of the instruction to be renamed
     * @param newName
     *            the new name of the renamed instruction
     * @updates s
     * @requires [newName is a valid IDENTIFIER]
     * @ensures <pre>
     * s = [#s refactored so that every occurrence of instruction oldName
     *   is replaced by newName]
     * </pre>
     */
    public static void renameInstruction(Statement s, String oldName,
            String newName) {
        switch (s.kind()) {
            case BLOCK: {
                int length = s.lengthOfBlock();
                for (int i = 0; i < length; i++) {
                    Statement subTree = s.removeFromBlock(i);
                    renameInstruction(subTree, oldName, newName);
                    s.addToBlock(i, subTree);
                }
                break;
            }
            case IF: {
                Statement subTree = s.newInstance();
                Condition ifCondition = s.disassembleIf(subTree);
                renameInstruction(subTree, oldName, newName);
                s.assembleIf(ifCondition, subTree);
            }
            case IF_ELSE: {

                Statement subTreeIf = s.newInstance();
                Statement subTreeElse = s.newInstance();
                Condition ifElseCondition = s.disassembleIfElse(subTreeIf,
                        subTreeElse);
                renameInstruction(subTreeIf, oldName, newName);
                renameInstruction(subTreeElse, oldName, newName);
                s.assembleIfElse(ifElseCondition, subTreeIf, subTreeElse);

            }
            case WHILE: {

                Statement subTree = s.newInstance();
                Condition whileCondition = s.disassembleWhile(subTree);
                renameInstruction(subTree, oldName, newName);
                s.assembleWhile(whileCondition, subTree);

            }
            case CALL: {
                String call = s.disassembleCall();
                if (call.equals(oldName)) {
                    s.assembleCall(newName);
                } else {
                    s.assembleCall(call);
                }
            }
            default:
                break;
        }
    }

    /**
     * Refactors the given {@code Program} by renaming instruction
     * {@code oldName}, and every call to it, to {@code newName}. Everything
     * else is left unmodified.
     *
     * @param p
     *            the {@code Program}
     * @param oldName
     *            the name of the instruction to be renamed
     * @param newName
     *            the new name of the renamed instruction
     * @updates p
     * @requires <pre>
     * oldName is in DOMAIN(p.context)  and
     * [newName is a valid IDENTIFIER]  and
     * newName is not in DOMAIN(p.context)
     * </pre>
     * @ensures <pre>
     * p = [#p refactored so that instruction oldName and every call
     *   to it are replaced by newName]
     * </pre>
     */
    public static void renameInstruction(Program p, String oldName,
            String newName) {
        Map<String, Statement> c = p.newContext();
        Map<String, Statement> ctxt = p.replaceContext(c);
        while (ctxt.size() > 0) {
            Map.Pair<String, Statement> instr = ctxt.removeAny();
            String key = instr.key();
            if (instr.key().equals(oldName)) {
                key = newName;
            }
            renameInstruction(instr.value(), oldName, newName);
            c.add(key, instr.value());
        }

        p.replaceContext(c);
        Statement b = p.newBody();
        Statement pBody = p.replaceBody(b);
        renameInstruction(pBody, oldName, newName);
        p.replaceBody(pBody);
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
