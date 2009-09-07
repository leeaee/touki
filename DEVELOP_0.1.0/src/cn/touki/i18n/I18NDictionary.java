package cn.touki.i18n;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.touki.util.StringUtils;

/**
 * A I18NDictionary Used to translate I18NMessage and I18NException.
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.4 $
 * @since 7.00.00
 */
public class I18NDictionary {

    //Properties
    public static final String BUNDLE_BASE_NAME = "cn.touki.i18n.resource.LangResource";
    public static final Locale DEFAULT_LOCALE = Locale.getDefault();

    protected static Logger logger = LoggerFactory.getLogger(I18NDictionary.class);

    //Methods

    /**
     * 用默认语言翻译I18NException实例。
     * <p/>
     * 该方法相当于：
     * <pre>
     * I18NDictionary.translate(e.getI18NMessage, Locale.getDefault());
     * </pre>
     *
     * @param e I18NExceotion实例。
     * @return 翻译后的字符串。
     * @see #translate(I18NMessage, Locale)
     * @see I18NMessage
     * @see I18NException
     */
    public static String translate(I18NException e) {

        return translate(e.getI18NMessage(), DEFAULT_LOCALE);
    }

    /**
     * 用指定语言 <code>locale</code> 翻译I18NException实例。
     * <p/>
     * 该方法相当于：
     * <pre>
     * I18NDictionary.translate(e.getI18NMessage, locale);
     * </pre>
     *
     * @param e I18NExceotion实例。
     * @return 翻译后的字符串。
     * @see #translate(I18NMessage, Locale)
     * @see I18NMessage
     * @see I18NException
     */
    public static String translate(I18NException e, Locale locale) {

        return translate(e.getI18NMessage(), locale);
    }

    /**
     * 用默认语言翻译I18NMessage实例。
     * <p/>
     * 该方法相当于：
     * <pre>
     * I18NDictionary.translate(msg, Locale.getDefault());
     * </pre>
     *
     * @param msg I18NMessage实例。
     * @return 翻译后的字符串。
     * @see #translate(I18NMessage, Locale)
     */
    public static String translate(I18NMessage msg) {

        return translate(msg, DEFAULT_LOCALE);
    }

    /**
     * 用指定的语言<code>locale</code>翻译I18NMessage实例。
     * <p/>
     * 若指定语言不存在，或者I18NMessage对应的消息不存在，则返回 {@link I18NMessage#getMsgKey()}。
     *
     * @param msg    要翻译的I18NMessage实例。
     * @param locale 翻译用的语言
     * @return 翻译后的字符串。
     */
    public static String translate(I18NMessage msg, Locale locale) {

        return getMessage(msg.getMsgKey(), msg.getParams(), locale);
    }

    /**
     * 返回所有可用语言资源对应的Locale列表.
     *
     * @return List<Locale> 所有语言资源对应的Locale
     */
    public static List<Locale> getLocales() {
        String[] langs = XmlResourceBundle.getBundle(BUNDLE_BASE_NAME).getString("bundle.available").split(",");
        List<Locale> locales = new ArrayList<Locale>();

        for (String lang : langs) {
            locales.add(getLocaleWithString(lang));
        }

        return locales;
    }

    /**
     * 根据由“lang_country_variant”格式指定的Locale对象.
     *
     * @return <code>str</code>对应的Locale实例
     */
    public static Locale getLocaleWithString(String str) {
        String[] langTemp = str.split("_");

        if (langTemp.length == 1) {
            return new Locale(str);
        }
        else if (langTemp.length == 2) {
            return new Locale(langTemp[0], langTemp[1]);
        }
        else {
            return new Locale(langTemp[0], langTemp[1], langTemp[2]);
        }
    }

    /**
     * 用默认语言取得<code>key</code>对应的消息。
     * <p/>
     * 该方法相当于：
     * <pre>
     * I18NDictionary.getMessage(key, Locale.getDefault());
     * </pre>
     *
     * @param key 消息的key
     * @return 翻译后的消息字符串。
     * @see #getMessage(String, Locale)
     */
    public static String getMessage(String key) {

        return getMessage(key, DEFAULT_LOCALE);
    }

    /**
     * 用指定的语言 <code>locale</code> 获取 <code>key</code> 对应的消息。
     * <p/>
     * 若语言资源不存在，或者key对应的消息不存在，则返回key本身。
     *
     * @param key    消息的key
     * @param locale 指定翻译用的语言
     * @return 找到的key对应的消息。
     */
    public static String getMessage(String key, Locale locale) {

        return getMessage(key, null, locale);
    }

    /**
     * 用默认的语言，翻译key及其参数params。
     * <p/>
     * 本方法相当于：
     * <pre>
     * I18NDictionary.getMessage(key, params, Locale.getDefault());
     * </pre>
     *
     * @param key    消息的key
     * @param params 消息的参数
     * @return 翻译后的消息
     * @see #getMessage(String, Object[], Locale)
     */
    public static String getMessage(String key, Object[] params) {

        return getMessage(key, params, DEFAULT_LOCALE);
    }

