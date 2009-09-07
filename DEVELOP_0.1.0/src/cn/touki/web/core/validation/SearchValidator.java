package cn.touki.web.core.validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.touki.web.exception.WebException;

/**
 * Validator for searching.
 * <p/>
 * To create a SearchValidator is much like to create a {@link WebBeanValidator}, but much easier, you are supposed to
 * make following 3 steps below:
 * <p/>
 * <p/>
 * <H6>Step 1 - Create a {@code SearchValidator} Class</H6>
 * <p/>
 * Implement the {@link #validate} method and throw a {@link WebValidationException} when any bean property is invalid.
 * <p/>
 * The implementation of this method <b>SHOULD</b> put all verified parameters to the {@code searchParams} field, so
 * that {@link #process} method will re-format them and set them into {@code HttpSession} as an Object Array. If you
 * missed or failed to do this, you will get <b>NONE</b> searching parameter in the result page which you deal with the
 * searching.
 * <p/>
 * <H6>Step 2 - Add this as a bean to {@code ewweb_validators.xml} file</H6>
 * <p/>
 * This kind of Validators need no parameters, unlike the {@link WebBeanValidator}. For example: <Pre> &lt;bean
 * id="userSearchValidator" class="com.konlink.ewweb.validation.UserSearchValidator" /&gt; </Pre>
 * <p/>
 * <H6>Step 3 - Add following hidden element in the HTML Form in which you need this validator.</H6>
 * <p/>
 * List all validators you needed if necessary. The value of the validator is the name of this "Validator Bean".
 * <pre>
 *   &lt;form ...&gt;
 *       ...
 *       &lt;input type="hidden" name="validators" value="userSearchValidator" /&gt;
 *       &lt;input type="hidden" name="validators" value="userDetailSearchValidator" /&gt;
 *       ...
 *   &lt;/form&gt;
 *   </pre>
 * <p/>
 * <p/>
 * The validators would be loaded and run in the order as you specified in the HTML form. Each validator will create an
 * Object Array as the searching parameters of its own, if the {@link #validate} method has passed. You may use {@link
 * #getValidatedParams} method to get this array in your servlet/Jsp which response the request of respective HTML
 * searching form.
 *
 */
public abstract class SearchValidator extends WebValidator {

    //Properties
    public static final String KEYNAME_PREFIX = "session-search-key-";
    private String keyName;

    //Constructor

    /**
     * Get a new SearchValidator.
     */
    public SearchValidator() {
        keyName = KEYNAME_PREFIX + this.getClass().getName();
    }

    //Methods

    /**
     * Process this validator. Invoke {@code validate()} method and then fetch all values in the searchParams into an
     * array of Object, which is to be set into session with the keyname {@link #getSessionKeyName}.
     * @throws WebException 
     */
    @Override
    public void process(HttpServletRequest req) throws WebValidationException, WebException {

        Object[] params = validate(req);

        req.getSession().setAttribute(keyName, params);
    }

    /**
     * Validate the parameters in HttpServletRequest. Implementation of this method should put the validated key-value
     * pair to {@code searchParams} with {@code searchParams.put(key, value)} method, which is a protected field.
     *
     * @param req HttpServletRequest
     * @return parsed parameters from request
     */
    @Override
    public abstract Object[] validate(HttpServletRequest req) throws WebValidationException,WebException;

    /**
     * Get the default set of parameters when no search request has been committed.
     *
     * @return the default parameter array.
     */
    public abstract Object[] getDefaultParams();

    /**
     * Get the key name of the searchParams when it is saved to {@code HttpSession}.
     *
     * @return the string of keyname
     */
    public String getSessionKeyName() {
        return keyName;
    }

    /**
     * Get the validated searching parameters from the {@code HttpSession}.
     *
     * @param session HttpSessipn
     * @return the validated searching parameters in an Object array
     */
    public Object[] getValidatedParams(HttpSession session) {
        Object[] params = (Object[]) session.getAttribute(keyName);
        return params == null ? getDefaultParams() : params;
    }
}
