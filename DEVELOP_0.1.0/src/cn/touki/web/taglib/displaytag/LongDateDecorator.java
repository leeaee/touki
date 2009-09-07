package cn.touki.web.taglib.displaytag;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import cn.touki.util.DateUtils;

/**
 * Decorator to transform timestamp to long date.
 *
 * @author Liyi 
 */
public class LongDateDecorator implements DisplaytagColumnDecorator {

    // Properties

    // Constructor
	public LongDateDecorator() {
	}

    // Methods

    public String decorate(Object o, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
        long timestamp = ((Long) o).longValue();
        if (timestamp == 0 || timestamp == 111111111111L) {
            return "--";
        }
        return DateUtils.longToDateTimeString(timestamp);
    }
}
