package cn.touki.web.core.validation;

import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

import cn.touki.util.SensitiveWords;
import cn.touki.validation.AbstractBeanValidator;
import cn.touki.validation.ValidationException;
import cn.touki.web.view.VerifyCodeValidator;

/**
 * The implementation of AbstractBeanValidator in EWWEB project.
 * <p/>
 * Create a WebBeanValidator, following 3 steps below:
 * <p/>
 * <H6>Step 1 - Create a WebBeanValidator Class</H6>
 * <p/>
 * Implement the {@link #validate(HttpServletRequest, Object)} method and throw a {@link
 * com.WebValidationException.ewweb.core.validation.EWValidationException} when any bean property is invalid.
 * <p/>
 * Override methods {@code validateCreate} and {@code validateUpdate} if they need to be different. Their default
 * implementation is the same with {@link #validate}.
 * <p/>
 * Method {@link #validate} is not required and replaced by {@link #validate(HttpServletRequest, Object)}.
 * <p/>
 * <H6>Step 2 - Add this as a bean to {@code ewweb_validators.xml} file</H6>
 * <p/>
 * For example: <Pre> &lt;bean id="userValidator" class="com.konlink.ewweb.validation.UserValidator"&gt; &lt;property
 * name="beanClassName"&gt; &lt;value&gt;com.konlink.ewweb.core.entity.User&lt;/value&gt; &lt;/property&gt;
 * &lt;/bean&gt; </Pre>
 * <p/>
 * <H6>Step 3 - Add following hidden element in the HTML Form in which you need this validator.</H6>
 * <p/>
 * List all validators you needed if necessary. The value of the validator is the name of this "Validator Bean".
 * <pre>
 *   &lt;form ...&gt;
 *       ...
 *       &lt;input type="hidden" name="validators" value="userValidator" /&gt;
 *       &lt;input type="hidden" name="validators" value="userDetailValidator" /&gt;
 *       ...
 *   &lt;/form&gt;
 *   </pre>
 * <p/>
 * <p/>
 * The validators you specified in the HTML form would be loaded and run in the sequence of your specification. Each
 * validator will create a validated instance of the bean class of its own, if the {@link #validate} method has passed.
 * You may use {@link #getValidBean} method to get this bean in your servlet which response the request of respective
 * HTML form.
 *
 */
public abstract class WebBeanValidator extends AbstractBeanValidator {

    //Properties
    /**
     * Process the validation with {@link #validateCreate} method.
     */
    public static final int MODE_CREATE = 1;

    /**
     * Process the validation with {@link #validateUpdate} method.
     */
    public static final int MODE_UPDATE = 2;

    /**
     * Process the validation with {@link #validate} method.
     */
    public static final int MODE_DEFAULT = 0;

    protected static String DEFAULT_ENCODING = "UTF-8";
    protected static final int LENGTH_OF_NAME = 255;
    protected static final int LENGTH_OF_PRIMARY_NAME = 63;


    /**
     * The verify code validator to verify the code input
     */
    private VerifyCodeValidator verifyCodeValidator;

    /**
     * The request parameter names which may contain sensitive words
     */
    private Set<String> paramsMaySensitiveSet = new HashSet<String>();

    /**
     * The sensitive words dictionary
     */
    private SensitiveWords sensitiveWords;

    //Constructor

    //Methods
    /**
     * This method is deprecated in this class and should be replaced with {@link #validate(HttpServletRequest, Object)}.
     * @deprecated
     */
    @Deprecated
    public final void validate(Object obj) {

    }

    /**
     * To validate the specified <code>object</code>, which should be the type which this validator {@link #supports}.
     * Implement this method in concrete Validators.
     *
     * @param req HttpServletRequest
     * @param obj The bean to be validated
     * @return the validated bean
     * @throws ValidationException when any of the property is not valid.
     */
    public abstract Object validate(HttpServletRequest req, Object obj) throws ValidationException;

    /**
     * The validation when creating a new bean of type <code>beanClass</code>. Default implementation of this method is
     * the same with {@link #validate(HttpServletRequest, Object)}.
     *
     * @param req The HttpServletRequest
     * @param obj the object to create
     */
    public Object validateCreate(HttpServletRequest req, Object obj) throws ValidationException {
        return validate(req, obj);
    }

    /**
     * The validation when updating a bean of type <code>beanClass</code>. Default implementation of this method is the
     * same with {@link #validate(HttpServletRequest, Object)}.
     *
     * @param req The HttpServletRequest
     * @param obj the object to update
     */
    public Object validateUpdate(HttpServletRequest req, Object obj) throws ValidationException {
        return validate(req, obj);
    }

