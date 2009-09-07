package cn.touki.web.core.validation;

import cn.touki.validation.ValidationException;

public class WebValidationException extends ValidationException {

	private static final long serialVersionUID = 1L;
	//Properties
    private static final String INVALID_PROP = "exception.invalid.property";

    //Constructor
    public WebValidationException(String propertyNameKey, Object value) {
        super(INVALID_PROP, new Object[]{"{" + propertyNameKey + "}", value});
    }

    public WebValidationException(String propertyNameKey, Object value, Throwable cause) {
        super(INVALID_PROP, new Object[]{"{" + propertyNameKey + "}", value}, cause);
    }

    public WebValidationException(String propertyNameKey) {
        this(propertyNameKey, "");
    }

    /**
     * 用一个原因构造.
     *
     * @param root 导致本 Exception 的原因
     */
    public WebValidationException(Throwable root) {
        super(root);
    }
    //Methods

}
