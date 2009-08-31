package cn.touki.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.touki.i18n.util.ResourceBundleEnumeration;

/**
 * The Resource Bundle implemented with XML Properties Files.
 * <p/>
 * 本类用支持 XML 配置文件的Properties实现ResourceBundle，所有本类的实现类，都应该继承 getXmlResource() 方法以提供一个 XML 文件的读
 * 取流。
 *
 */
public abstract class XmlResourceBundle extends ResourceBundle {

    //Properties
    private Map<String, ?> lookup;
    private Properties props;

    private Log logger = LogFactory.getLog(this.getClass());

    //Constructor
    @SuppressWarnings("unchecked")
    public XmlResourceBundle() {
        props = new Properties();
        try {
            props.loadFromXML(getXmlResource());
        }
        catch (IOException e) {
            logger.warn(e);
        }
        lookup = new HashMap(props);
    }

    //Methods
    /**
     * 获取读取XML配置文件的InputStream
     *
     * @return 读取 XML 配置文件的 InputStream
     */
    protected abstract InputStream getXmlResource();

    @Override
    protected Object handleGetObject(String key) {
        if (key == null) {
            throw new NullPointerException();
        }

        return lookup.get(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        ResourceBundle tmpParent = this.parent;
        return new ResourceBundleEnumeration(lookup.keySet(), (tmpParent != null ? tmpParent.getKeys() : null));
    }

}
