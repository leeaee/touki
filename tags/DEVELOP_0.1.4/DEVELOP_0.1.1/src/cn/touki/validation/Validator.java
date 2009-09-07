package cn.touki.validation;

/**
 * An interface to provide schema to validate user input data.
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.1.1.1 $
 * @since 7.00.00
 */
public interface Validator {

    /**
     * To check whether this validator support the specified class.
     *
     * @param className the class type to check supports
     * @return <code>true</code> on support and <code>false</code> on not.
     */
    boolean supports(Class<?> className);

    /**
     * To validate the bean specified by <code>obj</code>. The <code>obj</code> should be a type supported by the {@link
     * #supports} method.
     *
     * @param obj the object to be validated.
     */
    void validate(Object obj) throws ValidationException;

} // end interface
