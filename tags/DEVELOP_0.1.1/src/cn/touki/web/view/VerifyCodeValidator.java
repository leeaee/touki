package cn.touki.web.view;

import javax.servlet.http.HttpServletRequest;

/**
 * The validator to verify whether a correct verify code has been sent. The default name of {@code reqParamName} and
 * {@code sessionAttrName} are '{@code verifyCode}' and '{@code jpeg-random-code}'
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.1.1.1 $
 * @since 2.0
 */
public class VerifyCodeValidator {

    //Properties
    private String reqParamName = "verifyCode";
    private String sessionAttrName = "jpeg-random-code";

    //Constructor
    public VerifyCodeValidator() {
    }
    
    //Methods

    /**
     * Check the verify code set by the HTML form.
     * <p/>
     * This only available when following conditions matched: <ul> <li>a field named {@link #getReqParamName} placed in
     * the form, which can be set with method {@link #setReqParamName}. <li>the correct verify code has been put in the
     * session with attribute name {@code sessionAttrName}, which can be set with method {@link #setSessionAttrName} to
     * this validator. </ul>
     * <p/>
     * If above conditions matched and the two codes do not match, {@code false} would be returned, otherwise, {@code
     * true} is returned.
     *
     * @param req HttpServletRequest
     */
    public boolean verify(HttpServletRequest req) {

        //If any verify code has been set in the form in field 'verifyCode', check it.
        //This is assumed that a correct verify code has been set in the session with the attribute name 'verifyCodeAttrName'.
        String verifyCode = req.getParameter(reqParamName);

        // We consider there's no verify code when it is null,
        // and a blank string ususally caused by a missing filling of user
        if (verifyCode != null) {
            if (this.sessionAttrName != null && this.sessionAttrName.length() > 0) {
                String sysVeriCode = (String) req.getSession().getAttribute(this.sessionAttrName);
                if (sysVeriCode != null && !sysVeriCode.equals(verifyCode)) {
                    return false;
                }
            }
        }

        return true;
    }

    public String getReqParamName() {
        return reqParamName;
    }

    public void setReqParamName(String reqParamName) {
        this.reqParamName = reqParamName;
    }

    public String getSessionAttrName() {
        return sessionAttrName;
    }

    public void setSessionAttrName(String sessionAttrName) {
        this.sessionAttrName = sessionAttrName;
    }

}
