package cn.touki.validation;

import cn.touki.i18n.I18NException;

public class ValidationException extends I18NException {
    //Properties
	private static final long serialVersionUID = 1L;

	//Constructor
    /**
     * 构造一个 ValidationException.
     *
     * @param beanName      校验失败对应的bean class
     * @param propertyName  出错的 bean class 的属性名称
     * @param propertyValue 出错的 bean 属性的值
     */
    public ValidationException(Class<?> beanName, String propertyName, Object propertyValue) {
        super(beanName.toString() + "." + propertyName + "'s value: " + propertyValue + " is not valid!");
    }

    /**
     * 用一个 msgKey 和一个 msgParams 数组构造一个 ValidationException.
     */
    public ValidationException(String msgKey, Object[] msgParams) {
        super(msgKey, msgParams);
    }

    /**
     * 用一个 msgKey 和一个 msgParams 数组以及一个原因构造一个 ValidationException.
     */
    public ValidationException(String msgKey, Object[] msgParams, Throwable cause) {
        super(msgKey, msgParams, cause);
    }

    /**
     * 用一个原因构造.
     *
     * @param root 导致本 Exception 的原因
     */
    public ValidationException(Throwable root) {
        super(root);
    }

    //Methods

} //end class
