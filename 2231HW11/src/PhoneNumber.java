/**
 * Returns the size of the given {@code BinaryTree<T>} recursively.
 *
 * @param <T>
 *            the type of the {@code BinaryTree} node labels
 * @param t
 *            the {@code BinaryTree} whose size to return
 * @return the size of the given {@code BinaryTree}
 * @ensures size = |t|
 */
public static <T> int sizeRecursive(BinaryTree<T> t) {
    BinaryTree<T> leftTree = t.newInstance();
    BinaryTree<T> rightTree = t.newInstance();
    int size = 0;

    if (t.height() != 0) {
        T root = t.disassemble(leftTree, rightTree);

        size = 1 + size(leftTree) + size(rightTree);

        t.assemble(root, leftTree, rightTree);
    }

    return size;
}

/**
 * Returns the size of the given {@code BinaryTree<T>} iteratively.
 *
 * @param <T>
 *            the type of the {@code BinaryTree} node labels
 * @param t
 *            the {@code BinaryTree} whose size to return
 * @return the size of the given {@code BinaryTree}
 * @ensures size = |t|
 */
public static <T> int sizeIterative(BinaryTree<T> t) {

    int size = 0;

    for (T x : t){
        size++;
    }

    return size;

}