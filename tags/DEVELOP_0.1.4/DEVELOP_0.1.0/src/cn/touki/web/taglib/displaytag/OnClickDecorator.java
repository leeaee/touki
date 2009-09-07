package cn.touki.web.taglib.displaytag;

import java.text.MessageFormat;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

/**
 * ${Description}
 *
 * @author Liyi 
 */
public class OnClickDecorator implements DisplaytagColumnDecorator {

    //Properties
    private static final String FORMAT = " onClick=\"onRowClick({0});\" style=\"cursor: hand; \"";

    //Constructor
    public OnClickDecorator() {
    }

    //Methods

    public String decorate(Object o, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
        return MessageFormat.format(FORMAT, o.toString());
    }
}
