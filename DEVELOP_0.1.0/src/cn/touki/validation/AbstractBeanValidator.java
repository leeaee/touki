package cn.touki.validation;

/**
 * An implementation of Validator interface, with an i18n exception.
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.2 $
 * @since 7.00.00
 */
public abstract class AbstractBeanValidator implements Validator {

    //Properties
    private Class<?> beanClass;

    //Constructor

    //Methods
    /**
     * Get the class type of the bean that is to validate by this validator.
     *
     * @return the class of supported type.
     */
    public Class<?> getBeanClass() {
        return beanClass;
    }

    /**
     * Set the class type of the bean that is to validate by this validator. If specified class was not found in class
     * path, the {@link Object} would be assigned.
     *
     * @param beanClassName full qulified class name
     */
    public void setBeanClassName(String beanClassName) {
        try {
            this.beanClass = Class.forName(beanClassName);
        }
        catch (ClassNotFoundException e) {
            this.beanClass = Object.class;
        }
    }

    public boolean supports(Class<?> className) {
        return beanClass.isAssignableFrom(className);
    }
}
