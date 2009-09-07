package cn.touki.i18n;

import java.util.Locale;

/**
 * Exception Class implemented Internationalization.
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.4 $
 * @since 7.00.00
 */
public class I18NException extends Exception {

	private static final long serialVersionUID = 1L;
	//Properties
    private Object[] params;

    //Constructor
    /**
     * 用一个cause构在一个Exception。
     *
     * @param cause 造成本 Exception 的原因。
     */
    public I18NException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    /**
     * 用一个消息<code>msg</code>构造一个Exception.
     * <p/>
     * 若该msg在字典中有对应的消息，则该msg将作为该消息的key，否则，getMessage()方法将直接返回该msg。
     *
     * @param msg 错误消息
     */
    public I18NException(String msg) {
        super(msg);
    }

    /**
     * 用一个消息<code>msg</code>及其参数<code>params</code>构造一个Exception.
     * <p/>
     * 在翻译国际化消息时，params 将作为以 <code>msg</code> 作为 key 的消息的参数；若 msg 在字典中不存在对应的消息，则 getMessage() 方法直接返回该 msg。
     *
     * @param msg    错误消息
     * @param params 消息的参数
     */
    public I18NException(String msg, Object[] params) {
        super(msg);
        this.params = params;
    }

    /**
     * 用一个消息<code>msg</code>及产生本异常的原因root构造一个Exception.
     * <p/>
     * 在翻译国际化消息时，msg将作为在字典中查找消息的key，若不存在，则getMessage()直接返回该msg。
     * <p/>
     * 对于原因<code>root</code>，
     * <Ul>
     *  <Li>root 为I18NException的实例，则 root 的getMessage()消息会作为msg对应消息的参数。
     *  <Li>root 不是I18NException实例，则 root 自身的消息作为msg对应的消息参数。
     * </Ul>
     *
     * @param msg  错误消息
     * @param root 异常的原因
     */
    public I18NException(String msg, Throwable root) {
        super(msg, root);
    }

    /**
     * 用一个消息<code>msg</code>，其参数<code>params</code>，及其原因root构造一个异常。
     * <p/>
     * 在翻译国际化消息的时候，params 将优先作为消息的参数。若 params 数组的长度为 0，则用 root 的 getMessage() 消息作为参数。
     *
     * @param msg    错误消息
     * @param params 消息参数
     * @param root   异常的原因
     */
    public I18NException(String msg, Object[] params, Throwable root) {
        super(msg, root);
        this.params = params;
    }

    //Methods
    /**
     * 获得本Exception异常消息的参数
     *
     * @return Object数组
     */
    public Object[] getParams() {
        return params;
    }

    /**
     * 从本I18NException中抽象出I18NMessage对象实例，用于翻译和传递等。
     * <p/>
     * 本方法用 I18NException 的 msg，params 构造一个 I18NMessage 实例，如果本 Exception 没有参数，且其 cause 是 I18NException
     * 的实例，则将该 cause 的 getI18NMessage() 对象将作为参数构造。
     *
     * @return 一个I18NMessage实例
     */
    public I18NMessage getI18NMessage() {
        if (this.params != null && this.params.length > 0) {
            return new I18NMessage(super.getMessage(), this.params);
        }
        else if (this.getCause() != null) {
            if (this.getCause() instanceof I18NException) {
                I18NException cause = (I18NException) this.getCause();
                return new I18NMessage(super.getMessage(), cause.getI18NMessage());
            }
            else {
                return new I18NMessage(super.getMessage(), this.getCause().getMessage());
            }
        }
        else {
            return new I18NMessage(super.getMessage());
        }
    }

    /**
     * 用默认语言翻译本异常消息并返回字符串。
     * <p/>
     * 本方法相当于：
     * <pre>
     * getMessage(Locale.getDefault());
     * </pre>
     *
     * @return 翻译过后的消息，若msg在字典中对应的消息不存在，则直接返回该msg消息。
     * @see #getMessage(Locale)
     */
    @Override
    public String getMessage() {
        return I18NDictionary.translate(this);
    }

    /**
     * 用指定的语言 <code>locale</code> 翻译本异常，并返回消息的字符串。
     *
     * @param locale 翻译用的语言
     * @return 翻译后的消息，若 msg 在字典中对应的消息不存在，则直接返回该 msg 消息。
     * @see #getMessage()
     */
    public String getMessage(Locale locale) {
        return I18NDictionary.translate(this, locale);
    }
}