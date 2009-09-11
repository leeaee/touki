package cn.touki.web.validation;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import cn.touki.validation.StringValidateUtil;
import cn.touki.validation.ValidationException;
import cn.touki.web.core.validation.WebBeanValidator;
import cn.touki.web.core.validation.WebValidationException;
import cn.touki.web.entity.csadmin.Role;
import cn.touki.web.exception.StringTooLongException;

/**
 * Validator for admin.
 *
 * @author Liyi
 */

public class RoleValidator extends WebBeanValidator {

    //Properties

    //Constructor
    public RoleValidator() {
    }
    
    //Methods
    @Override
    public Object validateCreate(HttpServletRequest req, Object obj) throws ValidationException {
    	
        return validate(req, obj);
    }

    @Override
    public Object validateUpdate(HttpServletRequest req, Object obj) throws ValidationException {
    	
        return validate(req, obj);
    }

    @Override
    public Object validate(HttpServletRequest req, Object obj) throws ValidationException {
    	
        Role role = (Role) obj;

        // Check for name
        if (role.getName() == null || !StringValidateUtil.isQualifiedName(role.getName(), DEFAULT_ENCODING)) {
            throw new WebValidationException("prop.name", role.getName());
        }

        try {
            if (role.getName().getBytes(DEFAULT_ENCODING).length > LENGTH_OF_PRIMARY_NAME) {
                throw new StringTooLongException("prop.name", LENGTH_OF_PRIMARY_NAME);
            }
        }
        catch (UnsupportedEncodingException e) {
            throw new WebValidationException(e);
        }
        catch (StringTooLongException e) {
            throw new WebValidationException(e);
        }

        return role;
    }
}
