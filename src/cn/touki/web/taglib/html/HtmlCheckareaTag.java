package cn.touki.web.taglib.html;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * An abstract Tag class for HtmlRadioTag and HtmlCheckboxTag.
 *
 * @author <A href="mailto:leeaee@gmail.com">Liyi</A>
 * 
 */
@SuppressWarnings("unchecked")
public class HtmlCheckareaTag extends HtmlTag {

	private static final long serialVersionUID = 1L;
	//Properties
	private String id;
	private String areaStyle;
	private List options;
	private List checked;	
	private String propValue;
	private String propText;
    private String connector = "&nbsp;&nbsp;";

    //Constructor
    public HtmlCheckareaTag() {
    }

    //Methods
    public List getOptions() {
        return options;
    }

    public void setOptions(List options) {
        this.options = options;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getPropValue() {
		return propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}

	public String getPropText() {
		return propText;
	}

	public void setPropText(String propText) {
		this.propText = propText;
	}

	public List getChecked() {
		return checked;
	}

	public void setChecked(List checked) {
		this.checked = checked;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaStyle() {
		return areaStyle;
	}

	public void setAreaStyle(String areaStyle) {
		this.areaStyle = areaStyle;
	}

	@Override
    public int doStartTag() throws JspException {
    	
        JspWriter out = pageContext.getOut();

        StringBuffer html = new StringBuffer("<div");
        
        //add attribute 'id'
        if (this.id != null && this.id.length() > 0) {
            html.append(" id=\"" + this.id + "\"");
        }

        //add attribute 'class'
        if (this.areaStyle != null && this.areaStyle.length() > 0) {
            html.append(" class=\"" + this.areaStyle + "\"");
        }

        //add other attributs set by 'decorator'
        if (this.decorate != null && this.decorate.length() > 0) {
            html.append(" " + this.decorate);
        }

        html.append(">\n");
        
        //add options
        if (this.options != null && this.options.size() > 0) {
            for (int i = 0; i < options.size(); i++) {
                Object option = options.get(i);
                String value = "";
                String text = "";
				try {
					value = String.valueOf(PropertyUtils.getProperty(option, propValue));
					text = String.valueOf(PropertyUtils.getProperty(option, propText));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
                html.append("    ");
                html.append("<label><input type=\"checkbox\" name=\"").append(name).append("\"");
                html.append(" value=\"").append(value).append("\"");
                
                //add attribute 'class'
                if (this.style != null && this.style.length() > 0) {
                    html.append(" class=\"" + this.style + "\"");
                }                
                
                //check if this is a 'selected' option
                if (this.checked != null && this.checked.size() > 0) {
	                for (int j = 0; j < this.checked.size(); j ++) {
	                	String selectValue = String.valueOf(this.checked.get(j));
		                if (selectValue.equals(value)) {
		                    html.append(" checked");
		                }
	                }
                }
                html.append(">");
                
                html.append(text);
                html.append("</label><br />\n");
            }
        }

        html.append("</div>\n");

        try {
            out.print(html);
        }
        catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }

}
