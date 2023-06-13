package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils;

import static android.text.TextUtils.isEmpty;

public class StringUtils {

    private static final int STRING_BUILDER_SIZE = 256;

    /**
     * A String for a space character.
     *
     * @since 3.2
     */
    public static final String SPACE = " ";

    /**
     * The empty String {@code ""}.
     *
     * @since 2.0
     */
    public static final String EMPTY = "";

    /**
     * A String for linefeed LF ("\n").
     *
     * @see <a href="http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6">JLF: Escape Sequences
     * for Character and String Literals</a>
     * @since 3.2
     */
    public static final String LF = "\n";

    /**
     * A String for carriage return CR ("\r").
     *
     * @see <a href="http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6">JLF: Escape Sequences
     * for Character and String Literals</a>
     * @since 3.2
     */
    public static final String CR = "\r";

    /**
     * Represents a failed index search.
     *
     * @since 2.1
     */
    public static final int INDEX_NOT_FOUND = -1;

    // Count matches
    //-----------------------------------------------------------------------

    /**
     * <p>Counts how many times the substring appears in the larger string.</p>
     *
     * <p>A {@code null} or empty ("") String input returns {@code 0}.</p>
     *
     * <pre>
     * StringUtils.countMatches(null, *)       = 0
     * StringUtils.countMatches("", *)         = 0
     * StringUtils.countMatches("abba", null)  = 0
     * StringUtils.countMatches("abba", "")    = 0
     * StringUtils.countMatches("abba", "a")   = 2
     * StringUtils.countMatches("abba", "ab")  = 1
     * StringUtils.countMatches("abba", "xxx") = 0
     * </pre>
     *
     * @param str the CharSequence to check, may be null
     * @param sub the substring to count, may be null
     * @return the number of occurrences, 0 if either CharSequence is {@code null}
     * @since 3.0 Changed signature from countMatches(String, String) to countMatches(CharSequence, CharSequence)
     */
    public static int countMatches(final CharSequence str, final CharSequence sub) {
        if (isEmpty(str) || isEmpty(sub)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = CharSequenceUtils.indexOf(str, sub, idx)) != INDEX_NOT_FOUND) {
            count++;
            idx += sub.length();
        }
        return count;
    }


}
