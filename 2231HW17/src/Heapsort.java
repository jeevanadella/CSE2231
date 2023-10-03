import components.binarytree.BinaryTree;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * hw17.
 *
 * @author Jeevan Nadella
 *
 */
public final class Heapsort {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Heapsort() {
    }

    /**
     * Checks if the given {@code BinaryTree<Integer>} satisfies the heap
     * ordering property according to the <= relation.
     *
     * @param t
     *            the binary tree
     * @return true if the given tree satisfies the heap ordering property;
     *         false otherwise
     * @ensures <pre>
     * satisfiesHeapOrdering = [t satisfies the heap ordering property]
     * </pre>
     */
    private static boolean satisfiesHeapOrdering(BinaryTree<Integer> t) {

        BinaryTree<Integer> left = t.newInstance();
        BinaryTree<Integer> right = t.newInstance();

        boolean result = true;

        if (t.size() != 0) {
            int root = t.disassemble(left, right);

            if ((root >= left.root()) && (root >= right.root())) {
                result = false;
            }

            result = (satisfiesHeapOrdering(left)
                    && satisfiesHeapOrdering(right));

            t.assemble(root, left, right);
        }

        return result;
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
