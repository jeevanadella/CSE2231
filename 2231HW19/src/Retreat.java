import org.w3c.dom.Node;

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
public final class Retreat {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Retreat() {
    }

    /**
     * Retreats the position in {@code this} by one.
     *
     * @updates this
     * @requires this.left /= <>
     * @ensures <pre>
     * this.left * this.right = #this.left * #this.right  and
     * |this.left| = |#this.left| - 1
     * </pre>
     */
    public void retreat() {

        Node newLast = this.preFront;

        while (newLast.next != this.lastLeft) {
            newLast = newLast.next;
        }

        this.lastLeft = newLast;
        this.rightLength++;
        this.leftLength--;
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
