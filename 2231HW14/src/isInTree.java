/**
 * Returns whether {@code x} is in {@code t}.
 *
 * @param <T>
 *            type of {@code BinaryTree} labels
 * @param t
 *            the {@code BinaryTree} to be searched
 * @param x
 *            the label to be searched for
 * @return true if t contains x, false otherwise
 * @requires IS_BST(t)
 * @ensures isInTree = (x is in labels(t))
 */
public static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t, T x) {

    BinaryTree<T> leftTree = t.newInstance();
    BinaryTree<T> rightTree = t.newInstance();

    boolean result = false;

    if (t.height() != 0) {
        T root = t.disassemble(leftTree, rightTree);
        int check = x.compareTo(root);
        if (check > 0) {
            isInTree(rightTree, x);
        } else if (check < 0) {
            isInTree(leftTree, x);
        } else {
            result = true;
        }
        t.assemble(root, leftTree, rightTree);

    }

    return result;
}