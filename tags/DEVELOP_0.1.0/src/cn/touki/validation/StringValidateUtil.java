package cn.touki.validation;

import java.io.UnsupportedEncodingException;

/**
 * A utility box of string validation.
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.8 $
 * @since 2.0
 */
public class StringValidateUtil {

    //Properties
    public static final String REGEXP_QUALIFIED_NAME_ANSI = "^([[^\\x00-\\x9F][\\p{Alnum}][\\-_.]]{1,63})$";

    public static final String REGEXP_QUALIFIED_NAME_UTF8 = "^([[^\\x00-\\x7F][\\p{Alnum}][\\-_.]]{1,63})$";

    public static final String REGEXP_QUALIFIED_STR_UTF8 = "^([[^\\x00-\\x7F][\\p{Blank}][\\p{Alnum}][\\-_.]]{1,63})$";
    /**
     * Regular Expression for email: alpha, digits, -, _, ., form a style of <code>"username@domain.ext"</code>
     */
    private static String REGEXP_EMAIL =
            "^([[\\p{Alnum}][\\-_.]]{1,})\\@([[\\p{Alnum}][\\-.]]{1,})\\.([[\\p{Alnum}][\\-.]]{1,})$";

    /**
     * Regular Expression for phone number: digits, - forms a style of <code>"0xxxx-xxxxxxxx[-xx]"</code>
     */
    private static String REGEXP_PHONE =
            "^(0)([0-9]{2,4})\\-([0-9]{6,8})(\\-)?([0-9]{1,6})?$";

    /**
     * Regular Expression for international phone number: digits, -, + forms a style of
     * <code>"+xxxx-xxxx-xxxxxxxx[-xx]"</code>
     */
    private static String REGEXP_INTER_PHONE =
            "^(\\+)?([0-9]{1,4})\\-([0-9]{1,4})\\-([0-9]{6,8})(\\-)?([0-9]{1,6})?$";

    /**
     * Regular Expression for chinese mobile phone number: digits form a style of <code>"13xxxxxxxxx"</code>
     */
    private static String REGEXP_MOBILE = "^(13)([0-9]{9})$";

    /**
     * Regular Expression for a website url: all printable characters form a style of
     * <code>"http://domain.ext:00000/path?querystring"</code>
     */
    private static String REGEXP_WEBSITE =
            "^(http\\:\\/\\/)([[\\p{Alnum}][\\-\\.]]{1,})((\\:)([[\\p{Digit}]]{1,5}))?((\\/)([[\\p{Print}]]{0,}))?$";

    /**
     * Regular Expression for a icq/qq no.: 5-12 digits
     */
    private static String REGEXP_ICQ_QQ =
            "^([0-9]{5,12})$";

    /**
     * Regular Expression for a Birthday Date: "yyyy-mm-dd"
     */
    private static String REGEXP_BIRTHDAY =
            "^([0-9]{4})\\-([0-9]{2})\\-([0-9]{2})$";

    /**
     * Regular Expression for a Chinese ID Card old No: 15 digits
     */
    private static String REGEXP_IDCARD_CHN_OLD =
            "^([0-9]{15})$";

    /**
     * Regular Expression for a Chinese ID Card new No: 18 digits, the latest no can be 'X' or 'x'
     */
    private static String REGEXP_IDCARD_CHN_NEW =
            "^([0-9]{17})([0-9xX]{1})$";

    private static String REGEXP_IP_ADDRESS =
            "^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$";

    /**
     * Regular Expression for a Chinese Post Code: 6 digits;
     */
    private static String REGEXP_POSTCODE =
            "^([0-9]{6})$";

    /**
     * Regular Expression for a ework id prefix: 1 - 5 digits.
     */
    private static String REGEXP_EWORK_ID_PREFIX =
            "^([0-9]{1,5})$";

    private static final int MAX_IPV4_VALUE = 0xFF;
    
    /**
     * Regular Expression for a ework user amount of payment.
     */
    private static final String REGEXP_AMOUNT="^[1-9][0-9]*[\\.]?[0-9]*$";

    private static final String REGEXP_VERSION = "^([0-9]{1,})\\.([0-9]{2})\\.([0-9]{2})\\.([0-9]{2})$";

    private static final String REGEXP_DATE = "^([0-9]{4})\\-([0-9]{2})\\-([0-9]{2}) ([0-9]{2})\\:([0-9]{2})\\:([0-9]{2})$";

