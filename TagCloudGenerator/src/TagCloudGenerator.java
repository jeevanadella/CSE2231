import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

/**
 * Returns a -,+ integer, or zero as the first arg that is less than, greater,
 * or equal to than the second.
 *
 */
class IntegerLT implements Comparator<Pair<String, Integer>> {

    /**
     * @param o1
     *
     * @param o2
     *
     * @returns -,+, or zero based on the comparison
     * @ensures Integer with higher value is set first
     */
    @Override
    public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
        return o2.value().compareTo(o1.value());

    }

}

/**
 * Returns a -,+ string, or zero as the first arg that is less than, greater, or
 * equal to than the second.
 *
 */
class StringLT implements Comparator<Pair<String, Integer>> {
    /**
     * @param o1
     *
     * @param o2
     *
     * @requires String to be a single world
     *
     * @ensures String with high beginning letter is first
     */
    @Override
    public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
        return o1.key().compareTo(o2.key());
    }
}

/**
 * Program that when given a text file counts the words and produces a html page
 * with a table of words and counts in alphabetical order.
 *
 * @author Jeevan Nadella, Adam Khalil
 *
 */
public final class TagCloudGenerator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudGenerator() {
    }

    /**
     * Reads the input file and goes through line by line putting each word into
     * a queue.
     *
     * @param in
     *            the text file from the user
     * @return queue featuring all the words.
     * @requires User's text file name is correct.
     * @ensures queue created will include all the words in the text file.
     */
    public static Queue<String> readFileIn(SimpleReader in) {

        Queue<String> wordQ = new Queue1L<>();
        int endPosition = 0;

        //setting up an impossible position so that if nextWord does not find seperator
        //the whole string is a word.
        final int imp = -100;
        int position = 0;
        String word = "";
        //as long as not at the end of the file will read in terms and definitions
        while (!in.atEOS()) {
            //creating position int to keep track of current position in line
            position = 0;
            //read in a line from file
            String line = in.nextLine();
            //making all the words lowercase
            line = line.toLowerCase();

            //while not at the end of the line place words in one at a time
            //checking for separators
            while ((position < line.length()) && (position != imp)) {

                //searching for separators and finding endPosition of the word
                endPosition = nextWord(line, position);

                //if no position is found whole string from position on is a word
                if (endPosition == imp) {
                    word = line.substring(position);
                    position = endPosition;

                    //case in which starting position is a separator
                } else if (endPosition == position) {
                    word = line.substring(position, endPosition + 1);
                    position = endPosition + 1;

                    //else cut off the rest of string from ending position
                } else {
                    word = line.substring(position, endPosition);
                    position = endPosition;
                }

                //placing the word into a queue if starting position is not a seperator

                if (position == endPosition) {
                    wordQ.enqueue(word);
                }
            }
        }
        return wordQ;
    }

    /**
     * Given a string it looks for separator and returns ending position of the
     * word. If no separator is found it will return an impossible string
     * position.
     *
     * @param text
     *            String that will be examined
     * @param position
     *            Position the program will start from
     * @return ending position of the word or impossible position in case of no
     *         separator.
     * @requires position is not greater than the length of text. Text is not
     *           empty.
     */
    public static int nextWord(String text, int position) {
        assert text != null : "Violation` of: text is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        //creating separators set  \t\n\r,-.!?[]';:/()
        Set<Character> separators = new Set1L<>();
        separators.add(' ');
        separators.add('(');
        separators.add(')');
        separators.add('.');
        separators.add(':');
        separators.add(';');
        separators.add('\'');
        separators.add('/');
        separators.add('?');
        separators.add('!');
        separators.add('-');
        separators.add(',');
        separators.add('\r');
        separators.add('\n');
        separators.add('\t');

        //setting the end position to an impossible number so that there
        //could never be a separator at the initial end position
        final int imp = -100;
        int endPosition = imp;

        // for the text length look for the first separator
        for (int n = position; n < text.length(); n++) {
            if (separators.contains(text.charAt(n)) && endPosition == imp) {
                //if separator is found break out of the loop
                endPosition = n;
            }
        }

        return endPosition;
    }

    /**
     * Places words from queue into a Map and counts each word and places the
     * counts into the map as well. If more then one copy of a word appear in
     * the it will remove it so wordQ only has one copy of each word.
     *
     * @param countMap
     *            map of strings <term,definition>.
     * @param wordQ
     *            a queue containing all the terms.
     * @requires Both countMap and wordQ are not empty.
     * @ensures Both countMap and wordQ will be updated
     * @updates countMap
     * @Updates wordQ
     */
    public static void wordCount(Map<String, Integer> countMap,
            Queue<String> wordQ) {

        //creating an int for the length of queue.
        int length = wordQ.length();

        //For all the words in the queue add to one version to the map and count.
        //Also remove extra copies of words in the queue.
        while (length > 0) {
            String word = wordQ.dequeue();
            length--;

            //if word is not already in the queue add it with a count of one
            //add word back into the queue
            if (!(countMap.hasKey(word))) {
                countMap.add(word, 1);
                wordQ.enqueue(word);
            } else {
                //if word does exist add one to the count and do not add to queue
                int count = countMap.value(word) + 1;
                countMap.replaceValue(word, count);
            }
        }
    }

    /**
     * Sorts all words by greatest count then takes the highest n number counts
     * and sorts them in alphabetical order.
     *
     * @param countMap
     *            map of string, int <word,count>.
     * @param n
     *            user inputed number
     * @return Sorted n number of words in alphabetical order
     * @requires countMap is not empty
     * @ensures countMap is not modified and a sorting machine with n number of
     *          words is in alphabetical order
     */
    public static SortingMachine<Pair<String, Integer>> sortMethod(
            Map<String, Integer> countMap, int n) {

        Comparator<Map.Pair<String, Integer>> ci = new IntegerLT();
        SortingMachine<Map.Pair<String, Integer>> sortCount = new SortingMachine1L<Map.Pair<String, Integer>>(
                ci);
        //creating a sorting machine comparing the count values
        for (Pair<String, Integer> x : countMap) {
            sortCount.add(x);
        }

        sortCount.changeToExtractionMode();

        //sortingMachine for the words
        Comparator<Map.Pair<String, Integer>> cs = new StringLT();
        SortingMachine<Map.Pair<String, Integer>> sortWord = new SortingMachine1L<Map.Pair<String, Integer>>(
                cs);

        int size = sortCount.size();
        for (int i = 0; i < size && i < n; i++) {
            Map.Pair<String, Integer> k = sortCount.removeFirst();
            sortWord.add(k);
        }

        return sortWord;
    }

    /**
     * Calculates an slope to correctly map out the largest and smallest counts
     * to font values.
     *
     * @param sortWord
     *            sortingMachine of n number of words in alphabetical order
     * @param q
     *            an empty q
     * @return slope to map out function between counts and fonts
     * @ensures slope will correctly map out counts to fonts and q will have all
     *          words and counts in alphabetical order
     * @clears sortWord
     */
    public static int countSlope(SortingMachine<Pair<String, Integer>> sortWord,
            Queue<Pair<String, Integer>> q) {
        sortWord.changeToExtractionMode();
        while (sortWord.size() > 0) {
            q.enqueue(sortWord.removeFirst());
        }

        //sorting back by count
        Comparator<Map.Pair<String, Integer>> ci = new IntegerLT();
        SortingMachine<Map.Pair<String, Integer>> sortCountSlope = new SortingMachine1L<Map.Pair<String, Integer>>(
                ci);
        for (int i = q.length(); i > 0; i--) {
            Map.Pair<String, Integer> temp = q.dequeue();
            sortCountSlope.add(temp);
            q.enqueue(temp);
        }
        sortCountSlope.changeToExtractionMode();
        int y = sortCountSlope.removeFirst().value();
        while (sortCountSlope.size() > 1) {
            sortCountSlope.removeFirst();
        }

        int x = y - sortCountSlope.removeFirst().value();
        if (x == 0) {
            x = 1;
            // all the counts were the same
        }

        final int z = 48 - 11;

        return z / x;
    }

    /**
     * Produces html file with tag cloud of size n in alphabetical order.
     *
     * @param sortWord
     *            sortingMachine of n number of words in alphabetical order
     * @param outFile
     *            the output file
     * @param in
     *            name of the input file.
     * @param n
     *            user inputed number.
     * @requires outFile is a valid output file
     * @ensures Creates an html file with a tag cloud in alphabetical order.
     * @clears sortWord
     */
    public static void outprint(SortingMachine<Pair<String, Integer>> sortWord,
            SimpleWriter outFile, String in, int n) {

        Queue<Pair<String, Integer>> q = new Queue1L<>();
        int slope = countSlope(sortWord, q);

        outFile.println("<html>");
        outFile.println("<head>");
        outFile.println("<title>Top " + n + " words in " + in + "</title>");
        outFile.println(
                "<link href=\"http://web.cse.ohio-state.edu/software/2231/web-"
                        + "sw2/assignments/projects/tag-cloud-generator/data/tag"
                        + "cloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        outFile.println("<link href=" + '"' + "doc/tagcloud.css" + '"' + " rel="
                + '"' + "stylesheet" + '"' + " type =" + '"' + "text/css" + '"'
                + ">");
        outFile.println("</head>");
        outFile.println("<body>");
        outFile.println("<h2>Top " + n + " words in " + in + "</h2>");
        outFile.println("<hr>");
        outFile.println("<div class=\"cdiv\">");
        outFile.println("<p class =" + '"' + "cbox" + '"' + ">");
        //for all the words in the queue add to table
        while (q.length() > 0) {
            Map.Pair<String, Integer> k = q.dequeue();
            int font = k.value() * slope;
            outFile.println("<span style=" + '"' + "cursor:default" + '"'
                    + " class=" + '"' + 'f' + Integer.toString(font) + '"'
                    + " title=" + '"' + "count: " + k.value() + '"' + ">"
                    + k.key() + "</span>");
        }
        outFile.println("</p>");
        outFile.println("</div>");
        outFile.println("</body>");
        outFile.println("</html>");
        outFile.close();
    }

    /**
     * Given a user entered file and positive number, output file will be
     * created that is tag cloud that is the size of the positive number given
     * of the words from the user given file.
     *
     * @requires The file names input are correct
     *
     * @param args
     *            Command-line arguements: not used
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //asking user for input file
        out.print("Enter input file: ");
        String userInput = in.nextLine();
        //making sure user inputs an txt file
        final int three = 3;
        String textTest = userInput.substring(userInput.length() - three);
        while (!textTest.equals("txt")) {
            out.println("Please use a text file");
            userInput = in.nextLine();
        }
        SimpleReader inFile = new SimpleReader1L(userInput);

        //asking user for a positive number
        out.println("Please enter a positive integer for size of tag cloud");
        int userNum = in.nextInteger();
        while (userNum < 0) {
            out.print("Please enter a positive integer");
            userNum = in.nextInteger();
        }

        //asking user for output folder
        out.print("Enter wanted name of the output file: ");
        String userInputTwo = in.nextLine();
        //Making sure user inputs an html file
        final int four = 4;
        String htmlTest = userInputTwo.substring(userInputTwo.length() - four);
        while (!htmlTest.equals("html")) {
            out.println("Please use a html file");
            userInput = in.nextLine();
        }
        SimpleWriter outFile = new SimpleWriter1L(userInputTwo);

        //creating an map for words and counts
        Map<String, Integer> countMap = new Map1L<>();

        //creating a queue for the words
        Queue<String> wordQ = new Queue1L<>();

        //reading in the input file words into WordQ
        wordQ.append(readFileIn(inFile));

        wordCount(countMap, wordQ);

        //sorting word q in alphabetical order using sortingMachuine
        SortingMachine<Pair<String, Integer>> sortWord = sortMethod(countMap,
                userNum);

        outprint(sortWord, outFile, userInput, userNum);

        in.close();
        inFile.close();
        out.close();
    }
}