    /**
     * 用指定语言<code>locale</code>翻译<code>key</code>及其参数<code>params</code>.
     * <p/>
     * 对于参数集params， <Ul> <Li>若参数是用花括号{}包括起来的字符串，则先将该字符串转换成对应的消息，再作为参数传递。 <Li>若参数是 I18NMessage 的实例，则先将该国际化消息翻译后再作为参数传递。
     * </Ul>
     *
     * @param key    国际化消息的 key
     * @param params 国际化消息的参数
     * @param locale 翻译用的语言
     * @return 返回翻译后的字符串，若locale对应的语言资源不存在，或者key对应的消息不存在，则返回key本身。
     */
    public static String getMessage(String key, Object[] params, Locale locale) {

        if (key == null) {
            return null;
        }

        String msg;
        try {
            msg = XmlResourceBundle.getBundle(BUNDLE_BASE_NAME, locale).getString(key);
        }
        catch (MissingResourceException e) { //若没有获得资源，或者key对应的消息不存在，则返回key本身。
            //"--"默认符号不必写入日志
            if (!key.equals("--")) {
                logger.warn("Language Resource for locale [" + locale + "] is missing! MsgKey = " + key);
            }
            return key;
        }

        //检查本消息是否带参数
        if (params != null) {

            //在将params的内容作为参数之前，需先把由{}括起来的字符串翻译，并将I18NMessage实例先翻译。
            Object[] tempParams = new Object[params.length];

            for (int i = 0; i < params.length; i++) {

                //某参数是用花括号{}包含的字符串
                if (params[i] instanceof String) {
                    String param = (String) params[i];
                    if (StringUtils.isQuotedWithBraces(param)) {
                        tempParams[i] = getMessage(StringUtils.trimBraces(param), locale);
                    }
                    else {
                        tempParams[i] = param;
                    }
                }

                //某参数本身就是一个I18NMessage.
                else if (params[i] instanceof I18NMessage) {
                    tempParams[i] = translate((I18NMessage) params[i], locale);
                }

                else {
                    tempParams[i] = params[i];
                }
            }

            return MessageFormat.format(msg, tempParams);
        }
        else {
            return MessageFormat.format(msg, new Object[0]);
        }

    }

    /**
     * 根据传入的 {@code locales}，返回一个修改了顺序的 {@link #getLocales} Locale 链表，使得该顺序更符合用户的期望。
     *
     * @param locales 用户期望的 {@code locale} 顺序，可用 {@code HttpServletRequest.getLocales()} 得到。
     * @return 和 {@link #getLocales} 对象相同元素的 Locale 链表。
     */
    public static List<Locale> getUserPreferredLocales(Enumeration<?> locales) {

        List<Locale> sysLocales = getLocales();
        List<Locale> usrLocales = new ArrayList<Locale>();
        List<Locale> result = new ArrayList<Locale>();
        List<Locale> unMatchedSysLocales = new ArrayList<Locale>();
        List<Locale> unrecognizableLocales = new ArrayList<Locale>();
        List<Locale> tempLocales = new ArrayList<Locale>();

        //Change Enumeration into List.
        while (locales.hasMoreElements()) {
            Locale locale = (Locale) locales.nextElement();
            usrLocales.add(locale);
        }

        //fist loop, find completely matched locales, add match locales to result,
        //and unmatch user locales to unMatchedSysLocales for further loop.
        Iterator<Locale> sysIter = sysLocales.iterator();

        for (Locale locale : usrLocales) {

            unMatchedSysLocales.clear();

            while (sysIter.hasNext()) {
                Locale sysLocale = (Locale) sysIter.next();

                if (locale.toString().equals(sysLocale.toString())) {
                    result.add(sysLocale);
                }
                else {
                    unMatchedSysLocales.add(sysLocale);
                }
            }

            tempLocales.clear();
            tempLocales.addAll(unMatchedSysLocales);
            sysIter = tempLocales.iterator();
        }

        //second loop, find those locales in sysLocale whose language match user's perference.
        Iterator<Locale> unMatchedIter = unMatchedSysLocales.iterator();

        for (Locale locale : usrLocales) {
            String language = locale.getLanguage();

            unrecognizableLocales.clear();

            while (unMatchedIter.hasNext()) {
                Locale sysLocale = (Locale) unMatchedIter.next();

                if (sysLocale.getLanguage().equals(language)) {
                    result.add(sysLocale);
                }
                else {
                    unrecognizableLocales.add(sysLocale);
                }
            }

            tempLocales.clear();
            tempLocales.addAll(unrecognizableLocales);
            unMatchedIter = tempLocales.iterator();
        }

        //add userUnRecogNizableLocales to result.
        result.addAll(unrecognizableLocales);

        return result;
    }
}
