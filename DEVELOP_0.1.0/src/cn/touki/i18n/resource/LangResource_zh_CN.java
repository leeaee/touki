package cn.touki.i18n.resource;

import java.io.InputStream;
import java.io.Serializable;

import cn.touki.i18n.XmlResourceBundle;

/**
 * the implementation of XmlResourceBundle for zh_CN Language.
 *
 */
public class LangResource_zh_CN extends XmlResourceBundle implements Serializable {

    //Properties
	private static final long serialVersionUID = 3546916745159526873L;

	//Constructor
    public LangResource_zh_CN() {
    }

    //Methods
    @Override
    protected InputStream getXmlResource() {
        return LangResource_zh_CN.class.getResourceAsStream("LangResource_zh_CN.xml");
    }

}