    /**
     * The main method to do for this web validator. This method will do following task in sequece: <ul> <li>Create an
     * instance of the bean class of this validator. <li>Copy the corresponding parameter to the bean. <li>Validate this
     * bean with corresponding validate method on create, update or others. <li>Set this bean to the attribute of
     * Request with the name formed with {@link #getBeanAttributeName}. </ul>
     *
     * @param req         The HttpServletRequest
     * @param processMode create? update? or others.
     */
    public void process(HttpServletRequest req, int processMode)
            throws IllegalAccessException, InstantiationException, InvocationTargetException, ValidationException {

        if (this.verifyCodeValidator != null && !this.verifyCodeValidator.verify(req)) {
            throw new WebValidationException("prop.vericode");
        }

        //Set the corresponding property from reques parameters to bean.
        Object obj = this.getBeanClass().newInstance();
        setPropertiesFromRequest(obj, req);

        //Validate the object with different validate method.
        switch (processMode) {
            case MODE_CREATE:
                obj = validateCreate(req, obj);
                break;
            case MODE_UPDATE:
                obj = validateUpdate(req, obj);
                break;
            default:
                obj = validate(req, obj);
                break;
        }

        //Set the validated object to request attribute for further use.
        req.setAttribute(getBeanAttributeName(getBeanClass()), obj);
    }

    public VerifyCodeValidator getVerifyCodeValidator() {
        return verifyCodeValidator;
    }

    /**
     * Set the {@code VerifyCodeValidator of this beanValidator}.
     */
    public void setVerifyCodeValidator(VerifyCodeValidator verifyCodeValidator) {
        this.verifyCodeValidator = verifyCodeValidator;
    }

/*
    public String[] getParamsMaySensitive() {
        return paramsMaySensitiveSet.toArray(new String[0]);
    }
*/

    /**
     * Set an array of parameter names, in whose value sensitive words may be found.
     *
     * @param paramsMaySensitive a list of parameter names, splitted with comma ','.
     */
    public void setParamsMaySensitive(String paramsMaySensitive) {

        // trim all blanks and split them into an array
        String[] sensitiveParamsArray = paramsMaySensitive.replaceAll(" ", "").split(",");

        // build a new list and set them in
        this.paramsMaySensitiveSet = new HashSet<String>(sensitiveParamsArray.length);

        for (String paramName : sensitiveParamsArray) {
            this.paramsMaySensitiveSet.add(paramName);
        }
    }

    /**
     * Set the sensitive words dictionary to this validator.
     */
    public void setSensitiveWords(SensitiveWords sensitiveWords) {
        this.sensitiveWords = sensitiveWords;
    }

    /**
     * Set bean <code>obj</code>'s properties with parameters in {@link HttpServletRequest}.
     *
     * @param obj the object which need to set properties.
     * @param req the value to look from.
     * @return properties assigned bean
     */
    public Object setPropertiesFromRequest(Object obj, HttpServletRequest req)
            throws IllegalAccessException, InvocationTargetException {

        //Build a container for all Request parameters.
        Map<String, String[]> properties = new HashMap<String, String[]>();

        //Iterate Request's parameters and copy them to the map;
        Enumeration<?> reqNames = req.getParameterNames();
        while (reqNames.hasMoreElements()) {
            String name = (String) reqNames.nextElement();
            String[] value = req.getParameterValues(name);

            if (this.sensitiveWords != null && this.paramsMaySensitiveSet.contains(name)) {
                this.sensitiveWords.checkValid(value);
            }

            properties.put(name, value);
        }

        BeanUtils.populate(obj, properties);

        return obj;
    }

    /**
     * Get the validated bean of class <code>beanClass</code> from HttpServletRequest's attribute.
     *
     * @param req       HttpServletRequest
     * @param beanClass the class type of the been to look for
     * @return the corresponding bean
     * @throws NullPointerException When not bean of proper Class can be found. This mostly caused when no corresponding
     *                              Validator has been invoked.
     */
    public static Object getValidBean(HttpServletRequest req, Class<?> beanClass) {
    	
        Object obj = req.getAttribute(getBeanAttributeName(beanClass));

        if (obj == null) {
            throw new NullPointerException("No valid bean of " + beanClass.getName() +
                    " found in Request's attribute! "
                    + "Please make sure that there's at least one 'validator' hidden input tag in the form!");
        }
        return obj;
    }

    /**
     * Get the attribute name in request of the bean object in class of <code>beanClass</code>.
     * <p/>
     * For example:
     * <pre>
     * getBeanAttributeName(com.konlink.ewwweb.entity.User.class);
     * <p/>
     * //will get you string "page-bean-validated-User"
     * </pre>
     *
     * @return validated-[Last Class Name in full class path]
     */
    public static String getBeanAttributeName(Class<?> beanClass) {

        String[] parts = beanClass.getName().split("\\.");

        return "page-bean-validated-" + parts[parts.length - 1];
    }

}
