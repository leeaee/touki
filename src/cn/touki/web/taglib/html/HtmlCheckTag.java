package cn.touki.web.taglib.html;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import cn.touki.i18n.I18NDictionary;

/**
 * An abstract Tag class for HtmlRadioTag and HtmlCheckboxTag.
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.2 $
 * @since 2.0
 */
public abstract class HtmlCheckTag extends HtmlTag {

	private static final long serialVersionUID = 1L;
	//Properties
    private String[] options;
    private String connector = "&nbsp;&nbsp;";

    //Constructor
    protected HtmlCheckTag() {
    }

    //Methods
    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        Locale curLocale = getCurrentLocale();

        StringBuffer basicAttr = new StringBuffer(" type=\"");
        basicAttr.append(getCheckType());
        basicAttr.append("\"");

        basicAttr.append(" name=\"" + this.name + "\"");

        if (this.style != null && this.style.length() > 0) {
            basicAttr.append(" class=\"" + this.style + "\"");
        }

        if (this.decorate != null && this.decorate.length() > 0) {
            basicAttr.append(" " + this.decorate);
        }

        //build html
        StringBuffer html = new StringBuffer();
        if (this.options != null && this.options.length > 0) {
            for (int i = 0; i < options.length; i++) {
                String option = options[i];

                html.append("<input").append(basicAttr);
                html.append(" value=\"").append(i).append("\"");

                //check whether this is a checked option
                if (isChecked(i)) {
                    html.append(" checked");
                }

                html.append(" /> ");
                html.append(I18NDictionary.getMessage(option, curLocale));

                if (i != options.length - 1) {
                    html.append(this.connector);
                }
            }
        }

        try {
            out.print(html);
        }
        catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }

    protected abstract boolean isChecked(int i);

    protected abstract String getCheckType();
}
