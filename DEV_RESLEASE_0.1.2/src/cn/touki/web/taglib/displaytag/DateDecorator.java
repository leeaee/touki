package cn.touki.web.taglib.displaytag;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import cn.touki.util.DateUtils;

/**
 * Decorator to transform timestamp to date.
 *
 * @author Liyi 
 */
public class DateDecorator implements DisplaytagColumnDecorator {

    // Properties

    // Constructor
    public DateDecorator() {
    }

    // Methods

    public String decorate(Object o, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
        long timestamp = ((Long) o).longValue();

        return DateUtils.longToDateString(timestamp);
    }
}
