package cn.touki.i18n.resource;

import java.io.InputStream;
import java.io.Serializable;

import cn.touki.i18n.XmlResourceBundle;

/**
 * An implementation of XmlResourceBundle.
 *
 */
public class LangResource extends XmlResourceBundle implements Serializable {

    //Properties
	private static final long serialVersionUID = 1L;

	//Constructor
    public LangResource() {
    }

    //Methods
    @Override
    protected InputStream getXmlResource() {
        return LangResource.class.getResourceAsStream("LangResource.xml");
    }
}
