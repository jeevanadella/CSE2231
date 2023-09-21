import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Counts all the words in a txt file and lists them in a table in a html file.
 *
 * @author Jeevan Nadella
 *
 */
public final class WordCounter {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private WordCounter() {
    }

    /**
     * @param inputFile
     *            input stream
     * @param separators
     *            a set containing all the separators
     * @return set with all the terms in input file
     * @ensures the set that is returned has all the words in the file.
     */
    private static Set<String> getWords(SimpleReader inputFile,
            Set<Character> separators) {

        // initialize set
        Set<String> words = new Set1L<>();

        // loop until file ends
        while (!(inputFile.atEOS())) {

            int pos = 0;

            // get line of text
            String line = inputFile.nextLine();

            // get length of line
            int lineLen = line.length();

            // loop through line until position is greater or equal to length
            while ((pos <= (lineLen - 1))) {

                // get the word or separator
                String word = nextWordOrSeparator(line, pos, separators);

                // check whether the word is separator or word and if word is in set
                if (!word.contains(" ") && !word.contains(",")
                        && !word.contains(".") && !word.contains("!")
                        && !word.contains("?") && !word.contains(";")
                        && !word.contains(":") && !word.contains("-")
                        && !word.contains("\t") && !words.contains(word)) {

                    // add word to set
                    words.add(word);
                }

                // increase position by word length
                pos = pos + word.length();
            }

        }

        // return set
        return words;
    }

    /**
     * @param wordCount
     *            a map with format [(word, 0)]
     * @param separators
     *            a set with all the separators
     * @param words
     *            set with all the words from text
     * @param inputFile
     *            input stream
     * @replaces mapAndCount replaces mapAndCount so it has format [(word,
     *           number of occurrences of word)]
     * @ensures makes sure that number of occurrences is correct and associated
     *          to correct word
     *
     */
    private static void updateMap(Map<String, Integer> wordCount,
            Set<Character> separators, Set<String> words,
            SimpleReader inputFile) {

        // loop through the text like getWords method

        while (!(inputFile.atEOS())) {

            int pos = 0;

            String line = inputFile.nextLine();

            int lineLen = line.length();

            while ((pos <= (lineLen - 1))) {

                String word = nextWordOrSeparator(line, pos, separators);

                // check if word is in words
                if (words.contains(word)) {

                    // increase its count if so
                    int count = wordCount.value(word);
                    count = count + 1;
                    wordCount.replaceValue(word, count);
                }

                pos = pos + word.length();
            }

        }

    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param pos
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     * text[position, position + |nextWordOrSeparator|) and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     * entries(nextWordOrSeparator) intersection separators = {} and
     * (position + |nextWordOrSeparator| = |text| or
    * entries(text[position, position + |nextWordOrSeparator| + 1))
     * intersection separators /= {})
     * else
     * entries(nextWordOrSeparator) is subset of separators and
     * (position + |nextWordOrSeparator| = |text| or
     * entries(text[position, position + |nextWordOrSeparator| + 1))
     * is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int pos,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= pos : "Violation of: 0 <= position";
        assert pos < text.length() : "Violation of: position < |text|";

        StringBuilder returned = new StringBuilder();

        if (separators.contains(text.charAt(pos))) {
            boolean check = true;
            for (int i = pos; i < text.length() && check; i++) {
                if (!(separators.contains(text.charAt(i)))) {
                    check = false;
                } else {
                    returned = returned.append(text.charAt(i));
                }
            }
        } else { //position in text doesn't have a separator
            boolean check = true;

            for (int i = pos; i < text.length() && check; i++) {
                if (separators.contains(text.charAt(i))) {
                    check = false;
                } else {
                    returned = returned.append(text.charAt(i));
                }
            }
        }

        return returned.toString();
    }

    /**
     * Output html file.
     *
     * @param outName
     *            name of the folder where the index.html file should be located
     *            after creation.
     * @param inName
     *            name of the input file
     * @param wordsQ
     *            queue of all terms in alphabetical order
     * @param wordCount
     *            map of terms associated to the number of occurrences of that
     *            word
     *
     * @requires |wordAndCount| /= 0 and |wordsQ| /= 0 and outputfolderName is a
     *           actual folder
     * @ensures html file created will be placed within indicated folder
     */
    private static void createIndex(String outName, String inName,
            Queue<String> wordsQ, Map<String, Integer> wordCount) {

        SimpleWriter site = new SimpleWriter1L(outName);

        // opening tags
        site.println("<html>");
        site.println("<head>");
        site.println("<title> Words Counted in " + inName + "</title>");
        site.println("</head>");
        site.println("<body>");
        site.println("<h2> Words Counted in " + inName + "</h2>");
        site.println("<hr>");
        site.println("<table border=\"1\">");
        site.println("<tbody>");
        site.println("<tr> <th> Words </th> <th> Counts </th> </tr>");

        // loop through queue and use it as a key in map to find count of word

        for (String word : wordsQ) {
            site.println("<tr>");
            site.println("<td>" + word + "</td>");
            int count = wordCount.value(word);
            site.println("<td>" + count + "</td>");
            site.println("</tr>");
        }
        site.println("</tbody>");
        site.println("</table>");
        site.println("</body>");

        site.println("</html>");

        // close output
        site.close();
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

        // ask user for input and output file names
        out.println("Name of the input file: ");
        String inName = in.nextLine();

        // create the SimpleReader for the file
        SimpleReader inFile = new SimpleReader1L(inName);

        SimpleReader inFile2 = new SimpleReader1L(inName);

        out.println("Name of the output file: ");
        String outName = in.nextLine();

        // create separator set
        Set<Character> separators = new Set1L<>();
        separators.add(' ');
        separators.add(',');
        separators.add('.');
        separators.add('?');
        separators.add('!');
        separators.add(';');
        separators.add(':');
        separators.add('-');
        separators.add('\t');

        // call method to create set with words in text
        Set<String> words = getWords(inFile, separators);

        // create map
        Map<String, Integer> wordCount = new Map1L<String, Integer>();

        // create queue so you can use comparator
        Queue<String> wordsQ = new Queue1L<>();

        //create temporary set for iterating over set using removeAny()
        Set<String> temp = new Set1L<String>();

        temp.transferFrom(words);

        //loop through temporary set and add to map and queue
        while (temp.size() > 0) {
            String x = temp.removeAny();
            wordsQ.enqueue(x);
            wordCount.add(x, 0);
            words.add(x);
        }

        // call method to update map
        updateMap(wordCount, separators, words, inFile2);

        // create output website
        createIndex(outName, inName, wordsQ, wordCount);

        // close inputs
        inFile.close();
        inFile2.close();

        /*
         * Close input and output streams
         */

        in.close();
        out.close();
    }

}
