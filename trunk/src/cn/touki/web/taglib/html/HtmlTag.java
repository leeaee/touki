package cn.touki.web.taglib.html;

import java.util.Locale;

import javax.servlet.jsp.tagext.TagSupport;

public class HtmlTag extends TagSupport {

	private static final long serialVersionUID = 3624864486436296150L;
	
	//Properties
    protected String name;
    protected String style;
    protected String decorate;
    protected Locale locale;
    protected String localeRef;

    //Constructor
    protected HtmlTag() {
    }

    //Methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDecorate() {
        return decorate;
    }

    public void setDecorate(String decorate) {
        this.decorate = decorate;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getLocaleRef() {
        return localeRef;
    }

    public void setLocaleRef(String localeRef) {
        this.localeRef = localeRef;
    }

    protected Locale getCurrentLocale() {
        // decide the locale to translate texts
        if (this.locale == null) {
            if (localeRef != null && localeRef.length() > 0) {
                locale = (Locale) pageContext.getSession().getAttribute(localeRef);
            }
            else {
                locale = Locale.getDefault();
            }
        }

        return locale;
    }

}
