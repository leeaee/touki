package cn.touki.web.taglib;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import cn.touki.i18n.I18NException;
import cn.touki.web.core.servlet.Constants;
import cn.touki.web.exception.WebException;
import cn.touki.web.view.Button;

public class WebExceptionTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	//Properties
    private Throwable exception;
    private boolean debug;
    private String formLevel;

    //Constructor
    public WebExceptionTag() {
    }

    //Methods
    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @SuppressWarnings("unchecked")
	@Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        ServletRequest req = pageContext.getRequest();
        Locale userLocale = (Locale) pageContext.getSession().getAttribute(Constants.USER_LOCALE_KEY);

        if (this.exception == null) {
            exception = (Throwable) req.getAttribute(Constants.ERROR_MESSAGE);
            formLevel = "warn";
        }
        else {
            formLevel = "error";
        }

        String message;
        if (exception instanceof I18NException) {
            message = ((I18NException) exception).getMessage(userLocale);
        }
        else if (exception instanceof RuntimeException) {
            message = new WebException(WebException.RUNTIME, exception).getMessage(userLocale);
        }
        else {
            message = exception.getMessage();
        }

        try {
            out.println("<table width=\"400\" cellpadding=\"5\" cellspacing=\"0\" class=\"msgbox\">\n");

            //Table title
            out.println("    <tr>\n" +
                    "        <th class=\"" + formLevel + "\"></th>\n" +
                    "    </tr>\n");

            //exception message
            out.println("    <tr>\n" +
                    "        <td>\n");

            //if debug, output exception's class type
            if (debug) {
                out.println("<p/>Exception Type: " + exception.getClass().getName() + "\n");
            }

            out.println("<p/>" + message + "</td>\n" +
                    "    </tr>\n");

            //if debug, out exception's stack trace
            if (debug) {
                out.println("    <tr>\n" +
                        "        <td><pre>");
                exception.printStackTrace(new PrintWriter(out));
                out.println("        </pre></td>\n" +
                        "    </tr>\n");
            }
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

            out.println("</table>\n");
        }
        catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {

        return SKIP_BODY;
    }
}
