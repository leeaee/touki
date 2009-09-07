package cn.touki.i18n.resource;

import java.io.InputStream;
import java.io.Serializable;

import cn.touki.i18n.XmlResourceBundle;

/**
 * the implementation of XmlResourceBundle for zh_TW Language.
 *
 */
public class LangResource_zh_TW extends XmlResourceBundle implements Serializable {

    //Properties
	private static final long serialVersionUID = 1L;

	//Constructor
    public LangResource_zh_TW() {
    }

    @Override
    protected InputStream getXmlResource() {
        return LangResource_zh_TW.class.getResourceAsStream("LangResource_zh_TW.xml");
    }
    
    //Methods

}
