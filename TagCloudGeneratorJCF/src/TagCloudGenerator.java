import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Program that when given a text file counts the words and produces a html page
 * with a table of words and counts in alphabetical order.
 *
 * @author Jeevan Nadella, Adam Khalil
 *
 */
public final class TagCloudGenerator {

    /**
     * Returns a -,+ integer, or zero as the first arg that is less than,
     * greater, or equal to than the second.
     *
     */
    private static class IntegerSort
            implements Serializable, Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> firstInt,
                Map.Entry<String, Integer> secondInt) {

            int a = secondInt.getValue().compareTo(firstInt.getValue());
            if (a == 0) {
                int b = secondInt.getKey()
                        .compareToIgnoreCase(firstInt.getKey());
                a = b;
            }
            return a;
        }
    }

    /**
     * Returns a -,+ integer, or zero as the first arg that is less than,
     * greater, or equal to than the second.
     *
     */
    private static class StringSort
            implements Serializable, Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> a,
                Map.Entry<String, Integer> b) {
            /*
             * Compare string values of map entry.
             */
            int c = a.getKey().compareToIgnoreCase(b.getKey());
            if (c == 0) {
                int d = a.getValue().compareTo(b.getValue());
                c = d;
            }
            return c;
        }
    }

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudGenerator() {
    }

    /**
     * Reads the input file and goes through line by line putting each word into
     * a queue.
     *
     * @param inFile
     *            the text file from the user
     * @return queue featuring all the words.
     * @requires User's text file name is correct.
     * @ensures queue created will include all the words in the text file.
     */
    public static Queue<String> readFileIn(BufferedReader inFile) {

        Queue<String> wordQ = new LinkedList<String>();
        int endPosition = 0;

        //setting up an impossible position so that if nextWord does not find seperator
        //the whole string is a word.
        final int imp = -100;
        int position = 0;
        String word = "";
        //creating position int to keep track of current position in line
        position = 0;
        //as long as not at the end of the file will read in terms and definitions
        //read in a line from file
        try {
            String line = inFile.readLine();
            while (line != null) {
                //making all the words lowercase
                position = 0;
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

                    //placing the word into a queue if starting position is
                    //not a seperator

                    if (position == endPosition) {
                        wordQ.add(word);
                    }
                }
                line = inFile.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading from file" + e);
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
        Set<Character> separators = new HashSet<Character>();
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
        separators.add('[');
        separators.add(']');
        separators.add('"');
        separators.add('`');
        separators.add('*');
        separators.add('}');
        separators.add('{');
        separators.add('@');
        separators.add('_');

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
        int length = wordQ.size();

        //For all the words in the queue add to one version to the map and count.
        //Also remove extra copies of words in the queue.
        while (length > 0) {
            String word = wordQ.remove();
            length--;

            //if word is not already in the queue add it with a count of one
            //add word back into the queue
            if (!(countMap.containsKey(word))) {
                countMap.put(word, 1);
                wordQ.add(word);
            } else {
                //if word does exist add one to the count and do not add to queue
                int oldCount = countMap.get(word);
                int count = oldCount + 1;
                countMap.replace(word, oldCount, count);
            }
        }
    }

    /**
     * Sorts all words by greatest count and makes a list of the n # of words
     *
     * @param countMap
     *            map of string, int <word,count>.
     * @param n
     *            user inputed number
     * @return Sorted n number of words in greatest to least count
     * @requires countMap is not empty
     * @ensures countMap is not modified and a sorting machine with n number of
     *          words is in count order
     */
    public static List<Map.Entry<String, Integer>> sortMethodCount(
            Map<String, Integer> countMap, int n) {

        Comparator<Map.Entry<String, Integer>> count = new IntegerSort();
        List<Map.Entry<String, Integer>> cList = new ArrayList<Map.Entry<String, Integer>>();
        List<Map.Entry<String, Integer>> smallCList = new ArrayList<Map.Entry<String, Integer>>();
        //iterating over the map
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            cList.add(entry);
        }

        //sorting list by count
        cList.sort(count);

        int i = 0;
        //filling in the top n # of words
        while (cList.size() > 0 && i < n) {
            smallCList.add(cList.remove(0));
            i++;
        }

        return smallCList;
    }

    /**
     * Sorts all words sorts them in alphabetical order.
     *
     * @param smallCList
     *            list of entries string, integer.
     * @return Sorted n number of words in alphabetical order
     * @requires smallCList is not empty
     * @ensures countMap is not modified and a sorting machine with n number of
     *          words is in alphabetical order
     */
    public static List<Map.Entry<String, Integer>> sortMethodAlp(
            List<Map.Entry<String, Integer>> smallCList) {

        List<Map.Entry<String, Integer>> aList = new ArrayList<Map.Entry<String, Integer>>();
        Comparator<Map.Entry<String, Integer>> abcOrder = new StringSort();

        //placing all of small count list in aList
        aList.addAll(smallCList);
        //sorting new list by alphabetical order
        aList.sort(abcOrder);

        return aList;
    }

    /**
     * Calculates an slope to correctly map out the largest and smallest counts
     * to font values.
     *
     * @param max
     *            max count
     * @param min
     *            min count out of n counts
     * @return slope to map out function between counts and fonts
     * @ensures slope will correctly map out counts to fonts
     */
    public static double countSlope(int max, int min) {

        double x = max - min;
        if (x == 0) {
            x = 1;
            // all the counts were the same
        }

        final double z = 48 - 11;

        return z / x;
    }

    /**
     * Produces html file with tag cloud of size n in alphabetical order.
     *
     * @param aList
     *            list of n # of map.entries of word,count
     * @param outFile
     *            the output file
     * @param in
     *            name of the input file.
     * @param n
     *            user inputed number.
     * @param slope
     *            calculated slope to outline counts to font numbers
     * @requires outFile is a valid output file
     * @ensures Creates an html file with a tag cloud in alphabetical order.
     * @clears sortWord
     */
    public static void outprint(List<Map.Entry<String, Integer>> aList,
            PrintWriter outFile, String in, int n, double slope) {

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
        while (aList.size() > 0) {
            Map.Entry<String, Integer> k = aList.remove(0);
            final int el = 10;
            //adding smallest int wanted
            double fontDouble = k.getValue() * slope + el;
            int font = (int) fontDouble;
            outFile.println("<span style=" + '"' + "cursor:default" + '"'
                    + " class=" + '"' + 'f' + Integer.toString(font) + '"'
                    + " title=" + '"' + "count: " + k.getValue() + '"' + ">"
                    + k.getKey() + "</span>");
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
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));

        //asking user for input file
        System.out.print("Enter input file: ");
        BufferedReader inFile = null;
        String userInput = "";
        while (inFile == null) {
            try {
                userInput = in.readLine();
                inFile = new BufferedReader(new FileReader(userInput));
            } catch (IOException e) {
                System.err.println("Error reading from file:" + e);
                return;
            }
        }

        //asking user for a positive number
        System.out.println(
                "Please enter a positive integer for size of tag cloud");
        int userNum = 0;
        try {
            userNum = Integer.parseInt(in.readLine());
        } catch (NumberFormatException e) {
            System.err.println("Error. Number is wrong format.");
        } catch (IOException e) {
            System.err.println("Error reading input.");
        }
        if (userNum < 0) {
            userNum = 0;
            System.out.println("cant enter negative nummber");
        }

        String outputFile = "";
        System.out.print("Enter the wanted name for output file: ");
        PrintWriter outFile = null;
        while (outFile == null) {
            try {
                outputFile = in.readLine();
                outFile = new PrintWriter(
                        new BufferedWriter(new FileWriter(outputFile)));
            } catch (IOException e) {
                System.out.println("Invalid file due to " + e);
            }
        }

        //creating an map for words and counts
        Map<String, Integer> countMap = new HashMap<String, Integer>();

        //creating a queue for the words
        Queue<String> wordQ = new LinkedList<String>();

        //reading in the input file words into WordQ
        wordQ.addAll(readFileIn(inFile));

        wordCount(countMap, wordQ);

        //sorting word q in alphabetical order using sortingMachuine
        List<Map.Entry<String, Integer>> smallCList = sortMethodCount(countMap,
                userNum);

        int max = 0;
        int min = 0;
        if (smallCList.size() > 0) {
            min = smallCList.get(smallCList.size() - 1).getValue();
            max = smallCList.get(0).getValue();
        }

        double slope = countSlope(max, min);

        List<Map.Entry<String, Integer>> aList = sortMethodAlp(smallCList);

        outprint(aList, outFile, userInput, userNum, slope);

        try {
            in.close();
            inFile.close();
            outFile.close();
        } catch (IOException e) {
            System.err.println("Error closing file");
        }
    }
}
