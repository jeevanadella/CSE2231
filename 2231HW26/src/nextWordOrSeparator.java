import components.queue.Queue1L;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class nextWordOrSeparator {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private nextWordOrSeparator() {
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code SEPARATORS}) or "separator string" (maximal length string of
     * characters in {@code SEPARATORS}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection entries(SEPARATORS) = {}
     * then
     *   entries(nextWordOrSeparator) intersection entries(SEPARATORS) = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection entries(SEPARATORS) /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of entries(SEPARATORS)  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of entries(SEPARATORS))
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position) {
        Set<Character> strSet = new Set1L<Character>();

        for (int i = 0; i < SEPARATORS.length(); i++) {
            char c = SEPARATORS.charAt(i);
            if (!strSet.contains(c)) {
                strSet.add(c);
            }
        }
        int endIndex = position;
        boolean ifSep = strSet.contains(text.charAt(position));
        while (endIndex < text.length()
                && strSet.contains(text.charAt(endIndex)) == ifSep) {
            endIndex++;
        }

        return text.substring(position, endIndex);
    }

    /**
     * Definition of whitespace separators.
     */
    private static final String SEPARATORS = " \t\n\r";

    /**
     * Tokenizes the entire input getting rid of all whitespace separators and
     * returning the non-separator tokens in a {@code Queue<String>}.
     *
     * @param in
     *            the input stream
     * @return the queue of tokens
     * @updates in.content
     * @requires in.is_open
     * @ensures <pre>
     * tokens =
     *   [the non-whitespace tokens in #in.content] * <END_OF_INPUT>  and
     * in.content = <>
     * </pre>
     */
    public static Queue<String> tokens(SimpleReader in) {
        Set<Character> strSet = new Set1L<Character>();
        for (int i = 0; i < SEPARATORS.length(); i++) {
            char c = SEPARATORS.charAt(i);
            if (!strSet.contains(c)) {
                strSet.add(c);
            }
        }
        Queue<String> queueOfTokens = new Queue1L<String>();
        while (!in.atEOS()) {
            int position = 0;
            String line = in.nextLine();
            while (position < line.length()) {
                String token = nextWordOrSeparator(line, position);
                if (!strSet.contains(line.charAt(position))) {
                    queueOfTokens.enqueue(token);
                }
                position += token.length();
            }
        }
        queueOfTokens.enqueue(END_OF_INPUT);

        // This line added just to make the program compilable.
        return queueOfTokens;
    }

    /**
     * Token to mark the end of the input. This token cannot come from the input
     * stream because it contains whitespace.
     */
    public static final String END_OF_INPUT = "### END OF INPUT ###";

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
