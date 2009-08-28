package cn.touki.i18n.Adapter;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.LocaleResolver;

import cn.touki.i18n.I18NDictionary;
import cn.touki.web.core.servlet.Constants;


/**
 * Local i18n dictonary implementation adpater for display taglib.
 * 
 * @author Liyi
 * 
 */
public class I18nLocalAdapter implements I18nResourceProvider, LocaleResolver {

    public Locale resolveLocale(HttpServletRequest request) {
    	
        Locale userLocale = null;
        HttpSession session = request.getSession(false);

        // Only check session if sessions are enabled
        if (session != null) {
            userLocale = (Locale) session.getAttribute(Constants.USER_LOCALE_KEY);
        }

        if (userLocale == null) {
            // Returns Locale based on Accept-Language header or the server default
            userLocale = request.getLocale();
        }

        return userLocale;
    }

    public String getResource(String resourceKey, String defaultValue, Tag tag, PageContext pageContext) {

        Locale userLocale = resolveLocale((HttpServletRequest) pageContext.getRequest());
        String title = I18NDictionary.getMessage(resourceKey, userLocale);

        return title;
    }

}
