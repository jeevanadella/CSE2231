import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 *
 * @author Jeevan Nadella
 *
 */

public final class hw36 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private hw36() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main1(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        while (!in.atEOS()) {
            String temp = in.nextLine();
            out.println();
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments: input-file-name output-file-name
     */
    public static void main2(String[] args) throws IOException {

        BufferedReader input = new BufferedReader(new FileReader(args[0]));
        PrintWriter output = new PrintWriter(
                new BufferedWriter(new FileWriter(args[1])));

        String s = input.readLine();

        while (s != null) {
            output.println(s);
            s = input.readLine();
        }

        /*
         * Close input and output streams
         */
        input.close();
        output.close();
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments: input-file-name output-file-name
     */
    public static void main3(String[] args) {

        BufferedReader input;
        PrintWriter output;

        try {
            input = new BufferedReader(new FileReader(args[0]));
            output = new PrintWriter(
                    new BufferedWriter(new FileWriter(args[1])));
        } catch (IOException e) {
            System.err.println("Error opening file");
            return;
        }

        try {
            String s = input.readLine();
            while (s != null) {
                output.println(s);
                s = input.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading from file or writing to file");
        }

        try {
            input.close();
            output.close();
        } catch (IOException e) {
            System.err.println("Error closing file");
        }
    }

}
