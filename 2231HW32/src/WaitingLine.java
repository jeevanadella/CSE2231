import components.standard.Standard;

/**
 * Put a short phrase describing the program here.
 *
 * @author Jeevan Nadella
 */

/**
 * First-in-first-out (FIFO) waiting line component with primary methods.
 *
 * @param <T>
 *            type of {@code WaitingLineKernel} entries
 * @mathmodel type WaitingLineKernel is modeled by string of T
 * @initially <pre>
 * ():
 *  ensures
 *   this = <>
 * </pre>
 * @iterator ~this.seen * ~this.unseen = this
 */
public interface WaitingLineKernel<T>
        extends Standard<WaitingLine<T>>, Iterable<T> {

    /**
     * Adds {@code x} to the end of {@code this} if {@code this} does not
     * contain {@code x}.
     *
     * @param x
     *            the entry to be added
     * @aliases reference {@code x}
     * @updates {@code this}
     * @requires <pre>
     * {@code this does not contain x}
     * @ensures
     * {@code this = #this * <x>}
     * </pre>
     */
    void enqueue(T x);

    /**
     * Removes {@code customer} from the front of {@code this}.
     *
     * @return the entry removed
     * @updates {@code this}
     * @requires <pre>
     * {@code this /= <>}
     * </pre>
     * @ensures <pre>
     * {@code #this = <removeFrontFromLine> * this}
     * </pre>
     */
    T dequeue();

    /**
     * Reports the front of {@code this}.
     *
     * @return the front entry of {@code this}
     * @aliases reference returned by {@code front}
     * @requires <pre>
     * {@code this /= <>}
     * </pre>
     * @ensures <pre>
     * {@code <front> is prefix of this}
     * </pre>
     */
    T front();

    /**
     * Reports length of {@code this}.
     *
     * @return the length of {@code this}
     * @ensures <pre>
     * {@code length = |this|}
     * </pre>
     */
    int length();

}

/**
 * {@code WaitingLineKernel} enhanced with secondary methods.
 *
 * @param <T>
 *            type of {@code WaitingLine} entries
 * @mathdefinitions <pre>
 * IS_TOTAL_PREORDER (
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y, z: T
 *   ((r(x, y) or r(y, x))  and
 *    (if (r(x, y) and r(y, z)) then r(x, z)))
 *
 * IS_SORTED (
 *   s: string of T,
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y: T where (<x, y> is substring of s) (r(x, y))
 * </pre>
 */
public interface WaitingLine<T> extends WaitingLineKernel<T> {

    /**
     * Find the position of the {@code entry} in {@code this}
     *
     * @param entry
     *            the entry being looked for
     * @return the position of the {@code entry} in {@code this}
     * @requires <pre>
     * {@code  this /= <>}
     * </pre>
     * @ensures <pre>
     * {@code position = position of customer in this}
     * </pre>
     */
    int position(T entry);

    /**
     * Reports the entry at index of {@code this}.
     *
     * @return the entry of {@code this} at given position
     * @aliases reference returned by {@code remove}
     * @updates this
     * @requires <pre>
     * {@code this /= <>}
     * </pre>
     * @ensures <pre>
     * {@code <remove> is entry of this at index given by position}
     * </pre>
     */
    T remove(T x);

    /**
     * Replaces the entry in {@code this} at position {@code pos} with {@code x}
     * , and returns the old entry.
     *
     * @param pos
     *            the position to replace
     * @param x
     *            the new entry at position {@code pos}
     * @return the old entry at position {@code pos}
     * @aliases reference {@code x}
     * @updates this
     * @clear x
     * @requires <pre>
     * {@code  this /= <>, 0 <= pos and pos < |this|}
     * </pre>
     * @ensures <pre>
     * {@code this = #this[0, pos) * <x> * #this[pos+1, |#this|) and
     * <replaceEntry> = #this[pos, pos+1)}
     * </pre>
     */

    T replace(int pos, T x);

}