    //Constructor
    private StringValidateUtil() {
    }
    
    //Methods
    /**
     * To verify a string whether is an qualified name string in specified {@code charsetName}.
     * <p/>
     * A qualified name can include alphabets, digits, dashes(-), underscores(_), dots(.) and extended language
     * characters, which can be combined with byte 0xA0-0xFF for ANSI code, and 0x80-0xFD for UTF-8.
     * <p/>
     * eName, userId, groupId, vip name of eWork system are all instances of qualified name.
     *
     * @param str         the string to verify
     * @param charsetName the encoding charset of the string to verify
     * @return true or false
     */
    public static boolean isQualifiedName(String str, String charsetName) {
        if (charsetName.equals("UTF-8")) {
            return str.matches(REGEXP_QUALIFIED_NAME_UTF8);
        }
        else {
            return str.matches(REGEXP_QUALIFIED_NAME_ANSI);
        }
    }

    public static boolean isQualifiedString(String str) {
        return str.matches(REGEXP_QUALIFIED_STR_UTF8);
    }

    /**
     * To verify a string whether is an qualified name for ework server.
     *
     * A qualified ework server name allows only alphabet, digits, dashes(-), and underscores(_), with the initial
     * characater is '#'.
     *
     * @param str the string to verify
     * @return true or false
     */
    public static boolean isQualifiedEworkServerName(String str) {
        if (str.charAt(0) == '#') {
            return str.substring(1).matches(REGEXP_QUALIFIED_NAME_ANSI);
        }

        return false;
    }

    /**
     * To verify if a eworkId Prefix is valid.
     * <P/>
     * A valid eworkId prefix has 1 - 5 digits.
     *
     * @param idPrefix - the idPrefix to verify
     * @return whether valid.
     */
    public static boolean isValidEworkIdPrefix(String idPrefix) {
        return idPrefix.matches(REGEXP_EWORK_ID_PREFIX);
    }

    /**
     * To verify if a email is valid. <P>A valid email can contain alpha, digits, (-), (_), (.) to form a style of
     * username@domain.ext.
     *
     * @param email - the email to verify
     * @return whether valid.
     */
    public static boolean isValidEmail(String email) {
        return email.matches(REGEXP_EMAIL);
    }

    /**
     * To verify if a phone number is valid. <P>This phone number should be either a chinese phone number or a
     * international phone number
     *
     * @param phone the phone number to verify
     * @return true or false
     * @see #isValidInternationalPhone
     * @see #isValidLocalPhone
     */
    public static boolean isValidPhone(String phone) {
        return isValidLocalPhone(phone) || isValidInternationalPhone(phone) || isValidChineseMobile(phone);
    }

    /**
     * To verify if the specified string is a valid international phone number. <P>A valid phone number can contain
     * digits and dash(-), and starts with '+', to form a style of "+xxxx-xxxx-xxxxxxxx[-xxxxxx]". <P>To verify an
     * normal phone number, especially only in china, please refer to isValidPhone() method.
     *
     * @param phone - the phone number to verify
     * @return true or false
     * @see #isValidPhone
     * @see #isValidLocalPhone
     */
    public static boolean isValidInternationalPhone(String phone) {
        return phone.matches(REGEXP_INTER_PHONE);
    }

    /**
     * To verify if the specified string is a valid local phone number. <P>A valid chinese phone number can contain
     * digits and dash(-), to form a style of "0xxxx-xxxxxxxx[-xxxxxx]". <P>To verify an international phone number
     * start with "+", please refer to isValidInternationalPhone() method.
     *
     * @param phone the phone number to verify
     * @return true or false
     * @see #isValidInternationalPhone
     * @see #isValidPhone
     */
    public static boolean isValidLocalPhone(String phone) {
        return phone.matches(REGEXP_PHONE);
    }

    /**
     * To verify if a mobile number is valid.
     * <p/>
     * A valid mobile number can contain digits and starts with 13, to form a style of "13xxxxxxxxx".
     * <p/>
     * Mobile numbers in other countries are assumed as the common phone no.
     *
     * @param mobile the mobile number to verify
     * @return true or false
     * @see #isValidInternationalPhone
     * @see #isValidLocalPhone
     * @see #isValidPhone
     */
    public static boolean isValidChineseMobile(String mobile) {
        return mobile.matches(REGEXP_MOBILE);
    }

