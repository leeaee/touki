package cn.touki.web.taglib;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import cn.touki.i18n.I18NDictionary;
import cn.touki.i18n.I18NMessage;
import cn.touki.web.core.servlet.Constants;
import cn.touki.web.view.Button;

/**
 * A tag to display I18NMessage in JSP file.
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.1.1.1 $
 * @since 7.00.00
 */
public class WebMessageTag extends TagSupport {

	private static final long serialVersionUID = 8103775209068020707L;
	//Properties
    private I18NMessage message;

    //Constructor
    public WebMessageTag() {
    }

    //Methods
    public I18NMessage getMessage() {
        return message;
    }

    public void setMessage(I18NMessage message) {
        this.message = message;
    }

    @SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        Locale userLocale = (Locale) pageContext.getSession().getAttribute(Constants.USER_LOCALE_KEY);
        ServletRequest req = pageContext.getRequest();

        String messageStr = I18NDictionary.translate(message, userLocale);

        try {
            out.println("<table width=\"400\" cellpadding=\"5\" cellspacing=\"0\" class=\"msgbox\">\n");

            //Table title
            out.println("    <tr>\n" +
                    "        <th class=\"info\"></th>\n" +
                    "    </tr>\n");

            //output message
            out.println("    <tr>\n" +
                    "        <td>\n");

            out.println("       <p/>" + messageStr + "</td>\n" +
                    "    </tr>\n");

            //output buttons
            out.println("    <tr>\n" +
                    "        <td class=\"button\">");

            if (req.getAttribute(Constants.MSG_BUTTON) != null) {
                List<Button> buttons = (List<Button>) req.getAttribute(Constants.MSG_BUTTON);
                for (Button button : buttons) {
                    out.println(button.getHtml(userLocale));
                }
            }

            out.println("        </td>\n" +
                    "    </tr>\n");

            //table end
            out.println("</table>\n");
        }
        catch (IOException e) {
            throw new JspException(e);
        }


        return SKIP_BODY;
    }
}
