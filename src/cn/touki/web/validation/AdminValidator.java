package cn.touki.web.validation;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import cn.touki.util.StringUtils;
import cn.touki.validation.StringValidateUtil;
import cn.touki.validation.ValidationException;
import cn.touki.web.core.validation.WebBeanValidator;
import cn.touki.web.core.validation.WebValidationException;
import cn.touki.web.entity.csadmin.Admin;
import cn.touki.web.exception.StringTooLongException;
import cn.touki.web.exception.WebException;

/**
 * Validator for admin.
 *
 * @author Liyi
 */

public class AdminValidator extends WebBeanValidator {

    //Properties

    //Constructor
    public AdminValidator() {
    }
    
    //Methods
    @Override
    public Object validateCreate(HttpServletRequest req, Object obj) throws ValidationException {
        Admin admin = (Admin) obj;

        if (admin.getPassword() == null || admin.getPassword().length() < 6) {
        	throw new WebValidationException("prop.password");
        }

        return validate(req, obj);
    }

    @Override
    public Object validateUpdate(HttpServletRequest req, Object obj) throws ValidationException {
        Admin admin = (Admin) obj;

        if (admin.getPassword() == null || admin.getPassword().length() > 0 && admin.getPassword().length() < 6) {
            throw new WebValidationException("prop.password");
        }

        return validate(req, obj);
    }

    @Override
    public Object validate(HttpServletRequest req, Object obj) throws ValidationException {
        Admin admin = (Admin) obj;

        // Check for password match
        String pwdCfm = req.getParameter("pwdCfm");
        if (pwdCfm == null || !pwdCfm.equals(admin.getPassword())) {
            throw new WebValidationException(new WebException("exception.password.unmatch"));
        }

        // Check for name
        if (admin.getName() == null || !StringValidateUtil.isQualifiedName(admin.getName(), DEFAULT_ENCODING)) {
            throw new WebValidationException("prop.name", admin.getName());
        }

        try {
            if (admin.getName().getBytes(DEFAULT_ENCODING).length > LENGTH_OF_PRIMARY_NAME) {
                throw new StringTooLongException("prop.name", LENGTH_OF_PRIMARY_NAME);
            }

            if (admin.getTrueName().getBytes(DEFAULT_ENCODING).length > LENGTH_OF_PRIMARY_NAME) {
                throw new StringTooLongException("prop.user.realname", LENGTH_OF_PRIMARY_NAME);
            }
        }
        catch (UnsupportedEncodingException e) {
            throw new WebValidationException(e);
        }
        catch (StringTooLongException e) {
            throw new WebValidationException(e);
        }

        // Check for phone
        if (!StringUtils.isEmpty(admin.getPhone()) && !StringValidateUtil.isValidPhone(admin.getPhone())) {
            throw new WebValidationException("prop.phone", admin.getPhone());
        }

        // Check for mobile phone
        if (!StringUtils.isEmpty(admin.getMobile()) && !StringValidateUtil.isValidChineseMobile(admin.getMobile())) {
            throw new WebValidationException("prop.phone", admin.getMobile());
        }

        // Check for email
        if (!StringUtils.isEmpty(admin.getEmail()) && !StringValidateUtil.isValidEmail(admin.getEmail())) {
            throw new WebValidationException("prop.email", admin.getEmail());
        }

        return admin;
    }
}
