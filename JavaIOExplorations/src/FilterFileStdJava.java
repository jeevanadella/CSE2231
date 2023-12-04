import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Program to copy a text file into another file.
 *
 * @author Jeevan Nadella, Adam Khalil
 *
 */
public final class FilterFileStdJava {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private FilterFileStdJava() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments: input-file-name output-file-name
     */
    public static void main(String[] args) {

        BufferedReader input;
        PrintWriter output;
        BufferedReader in;

        List<String> list = new ArrayList<>();

        try {
            input = new BufferedReader(new FileReader(args[0]));
            in = new BufferedReader(new FileReader(args[2]));
            output = new PrintWriter(
                    new BufferedWriter(new FileWriter(args[1])));
        } catch (IOException e) {
            System.err.println("Error opening file");
            return;
        }

        try {
            String line = in.readLine();
            while (line != null) {
                list.add(line);
                line = in.readLine();
            }

            String s = input.readLine();
            while (s != null) {
                for (String k : list) {
                    if (s.contains(k)) {
                        output.println(s);
                    }
                }
                s = input.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading from file or writing to file");
        }

        try {
            input.close();
            output.close();
            in.close();
        } catch (IOException e) {
            System.err.println("Error closing file");
        }

    }

}
