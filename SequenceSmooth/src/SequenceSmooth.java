import components.sequence.Sequence;

/**
 * Implements method to smooth a {@code Sequence<Integer>}.
 *
 * @author Jeevan Nadella
 *
 */
public final class SequenceSmooth {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private SequenceSmooth() {
    }

//    /**
//     * Smooths a given {@code Sequence<Integer>} recursively.
//     *
//     * @param s1
//     *            the sequence to smooth
//     * @param s2
//     *            the resulting sequence
//     * @replaces s2
//     * @requires |s1| >= 1
//     * @ensures <pre>
//     * |s2| = |s1| - 1  and
//     *  for all i, j: integer, a, b: string of integer
//     *      where (s1 = a * <i> * <j> * b)
//     *    (there exists c, d: string of integer
//     *       (|c| = |a|  and
//     *        s2 = c * <(i+j)/2> * d))
//     * </pre>
//     */
//    public static void smooth(Sequence<Integer> s1, Sequence<Integer> s2) {
//        assert s1 != null : "Violation of: s1 is not null";
//        assert s2 != null : "Violation of: s2 is not null";
//        assert s1 != s2 : "Violation of: s1 is not s2";
//        assert s1.length() >= 1 : "Violation of: |s1| >= 1";
//
//        int first = 0, second = 0, aver = 0, newFirst = 0, newSecond = 0;
//        s2.clear();
//
//        if (s1.length() > 1) {
//            first = s1.remove(0);
//            second = s1.remove(0);
//
//            if ((first % 2 != 0) && (second % 2 != 0)) {
//                if ((first > 0) && (second > 0)) {
//                    aver += 1;
//                } else if ((first < 0) && (second < 0)) {
//                    aver -= 1;
//                }
//            }
//
//            if (((first % 2 == 0) && (second % 2 != 0))
//                    || ((first % 2 != 0) && (second % 2 == 0))) {
//                if (((first > 0) && (second < 0))
//                        || ((first < 0) && (second > 0))) {
//                    aver -= 1;
//                }
//            }
//
//            newFirst = first / 2;
//            newSecond = second / 2;
//            aver += newFirst + newSecond;
//
//            s1.add(0, second);
//            smooth(s1, s2);
//            s2.add(0, aver);
//            s1.add(0, first);
//        }
//    }

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
    public static void smooth(Sequence<Integer> s1, Sequence<Integer> s2) {
        assert s1 != null : "Violation of: s1 is not null";
        assert s2 != null : "Violation of: s2 is not null";
        assert s1 != s2 : "Violation of: s1 is not s2";
        assert s1.length() >= 1 : "Violation of: |s1| >= 1";

        int first = 0, second = 0, aver = 0, newFirst = 0, newSecond = 0;
        int firstSpot = 0, secondSpot = 1;
        s2.clear();

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
    }
}