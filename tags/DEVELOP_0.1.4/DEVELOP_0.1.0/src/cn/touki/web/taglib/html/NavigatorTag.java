package cn.touki.web.taglib.html;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import cn.touki.i18n.I18NDictionary;
import cn.touki.util.RequestUtils;
import cn.touki.util.StringUtils;
import cn.touki.web.core.orm.Page;
import cn.touki.web.core.servlet.Constants;
import cn.touki.web.exception.WebException;

public class NavigatorTag<T> extends HtmlTag {

    // Properties

	private static final long serialVersionUID = -2747842035854074161L;

	public static final String MSG_KEY_DISPLAY_RANGE = "tag.navigator.listrange";

    public static final String MSG_KEY_BTN_FIRST = "tag.navigator.btn.first";

    public static final String MSG_KEY_BTN_PREV = "tag.navigator.btn.prev";

    public static final String MSG_KEY_BTN_NEXT = "tag.navigator.btn.next";

    public static final String MSG_KEY_BTN_LAST = "tag.navigator.btn.last";

    public static final String MSG_KEY_PAGES = "tag.navigator.pages";

    private Page<T> page;

    private String excludedParams = "";

    // Constructor
    public NavigatorTag() {
    }

    // Methods
    public Page<T> getPageInfo() {
        return page;
    }

    public void setPageInfo(Page<T> pageInfo) {
        this.page = pageInfo;
    }

    public String getExcludedParams() {
        return excludedParams;
    }

    public void setExcludedParams(String excludedParams) {
        this.excludedParams = excludedParams;
        this.excludedParams = this.excludedParams.replaceAll("\\*", ".*");
        this.excludedParams = this.excludedParams.replaceAll("\\?", ".");
    }

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        Locale curLocale = getCurrentLocale();

        StringBuffer html = new StringBuffer("<table name=\"" + this.name
                + "\" width=\"100%\" border=\"0\"");

        // add attribute 'class'
        if (this.style != null && this.style.length() > 0) {
            html.append(" class=\"" + this.style + '\"');
        }

        // add other attributs set by 'decorator'
        if (this.decorate != null && this.decorate.length() > 0) {
            html.append(' ' + this.decorate);
        }

        html.append(">\n");

        html.append("    <tr>\n");
        html.append("        <td><nobr>");

        html.append(I18NDictionary.getMessage(MSG_KEY_DISPLAY_RANGE, new Object[] { page.getEntryCount(),
                page.getEntryFromIndex(), page.getEntryToIndex() }, curLocale));

        html.append("        </nobr></td>\n");

        html.append("        <td width=\"100%\"></td>\n");
        html.append("        <td align=\"right\"><nobr>\n");

        // Buttons
        String queryString = getQueryString(req);

        int prevPageIndex = page.getPageIndex() <= 1 ? 1 : page.getPageIndex() - 1;
        int nextPageIndex = page.getPageIndex() >= page.getPageCount() ? page.getPageCount() : page
                .getPageIndex() + 1;

        html.append("           <input type=\"button\" name=\"navFirst\" value=\""
                + I18NDictionary.getMessage(MSG_KEY_BTN_FIRST, curLocale)
                + "\" class=\"navbttn\" onclick=\"location.href='?" + queryString + "1'\"" + (page.isHasPre() ? "" : " disabled ") + "/>\n");
        html.append("           <input type=\"button\" name=\"navPrev\" value=\""
                + I18NDictionary.getMessage(MSG_KEY_BTN_PREV, curLocale)
                + "\" class=\"navbttn\" onclick=\"location.href='?" + queryString + prevPageIndex + "'\"" + (page.isHasPre() ? "" : " disabled ") + "/>\n");
        html.append("           <input type=\"button\" name=\"navNext\" value=\""
                + I18NDictionary.getMessage(MSG_KEY_BTN_NEXT, curLocale)
                + "\" class=\"navbttn\" onclick=\"location.href='?" + queryString + nextPageIndex + "'\"" + (page.isHasNext() ? "" : " disabled ") + "/>\n");
        html.append("           <input type=\"button\" name=\"navLast\" value=\""
                + I18NDictionary.getMessage(MSG_KEY_BTN_LAST, curLocale)
                + "\" class=\"navbttn\" onclick=\"location.href='?" + queryString + page.getPageCount()+ "'\"" + (page.isHasNext() ? "" : " disabled ") + "/>\n");

        html.append("&nbsp;&nbsp;\n");

        // Page

        // page select
        StringBuffer select = new StringBuffer("<select name=\"pagelist\" onchange=\"location.href='?" + queryString
                + "' + this.options[this.options.selectedIndex].value\">\n");

        for (int i = 1; i <= page.getPageCount(); i++) {
            select.append("<option value=\"").append(i).append('\"').append(
                    page.getPageIndex() == i ? " selected" : "").append('>').append(i).append("</option>\n");
        }

        select.append("</select>\n");

        html.append(I18NDictionary.getMessage(MSG_KEY_PAGES,
                new Object[] { select.toString(), page.getPageCount() }, curLocale));

        html.append("         </nobr></td>\n");
        html.append("    </tr>\n");
        html.append("</table>\n");

        try {
            out.println(html.toString());
        } catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }

    @SuppressWarnings("unchecked")
    protected String getQueryString(HttpServletRequest req) {

        // Deal with query string
        StringBuffer newQuery = new StringBuffer();
        String paramValue="";
        if (req.getParameter("isNav") == null) {
            newQuery.append("isNav=true&");
        }
        String[] patterns = this.excludedParams.split(" ");

        Enumeration tmp = req.getParameterNames();
        List paraNames = Collections.list(tmp);
        Collections.reverse(paraNames);

        for (Object aParaName : paraNames) {
            String paraName = aParaName.toString();

            // Parameter 'pageIndex' does not need to build.
            if (paraName.equals(Constants.QUERY_PAGE_INDEX)) {
                continue;
            }

            // paramNames matches excludedParams
            boolean matched = false;
            for (String pattern : patterns) {
                if (paraName.matches(pattern)) {
                    matched = true;
                    break;
                }
            }

            if (matched) {
                continue;
            }

            newQuery.append(paraName).append('=');

            try {
                paramValue=RequestUtils.getParam(req,paraName);
            } catch (WebException e) {
                e.printStackTrace();
            }
            if (!StringUtils.isAscii(paramValue)) {
                newQuery.append(RequestUtils.urlEncode(paramValue));
            }else {
                newQuery.append(paramValue);
            }
            
            newQuery.append('&');
        }

        return newQuery.append(Constants.QUERY_PAGE_INDEX).append('=').toString();
    }

}
