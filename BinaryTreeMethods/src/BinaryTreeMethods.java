import components.binarytree.BinaryTree;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Utility class with implementation of {@code BinaryTree} static, generic
 * methods height and isInTree.
 *
 * @author Jeevan Nadella, Adam Khalil
 *
 */
public final class BinaryTreeMethods {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private BinaryTreeMethods() {
    }

    /**
     * Returns the height of the given {@code BinaryTree<T>}.
     *
     * @param <T>
     *            the type of the {@code BinaryTree} node labels
     * @param t
     *            the {@code BinaryTree} whose height to return
     * @return the height of the given {@code BinaryTree}
     * @ensures height = ht(t)
     */
    public static <T> int height(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";

        BinaryTree<T> leftTree = t.newInstance();
        BinaryTree<T> rightTree = t.newInstance();

        int height = 0;
        int heightR = 0;
        int heightL = 0;

        if (t.size() != 0) {
            T root = t.disassemble(leftTree, rightTree);
            heightR++;
            heightL++;
            heightR += height(leftTree);
            heightL += height(rightTree);
            if (heightR >= heightL) {
                height += heightR;
            } else {
                height += heightL;
            }
            t.assemble(root, leftTree, rightTree);
        }
        return height;
    }

    /**
     * Returns true if the given {@code T} is in the given {@code BinaryTree<T>}
     * or false otherwise.
     *
     * @param <T>
     *            the type of the {@code BinaryTree} node labels
     * @param t
     *            the {@code BinaryTree} to search
     * @param x
     *            the {@code T} to search for
     * @return true if the given {@code T} is in the given {@code BinaryTree},
     *         false otherwise
     * @ensures isInTree = [true if x is in t, false otherwise]
     */
    public static <T> boolean isInTree(BinaryTree<T> t, T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

//        // iterative
//        boolean isIn = false;
//        boolean found = false;
//
//        for (T i : t) {
//            found = i.equals(x);
//            if (found) {
//                isIn = true;
//            }
//        }
//
//        return isIn;

        // recursive
        BinaryTree<T> leftTree = t.newInstance();
        BinaryTree<T> rightTree = t.newInstance();

        boolean isIn = false;

        if (t.size() != 0) {
            T root = t.disassemble(leftTree, rightTree);
            isIn = root.equals(x) || isInTree(leftTree, x)
                    || isInTree(rightTree, x);

            t.assemble(root, leftTree, rightTree);
        }
        return isIn;
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

        out.print("Input a tree (or just press Enter to terminate): ");
        String str = in.nextLine();
        while (str.length() > 0) {
            BinaryTree<String> t = BinaryTreeUtility.treeFromString(str);
            out.println("Tree = " + BinaryTreeUtility.treeToString(t));
            out.println("Height = " + height(t));
            out.print("  Input a label to search "
                    + "(or just press Enter to input a new tree): ");
            String label = in.nextLine();
            while (label.length() > 0) {
                if (isInTree(t, label)) {
                    out.println("    \"" + label + "\" is in the tree");
                } else {
                    out.println("    \"" + label + "\" is not in the tree");
                }
                out.print("  Input a label to search "
                        + "(or just press Enter to input a new tree): ");
                label = in.nextLine();
            }
            out.println();
            out.print("Input a tree (or just press Enter to terminate): ");
            str = in.nextLine();
        }

        in.close();
        out.close();
    }

}
