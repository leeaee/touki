package cn.touki.web.view;

import java.util.List;

/**
 * A helper class for modules in webapps.
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revison$
 * @since 2.0
 */
public class WebModule {

    //Properties
    private List<String> names;
    private List<String> nameKeys;
    private List<String> classes;

    //Constructor
    public WebModule() {
    }

    //Methods
    public void setNames(List<String> names) {
        this.names = names;
    }

    public void setNameKeys(List<String> nameKeys) {
        this.nameKeys = nameKeys;
    }

    public String getName(int index) {
        return names.get(index);
    }

    public String getNameKey(int index) {
        return nameKeys.get(index);
    }

    public int getSize() {
        return names.size();
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public String getClassName(int index) {
        return this.classes.get(index);
    }
}
