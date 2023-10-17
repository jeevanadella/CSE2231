import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.tree.Tree;

/**
 * Put a short phrase describing the program here.
 *
 * @author Jeevan Nadella
 *
 */
public final class TreeAndRecursion {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TreeAndRecursion() {
    }

    /**
     * Returns the size of the given {@code Tree<T>}.
     *
     * @param <T>
     *            the type of the {@code Tree} node labels
     * @param t
     *            the {@code Tree} whose size to return
     * @return the size of the given {@code Tree}
     * @ensures size = |t|
     */
    public static <T> int size(Tree<T> t) {

        // recursive
//        Sequence<Tree<T>> subTree = t.newSequenceOfTree();
//
//        int size = 0;
//
//        if (t.height() != 0) {
//            T root = t.disassemble(subTree);
//            for (int i = 0; i < subTree.length(); i++) {
//                size = size + size(subTree.entry(i));
//            }
//            size++;
//            t.assemble(root, subTree);

        // recursive
//            int size = 0;
//            Iterator<T> iter = t.iterator();
//            while (iter.hasNext()) {
//                size++;
//            }
//
//            return size;
//
//        }
//
//        return size;
    }

    /**
     * Returns the height of the given {@code Tree<T>}.
     *
     * @param <T>
     *            the type of the {@code Tree} node labels
     * @param t
     *            the {@code Tree} whose height to return
     * @return the height of the given {@code Tree}
     * @ensures height = ht(t)
     */
    public static <T> int height(Tree<T> t) {

        Sequence<Tree<T>> children = new Sequence1L<Tree<T>>();

        int height = 0;
        int tempMaxHeight = 0;

        if (t.size() > 0) {

            T root = t.disassemble(children);

            for (Tree<T> x : children) {
                if (height(x) > tempMaxHeight) {
                    tempMaxHeight = height(x);
                }
            }
            height = 1 + tempMaxHeight;
            t.assemble(root, children);
        }

        return height;
    }

    /**
     * Returns the largest integer in the given {@code Tree<Integer>}.
     *
     * @param t
     *            the {@code Tree<Integer>} whose largest integer to return
     * @return the largest integer in the given {@code Tree<Integer>}
     * @requires |t| > 0
     * @ensures <pre>
     * max is in labels(t)  and
     * for all i: integer where (i is in labels(t)) (i <= max)
     * </pre>
     */
    public static int max(Tree<Integer> t) {

        int max = 0;

        Sequence<Tree<Integer>> children = new Sequence1L<Tree<Integer>>();

        if (t.height() != 0) {
            int root = t.disassemble(children);
            for (int i = 0; i < children.length(); i++) {
                max = max(children.entry(i));
            }
            if (root > max) {
                max = root;
            }
            t.assemble(root, children);
        }

        return max;
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
