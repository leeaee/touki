package cn.touki.web.exception;

/**
 * The exception to describe a too long string.
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.1.1.1 $
 * @since 7.00.00
 */
public class StringTooLongException extends WebException {

	private static final long serialVersionUID = 1L;
	//Properties
    private static final String MSG_KEY = "exception.invalid.strtoolong";

    //Constructor
    /**
     * 用属性名称，限制长度来构造。
     *
     * @param propertyName 超长的字符串的属性名称
     * @param limitLength  限制的长度
     */
    public StringTooLongException(String propertyName, int limitLength) {
        super(MSG_KEY, new Object[]{"{" + propertyName + "}", new Integer(limitLength)});
    }

    /**
     * 用属性名称，限制长度以及一个原因来构造。
     *
     * @param propertyName 超长的字符串的属性名称
     * @param limitLength  限制的长度
     * @param root         引发的原因
     */
    public StringTooLongException(String propertyName, int limitLength, Throwable root) {
        super(MSG_KEY, new Object[]{"{" + propertyName + "}", new Integer(limitLength)}, root);
    }

    //Methods
}