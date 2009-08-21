package cn.touki.web.view;

import java.io.Serializable;
import java.util.Locale;

import cn.touki.i18n.I18NDictionary;
import cn.touki.i18n.I18NMessage;

/**
 * The HTML button.
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.1.1.1 $
 * @since 7.00.00
 */
public class Button implements Serializable {

	private static final long serialVersionUID = 1L;
	//Properties
    private String name = "";
    private String action;
    private I18NMessage label;
    private String typeClass = "bttn";

    /**
     * 按钮标签"确定"，对应action.ok.
     */
    public static final I18NMessage LABEL_OK = new I18NMessage("act.ok");

    /**
     * 按钮标签"取消"，对应action.cancel.
     */
    public static final I18NMessage LABEL_CANCEL = new I18NMessage("act.cancel");

    /**
     * 按钮标签"返回"，对应action.back.
     */
    public static final I18NMessage LABEL_BACK = new I18NMessage("act.back");

    /**
     * 按钮动作，返回上一页面。
     */
    public static final String ACT_BACK = "JavaScript:history.back();";

    /**
     * 按钮动作，关闭当前窗口。
     */
    public static final String ACT_CLOSE = "JavaScript:window.close();";

    /**
     * 按钮动作，返回首页。
     */
    public static final String ACT_GO_HOME = "location.href='./'";

    /**
     * 按钮风格，宽度自动根据文字扩展。
     */
    public static final String STYLE_AUTO = "bttn";

    /**
     * “确定”，点击后返回上页的按钮
     */
    public static final Button OK_BACK = new Button("btnOKBack", LABEL_OK, ACT_BACK);

    /**
     * “返回”，单击后返回上页的按钮
     */
    public static final Button BACK_BACK = new Button("btnBack", LABEL_BACK, ACT_BACK);

    /**
     * “确定”，点击后回首页的按钮
     */
    public static final Button OK_HOME = new Button("BtnOKHome", LABEL_OK, ACT_GO_HOME);

    //Constructor
    public Button() {
    }

    /**
     * 用标签文字和动作构建一个按钮。
     */
    public Button(I18NMessage label, String action) {
        if (label == null) {
            label = new I18NMessage("");
        }

        if (action == null) {
            action = "";
        }

        this.label = label;
        this.action = action;
    }

    /**
     * 用名称，标签文字和动作构建一个按钮。
     */
    public Button(String name, I18NMessage label, String action) {
        this(label, action);
        this.name = name;
    }

    //Methods
    /**
     * 获得该按钮的HTML代码.
     *
     * @param locale 用来翻译标签的locale.
     * @return HTML代码的字符串
     */
    public String getHtml(Locale locale) {
        StringBuffer html = new StringBuffer("<input type=\"button\" name=\"");

        html.append(name);
        html.append("\" ");

        html.append("value=\"");
        html.append(I18NDictionary.translate(label, locale));
        html.append("\" ");

        html.append("class=\"");
        html.append(typeClass);
        html.append("\" ");

        html.append("onclick=\"");
        html.append(action);
        html.append("\" ");

        html.append("/>");

        return html.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public I18NMessage getLabel() {
        return label;
    }

    public void setLabel(I18NMessage label) {
        this.label = label;
    }

    public String getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(String typeClass) {
        this.typeClass = typeClass;
    }
}
