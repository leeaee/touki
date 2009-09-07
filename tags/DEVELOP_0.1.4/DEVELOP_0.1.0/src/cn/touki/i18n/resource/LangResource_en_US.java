package cn.touki.i18n.resource;

import java.io.InputStream;
import java.io.Serializable;

import cn.touki.i18n.XmlResourceBundle;

/**
 * The Implementation of LangResource.
 *
 */
public class LangResource_en_US extends XmlResourceBundle implements Serializable {

    //Properties
	private static final long serialVersionUID = 1L;

	//Constructor
    public LangResource_en_US() {
    }

    @Override
    protected InputStream getXmlResource() {
        return LangResource_en_US.class.getResourceAsStream("LangResource_en_US.xml");
    }
    
    //Methods

}
