package cn.touki.web.core.validation;

import javax.servlet.http.HttpServletRequest;

import cn.touki.web.exception.WebException;
import cn.touki.web.view.VerifyCodeValidator;

/**
 * A basic abstact web validator.
 * 
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.2 $
 * @since 7.00.00
 */
public abstract class WebValidator {

    // Properties
    private VerifyCodeValidator verifyCodeValidator;

    // Constructor

    // Methods
    /**
     * Process this validator. Invoke {@code validate()} method.
     * 
     * @param req HttpServletRequest
     */
    public void process(HttpServletRequest req) throws WebValidationException, WebException {

        if (this.verifyCodeValidator != null && !this.verifyCodeValidator.verify(req)) {
            throw new WebValidationException("prop.vericode");
        }

        validate(req);
    }

    /**
     * Validate the parameters in HttpServletRequest.
     * 
     * @param req HttpServletRequest
     * @return parameters parsed from request.
     */
    public abstract Object[] validate(HttpServletRequest req) throws WebValidationException, WebException;

    public VerifyCodeValidator getVerifyCodeValidator() {
        return verifyCodeValidator;
    }

    public void setVerifyCodeValidator(VerifyCodeValidator verifyCodeValidator) {
        this.verifyCodeValidator = verifyCodeValidator;
    }

}
