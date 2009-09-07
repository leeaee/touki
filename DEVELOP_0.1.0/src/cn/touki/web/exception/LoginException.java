package cn.touki.web.exception;

/**
 * Exception thrown when login.
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.1.1.1 $
 * @since 7.00.00
 */
public class LoginException extends WebException {

	private static final long serialVersionUID = 1L;
	//Properties
    private static final String FAILED = "exception.login.failed";
    private static final String FAILED_WITH_REASON = "exception.login.failed.reason";

    /**
     * 登录失败原因：密码不匹配！
     */
    public static final String REASON_INVALID_PWD = "exception.password.invalid";

    //Constructor
    /**
     * 由一个已知原因的 Throwable 构造一个LoginException.
     * <p/>
     * 最后将报告“登录失败！{...}”，后面为 <code>cause.getMessage()</code> 的内容。
     * <p/>
     * 通常，<code>EntityNotFoundException</code> 和 <code>EntityPausedException</code> 在这里很常用。
     *
     * @param cause 导致LoginException的原因
     * @see EntityNotFoundException
     * @see EntityPausedException
     */
    public LoginException(Throwable cause) {
        super(FAILED_WITH_REASON, cause);
    }

    /**
     * 由一个Object对象原因构造一个 LoginException. 这个Object将作为转换国际化消息的参数。
     *
     * @param reason 导致LoginException的原因
     */
    public LoginException(Object reason) {
        super(FAILED_WITH_REASON, new Object[]{reason});
    }

    /**
     * 用一个messageKey对应的消息作为原因构造一个 LoginException.
     *
     * @param reason 作为原因的消息的key，该字符串将先翻译后再作为登录失败的原因发送。
     */
    public LoginException(String reason) {
        super(FAILED_WITH_REASON, new Object[]{"{" + reason + "}"});
    }

    /**
     * 默认的LoginException. 将只报告“登录失败！”没有具体的原因解释。
     */
    public LoginException() {
        super(FAILED);
    }

    //Methods
}