    /**
     * To verify if a icq/qq number is valid. <P>A valid icq/qq number should be a 5-12 number.
     *
     * @param qno the icq/qq number to verify
     * @return true or false
     */
    public static boolean isValidQNumber(String qno) {
        return qno.matches(REGEXP_ICQ_QQ);
    }

    public static boolean isValidBirthday(String birthday) {
        return birthday.matches(REGEXP_BIRTHDAY);
    }

    /**
     * To verify if a website url is valid. <P>A valid website url should satisfy: <Ol> <Li>Start with 'http://'
     * <Li>followed by a domain name just containing: alpha, digits, dash(-), and dot(.) <Li>may followed by a path and
     * a query string, which can contain any printale. </Ol>
     *
     * @param website the website url to verify
     * @return true or false
     */
    public static boolean isValidWebsite(String website) {
        return website.matches(REGEXP_WEBSITE);
    }

    /**
     * To verify if a id card No. is valid.
     * <p/>
     * <P>A valid Id card no. should be a 15 or 18 digits number. <P>18 digits number can be ended by a 'x'.
     *
     * @param idCardNo the id card number to verify
     * @return true or false
     */
    public static boolean isValidChineseIdCard(String idCardNo) {
        return idCardNo.matches(REGEXP_IDCARD_CHN_OLD) || idCardNo.matches(REGEXP_IDCARD_CHN_NEW);
    }

    /**
     * To verify that whether the input string is a valid ip address. <P>A valid ip address is like the form of
     * 000.000.000.000, and each segment should not be larger than {@code 0xFF}.
     *
     * @param ip the ip address to verify
     * @return true or false
     */
    public static boolean isValidIpAddress(String ip) {
        if (!ip.matches(REGEXP_IP_ADDRESS)) {
            return false;
        }

        //segments should be no larger than 0xFF
        String[] idSegment = ip.split("\\.");
        for (String seg : idSegment) {
            if (Integer.parseInt(seg) > MAX_IPV4_VALUE) {
                return false;
            }
        }

        return true;
    }

    /**
     * To verify if a post code is valid.
     * <p/>
     * <P>A valid post code should be a 6 digits number
     *
     * @param postcode the postcode number to verify
     * @return true or false
     */
    public static boolean isValidPostcode(String postcode) {
        return postcode.matches(REGEXP_POSTCODE);
    }

    /**
     * To verify if a eWork eName is valid.
     * <p/>
     * A valid ename should be like "qualified-name@qualified-name".
     *
     * @param ename   the ename to verify
     * @param charset the encoding charset of the string to verify
     * @return true or false
     */
    public static boolean isValidEworkEname(String ename, String charset) {
        if (ename.indexOf("@") <= 0) {
            return false;
        }

        String[] parts = ename.split("@");

        return isQualifiedName(parts[0], charset) && isQualifiedName(parts[1], charset);
    }

    /**
     * To check whether a string in specified encoding charset is in valid length.
     *
     * @param str     the string to check
     * @param charset the encoding charset of the string
     * @param length  the length of the string to compare with
     * @return true if the length of str is no more than <code>length</code>, otherwise false. If str is null, false
     *         would be returned. If charset is null or not supported, the system default charset will be applied.
     */
    public static boolean isValidLength(String str, String charset, int length) {
        if (str == null) {
            return false;
        }

        if (charset == null) {
            if (str.getBytes().length > length) {
                return false;
            }
        }

        try {
            if (str.getBytes(charset).length > length) {
                return false;
            }
        }
        catch (UnsupportedEncodingException e) {
            if (str.getBytes().length > length) {
                return false;
            }
        }

        return true;
    }
    
    /**
     * check the amount which the user entered whether it is a valide number.
     * 
     * @param amount the user enter the amount of payment .
     * @return true if it is a number,else false
     */
    public static boolean isValideAmount(String amount){
        return amount.matches(REGEXP_AMOUNT);
    }

    /**
     * Check the version whether a valid Konlink version string.
     * <p/>
     * A valid version should be like 7.01.02.00 or 11.00.02.00.
     *
     * @param version the version to verify.
     * @return true if valid.
     */
    public static boolean isValidVersion(String version) {
        return version.matches(REGEXP_VERSION);
    }
    
    /**
     * Check the date
     * @param dateStr
     * @return boolean
     */
    public static boolean isValidMeetDate(String dateStr){
    	return dateStr.matches(REGEXP_DATE);
    }

    
}
