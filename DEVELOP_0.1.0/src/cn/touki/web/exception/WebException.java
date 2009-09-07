package cn.touki.web.exception;

import cn.touki.i18n.I18NException;

/**
 * Base Exception Class in EWWeb Projects which extends from I18NException. <p /> All other Exception which needs to
 * implements I18N should extends from this exception class.
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.2 $
 * @since 7.00.00
 */
public class WebException extends I18NException {

	private static final long serialVersionUID = 1L;

	//Properties
    /**
     * 出现NullPointerException时，报告给用户的消息. 如：
     * <pre>
     * catch (NullPointerException e) {
     *     throw new EWWebException(EWWebException.NULL);
     * }
     * </pre>
     */
    public static final String NULL = "exception.null.pointer";

    /**
     * 出现RuntimeException时，报告给用户的消息. 如：
     * <pre>
     * catch (RuntimeException e) {
     *     throw new EWWebException(EWWebException.RUNTIME, e);
     * }
     * </pre>
     */
    public static final String RUNTIME = "exception.runtime";

    /**
     * 一个字典的key，该 key 没有内容，只有一个参数{0}.
     */
    public static final String BLANK = "exception.blank";

    /**
     * URL不存在时，报告给用户的消息.
     */
    public static final String URL_NOT_FOUND = "exception.urlnotfound";

    /**
     * 出现敏感词汇时，报告给用户的消息.
     */
    public static final String SENSITIVE_WORDS = "exception.sensiwords";

    /**
     * 网络连接不通时，报告给用户的消息.
     */
    public static final String NETWORK = "exception.network";

    //Constructor
    /**
     * 用一个消息<code>msg</code>构造一个Exception. <p /> 若该msg在字典中有对应的消息，则该msg将作为该消息的key，否则，getMessage()方法将直接返回该msg。
     *
     * @param msg 错误消息
     */
    public WebException(String msg) {
        super(msg);
    }

    /**
     * 用一个消息<code>msg</code>及产生本异常的原因root构造一个Exception. <p /> 在翻译国际化消息时，msg将作为在字典中查找消息的key，若不存在，则getMessage()直接返回该msg。
     * <p /> 对于原因<code>root</code>， <Ul> <Li>root 为I18NException的实例，则 root 的getMessage()消息会作为msg对应消息的参数。 <Li>root
     * 不是I18NException实例，则 root 自身的消息作为msg对应的消息参数。 </Ul>
     *
     * @param msg  错误消息
     * @param root 异常的原因
     */
    public WebException(String msg, Throwable root) {
        super(msg, root);
    }

    /**
     * 用一个消息<code>msg</code>及其参数<code>params</code>构造一个Exception. <p /> 在翻译国际化消息时，params将作为以<code>msg</code>作为key的消息的参数；若msg在字典中不存在对应的消息，
     * 则getMessage()方法直接返回该msg。
     *
     * @param msg    错误消息
     * @param params 消息的参数
     */
    public WebException(String msg, Object[] params) {
        super(msg, params);
    }

    /**
     * 用一个消息<code>msg</code>，其参数<code>params</code>，及其原因root构造一个异常。 <p /> 在翻译国际化消息的时候，params
     * 将优先作为消息的参数。若params数组的长度为0，则用root的getMessage()消息作为参数。
     *
     * @param msg    错误消息
     * @param params 消息参数
     * @param root   异常的原因
     */
    public WebException(String msg, Object[] params, Throwable root) {
        super(msg, params, root);
    }

    /**
     * 用一个cause构在一个Exception。
     *
     * @param cause 造成本 Exception 的原因。
     */
    public WebException(Throwable cause) {
        super(cause);
    }

    //Methods
}