package cn.touki.i18n.resource;

import java.io.InputStream;
import java.io.Serializable;

import cn.touki.i18n.XmlResourceBundle;

/**
 * the implementation of XmlResourceBundle for ja Language.
 *
 */
public class LangResource_ja extends XmlResourceBundle implements Serializable {

    //Properties
	private static final long serialVersionUID = 1L;

	//Constructor
    public LangResource_ja() {
    }

    @Override
    protected InputStream getXmlResource() {
        return LangResource_ja.class.getResourceAsStream("LangResource_ja.xml");
    }
    
    //Methods

}
