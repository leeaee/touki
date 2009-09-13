package cn.touki.web.taglib.html;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.beanutils.PropertyUtils;

import cn.touki.i18n.I18NDictionary;

/**
 * Tag for htm:combobox.
 *
 * @author Liyi
 */
@SuppressWarnings("unchecked")
public class HtmlComboboxTag extends HtmlTag {

	private static final long serialVersionUID = 1L;
	
	//Properties
    private List<Object> options;
	private List selected;
    private int size = 1;
    private String propValue;
    private String propText;
    private boolean hasBlankOption = true;
    private boolean hasNaOption = true;
    private String naOptionText = "option.state.na";

    //Constructor
    public HtmlComboboxTag() {
    }

    //Methods

    public List<Object> getOptions() {
		return options;
	}

	public void setOptions(List<Object> options) {
		this.options = options;
	}

    public List getSelected() {
		return selected;
	}

	public void setSelected(List selected) {
		this.selected = selected;
	}

	public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isHasNaOption() {
        return hasNaOption;
    }

    public boolean isHasBlankOption() {
		return hasBlankOption;
	}

	public void setHasBlankOption(boolean hasBlankOption) {
		this.hasBlankOption = hasBlankOption;
	}

	public void setHasNaOption(boolean hasNaOption) {
        this.hasNaOption = hasNaOption;
    }

    public String getNaOptionText() {
        return naOptionText;
    }

    public void setNaOptionText(String naOptionText) {
        this.naOptionText = naOptionText;
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

	@Override
    public int doStartTag() throws JspException {
    	
        JspWriter out = pageContext.getOut();

        Locale curLocale = getCurrentLocale();

        StringBuffer html = new StringBuffer("<select name=\"" + this.name + "\"");

        //add attribute 'size'
        if (this.size > 0) {
            html.append(" size=\"" + size + "\"");
        }

        //add attribute 'class'
        if (this.style != null && this.style.length() > 0) {
            html.append(" class=\"" + this.style + "\"");
        }

        //add other attributs set by 'decorator'
        if (this.decorate != null && this.decorate.length() > 0) {
            html.append(" " + this.decorate);
        }

        html.append(">\n");
        
        //add blank option
        if (this.hasBlankOption) {
        	html.append("<option value=\"\"></option>\n");
        }
        
        //add n/a option
        if (this.hasNaOption) {
            html.append("<option value=\"-1\">");
            html.append(I18NDictionary.getMessage(this.naOptionText, curLocale));
            html.append("</option>\n");
        }
        
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
                html.append("<option value=\"").append(value).append("\"");
                //check if this is a 'selected' option
                if (this.selected != null && this.selected.size() > 0) {
	                for (int j = 0; j < this.selected.size(); j ++) {
	                	String selectValue = String.valueOf(this.selected.get(j));
		                if (selectValue.equals(value)) {
		                    html.append(" selected");
		                }
	                }
                }
                html.append(">");

                html.append(text);
                html.append("</option>\n");
            }
        }

        html.append("</select>\n");

        try {
            out.print(html);
        }
        catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }
}
