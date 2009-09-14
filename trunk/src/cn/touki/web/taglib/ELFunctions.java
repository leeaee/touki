package cn.touki.web.taglib;

/**
 * EL function for taglib <f:/>
 *
 * @author Liyi
 */
import java.util.Locale;

import cn.touki.i18n.I18NDictionary;
import cn.touki.util.DateUtils;
import cn.touki.web.entity.common.Stateful;

public class ELFunctions {
	
	public static String getStateText(Integer stateIndex, Locale locale) {
		
	    String textColor = "";
	    
	    switch (stateIndex) {
	        case 0:
	            textColor = "green";
	            break;
	        case 1:
	            textColor = "red";
	            break;
	        case 2:
	            textColor = "orange";
	            break;
	        case 3:
	            textColor = "darkgray";
	            break;
	        case 4:
	            textColor = "black";
	            break;
	        case 5:
	            textColor = "gray";
	            break;
	    }
	
	    return "<span class=\"" + textColor + "\">" + I18NDictionary.getMessage(Stateful.TEXT[stateIndex], locale) + "</span>";
	}
	
	public static String long2DateTimeString(Long timestamp) {
		return DateUtils.longToDateTimeString(timestamp);
	}
	
	public static String long2DateString(Long timestamp) {
		return DateUtils.longToDateString(timestamp);
	}
	
	public static String Local2String(Locale locale) {
		return locale.toString();
	}
	
	public static String searchBarDisplay(Boolean hasValue) {
		
		if(hasValue) {
			return "block";
		} else {
			return "none";
		}
	}
	
	public static String searchBttnStyle(Boolean hasValue) {
		
		if(hasValue) {
			return "ui-state-active";
		} else {
			return "ui-state-default";
		}
	}
	
	public static String i18n(String key, Locale locale, String split) {
		
		StringBuffer sb = new StringBuffer();
		
		if (split != null && split.length() > 0) {

			String[] temp = key.split(split);
			
			for (int i = 0; i < temp.length; i ++) {
				sb.append(I18NDictionary.getMessage(temp[i], locale) + split);
			}
		
		}
		return sb.toString();
	}	
	
}
