package cn.touki.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utilities for String.
 * 
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.4 $
 * @since 2.0
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {
    // Properties

    public static final int STR_PAD_LEFT = 0;

    public static final int STR_PAD_RIGHT = 1;

    private static final String EXPR_ASCII = "[\\p{ASCII}]+";

    private static final String EXPR_OTHER = "[^\\p{ASCII}]+";

    // Constructor

    // Methods

    /**
     * To judge whether a string is null or is blank.
     * <P>
     * If this string contains only blanks, this string would be considered to be blank.
     * 
     * @return <code>true</code> on null or blank
     */
    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }// end func

    /**
     * To check whether a string is completely quoted with a pair of braces ({}).
     * 
     * @param string the string to check
     * @return <code>true</code> on quoted and otherwise <code>false</code>
     * @since 1.0
     */
    public static boolean isQuotedWithBraces(String string) {
        if (string == null) {
            return false;
        }

        return string.startsWith("{") && string.endsWith("}");
    } // end func

    /**
     * Trim the braces at the start and end if the string is quoted with a pair of braces.
     * 
     * @param string the string to trim with
     * @return braces trimmed string
     * @since 1.0
     */
    public static String trimBraces(String string) {
        if (isQuotedWithBraces(string)) {
            return string.substring(1, string.length() - 1);
        }

        return string;
    } // end func

    /**
     * Transform {@code '<', '>'} into {@code '&lt;, &gt;'} in specified string.
     * 
     * @return transformed string.
     */
    public static String excapeHtmlSpecialChars(String htmlCode) {
        htmlCode = htmlCode.replaceAll("<", "&lt;");
        htmlCode = htmlCode.replaceAll(">", "&gt;");
        return htmlCode;
    }

    /**
     * Pad the string <code>original</code> to the length of <code>padLength</code> with the string
     * <code>padWith</code>.
     * <P>
     * The fourth parameter padMethod stands for which place will the padding take place. This should be one of
     * {@link #STR_PAD_LEFT} or {@link #STR_PAD_RIGHT}
     * 
     * @param original the original String to pad on
     * @param padWith the string to pad with
     * @param padLength the length of the string to be pad to
     * @param padMethod the place the pad will take place
     * @return the pad string
     * @since 1.0
     */
    public static String pad(String original, String padWith, int padLength, int padMethod) {
        // Prepare the padding string
        StringBuffer padding = new StringBuffer();
        for (int i = 0; i < padLength - original.length(); i++) {
            padding.append(padWith);
        }

        // pad the original string on method
        if (padMethod == STR_PAD_LEFT) {
            return padding.append(original).toString();
        } else {
            return original + padding.toString();
        }
    } // end func

    /**
     * Get the capitalized string of <code>original</code>.
     * 
     * @param original the string to capitalize.
     * @return capitalized string.
     */
    public static String capitalize(String original) {

        if (original == null || original.length() == 0 || original.startsWith(" ")) {
            return original;
        } else {
            return original.substring(0, 1).toUpperCase() + original.substring(1);
        }
    }

    /**
     * Implode a collection into a string with specified '<code>delim</code>'.
     * 
     * @param col the collection to implode
     * @param delim the delimeter to connect each element
     * @return imploded string.
     */
    public static String implode(Collection<?> col, String delim) {
        return implode(col, delim, false);
    }

    /**
     * Implode a collection into a string with specified '<code>delim</code>'.
     * 
     * @param col the collection to implode
     * @param delim the delimeter to connect each element
     * @param lastDelim whether append '<code>delim</code>'
     * @return imploded string.
     */
    public static String implode(Collection<?> col, String delim, boolean lastDelim) {
        Iterator<?> iter = col.iterator();
        StringBuffer result = new StringBuffer("");

        while (iter.hasNext()) {
            result.append(iter.next().toString()).append(delim);
        }

        String rslt = result.toString();
        if (rslt.length() > 0 && !lastDelim) {
            rslt = rslt.substring(0, rslt.length() - delim.length() - 1);
        }

        return rslt;
    }

    /**
     * 对url字符串中的非asscii字符进行url编码。
     * 
     * @param url
     * @param charset
     */
    public static String encodeUrl(String url, String charset) {
        StringBuffer sb = new StringBuffer();
        List<String> para = buildStringList(url, EXPR_OTHER);
        List<String> value = buildStringList(url, EXPR_ASCII);
        try {
            for (int i = 0; i < value.size(); i++) {
                value.set(i, URLEncoder.encode(value.get(i), charset));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < para.size(); i++) {
            sb.append(para.get(i));
            if (i < value.size()) {
                sb.append(value.get(i));
            }
        }
        return sb.toString();
    }

    /**
     * 根据正则表达式拆分字符串，返回拆分后的list集合.
     * 
     * @param url 字符串
     * @param expr 正则表达式
     * @return list 拆分后的结果
     */
    public static List<String> buildStringList(String url, String expr) {
        Pattern p = Pattern.compile(expr);
        String[] s = p.split(url);
        return toList(s);
    }

    /**
     * 将可能含有""字符串的字符串数组，转换为无""字符串的List集合.
     * 
     * @param s String[] which may have "" string.
     * @return lst which not have "" String.
     */
    public static List<String> toList(String[] s) {
        ArrayList<String> lst = new ArrayList<String>();
        for (String string : s) {
            if (isNotBlank(string)) {
                lst.add(string);
            }
        }
        return lst;
    }
    
    /**
     * Is all the String is ACSII.
     * @param str the string
     * @return true if all string is ASCII; else false.
     */
    public static boolean isAscii(String str){
        
        return str.matches(EXPR_ASCII);
    }

} // end class
