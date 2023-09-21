import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * Implements method to smooth a {@code Sequence<Integer>}.
 *
 * @author Jeevan Nadella
 *
 */
public final class SequenceSmoothHW3 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private SequenceSmoothHW3() {
    }

    /**
     * Smooths a given {@code Sequence<Integer>} recursively.
     *
     * @param s1
     *            the sequence to smooth
     * @param s2
     *            the resulting sequence
     * @replaces s2
     * @requires |s1| >= 1
     * @ensures <pre>
     * |s2| = |s1| - 1  and
     *  for all i, j: integer, a, b: string of integer
     *      where (s1 = a * <i> * <j> * b)
     *    (there exists c, d: string of integer
     *       (|c| = |a|  and
     *        s2 = c * <(i+j)/2> * d))
     * </pre>
     */
    public static Sequence smoothRecursive(Sequence<Integer> s1) {
        assert s1 != null : "Violation of: s1 is not null";
        assert s1.length() >= 1 : "Violation of: |s1| >= 1";

        int first = 0, second = 0, aver = 0, newFirst = 0, newSecond = 0;
        Sequence<Integer> s2 = new Sequence1L<>();
        Sequence<Sequence> s3 = new Sequence1L<>();

        if (s1.length() > 1) {
            first = s1.remove(0);
            second = s1.remove(0);

            if ((first % 2 != 0) && (second % 2 != 0)) {
                if ((first > 0) && (second > 0)) {
                    aver += 1;
                } else if ((first < 0) && (second < 0)) {
                    aver -= 1;
                }
            }

            if (((first % 2 == 0) && (second % 2 != 0))
                    || ((first % 2 != 0) && (second % 2 == 0))) {
                if (((first > 0) && (second < 0))
                        || ((first < 0) && (second > 0))) {
                    aver -= 1;
                }
            }

            newFirst = first / 2;
            newSecond = second / 2;
            aver += newFirst + newSecond;

            s1.add(0, second);
            s3.add(0, smoothRecursive(s1));
            s2.add(0, aver);
            s1.add(0, first);
        }
        return s3;
    }

    /**
     * Smooths a given {@code Sequence<Integer>} iteratively.
     *
     * @param s1
     *            the sequence to smooth
     * @param s2
     *            the resulting sequence
     * @replaces s2
     * @requires |s1| >= 1
     * @ensures <pre>
     * |s2| = |s1| - 1  and
     *  for all i, j: integer, a, b: string of integer
     *      where (s1 = a * <i> * <j> * b)
     *    (there exists c, d: string of integer
     *       (|c| = |a|  and
     *        s2 = c * <(i+j)/2> * d))
     * </pre>
     */
    public static Sequence smoothIterative(Sequence<Integer> s1) {
        assert s1 != null : "Violation of: s1 is not null";
        assert s1.length() >= 1 : "Violation of: |s1| >= 1";

        int first = 0, second = 0, aver = 0, newFirst = 0, newSecond = 0;
        int firstSpot = 0, secondSpot = 1;
        Sequence<Integer> s2 = new Sequence1L<>();

        while (secondSpot < s1.length()) {
            first = s1.entry(firstSpot);
            second = s1.entry(secondSpot);
            aver = 0;

            if ((first % 2 != 0) && (second % 2 != 0)) {
                if ((first > 0) && (second > 0)) {
                    aver += 1;
                } else if ((first < 0) && (second < 0)) {
                    aver -= 1;
                }
            }

            if (((first % 2 == 0) && (second % 2 != 0))
                    || ((first % 2 != 0) && (second % 2 == 0))) {
                if (((first > 0) && (second < 0))
                        || ((first < 0) && (second > 0))) {
                    aver -= 1;
                }
            }

            newFirst = first / 2;
            newSecond = second / 2;
            aver += newFirst + newSecond;

            s2.add(0, aver);

            firstSpot += 1;
            secondSpot += 1;
        }
        s2.flip();
        return s2;
    }
}