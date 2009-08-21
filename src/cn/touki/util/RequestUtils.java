package cn.touki.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.touki.web.core.servlet.Constants;
import cn.touki.web.exception.WebException;

/**
 * It is the utils for http request to get parameter's value.
 * 
 * @author Liyi
 */
public class RequestUtils {

    /**
     * Get current signed-in user.
     * 
     * @param session the session to get user from.
     * @return the user entity if found, null when no user has signed-in.
     */
    public static Object getCurrentLogin(HttpSession session) {
        if (session.getAttribute(Constants.LOGIN_USER) == null) {
            return null;
        }

        return session.getAttribute(Constants.LOGIN_USER);
    }

    /**
     * 从请求的 Request 中解析 id 和 chk_xxx 的值，并构架一个链表返回. <p/>若 id 的值存在，则返回只有一个节点的链表，值为该 id 的值，否则链表为所有以 'chk_' 开头的属性的值.
     * 
     * @return List<String>
     */
    public static List<String> getRequestIds(HttpServletRequest req) throws WebException {

        List<String> ids = getRequestListCheckedIds(req);

        if (ids.size() == 0) {
            String id = req.getParameter("id");
            if (id != null && id.length() > 0) {
                ids.add(urlDecode(id));
            }
        }

        if (ids.size() == 0) {
            throw new WebException("exception.id");
        }

        return ids;
    }

    /**
     * 获得正确的参数值.通过表单提交的参数值不进行转码，通过分页标签url提交的参数进行转码（须保证url上的参数值是经过UTF-8编码过）.分页
     * 标签产生的url包含"isNav"参数。
     * @param req
     * @param paramName
     * @return argValue
     * @throws EWWebException
     */
    public static String getParam(HttpServletRequest req, String paramName) throws WebException {
        String argValue = req.getParameter(paramName);
        if (StringUtils.isNotBlank(argValue)) {
            if (req.getParameter("isNav") != null||req.getMethod().equals("GET")) {
                argValue = getRequestId(req, paramName);
            }
        }
        return argValue;
    }
    
    /**
     * 将iso8859的参数进行转码.
     * 
     * @param req
     * @param paramName
     * @return String
     */
    public static String getParamFromISO8859(HttpServletRequest req, String paramName){
        String argValue = req.getParameter(paramName);
        return urlDecode(argValue);
    } 

    /**
     * 返回请求中的 id 值，若 id 不存在，则返回第一个以 chk_ 开头的属性的值.
     * 
     * @return 字符串
     * @exception WebException 找不到合适的 id.
     */
    public static String getRequestId(HttpServletRequest req) throws WebException {
        return getRequestId(req, "id");
    }

    /**
     * 返回请求中指定名称的属性值，若该值不存在，则返回第一个以 chk_ 开头的属性的值.
     * 
     * @return 字符串
     * @exception WebException 找不到合适的指定属性值.
     */
    public static String getRequestId(HttpServletRequest req, String idParamName) throws WebException {
        if (idParamName == null) {
            idParamName = "id";
        }

        String id = req.getParameter(idParamName);
        String rVal = null;
        if (id != null && id.length() > 0) {
            rVal = id;
        } else {
            Enumeration<?> names = req.getParameterNames();
            while (names.hasMoreElements()) {
                String name = (String) names.nextElement();
                if (name.startsWith("chk_")) {
                    rVal = req.getParameter(name);
                    break;
                }
            }

            if (rVal == null) {
                throw new WebException("exception.id");
            }
        }
        if (req.getMethod().equals("POST")&&rVal.indexOf("%")==-1) {
            return rVal;
        }
        return urlDecode(rVal);
    }

    /**
     * 返回 request 中所有以 '{@code chk_}' 开头的属性值.
     * 
     * @param req HttpServletRequest
     * @return 字符串链表
     */
    public static List<String> getRequestListCheckedIds(HttpServletRequest req) {
        List<String> ids = new ArrayList<String>();

        Enumeration<?> names = req.getParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            if (name.startsWith("chk_")) {
                if (req.getMethod().equalsIgnoreCase("GET")||req.getParameter(name).indexOf("%")>-1) {
                    ids.add(urlDecode(req.getParameter(name)));
                    continue;
                }
                if(req.getMethod().equalsIgnoreCase("POST")){
                    ids.add(req.getParameter(name));
                }
            }
        }

        return ids;
    }

    /**
     * 用默认的字符集编码（UTF-8）编码指定的字符串，以便能够正确的作为 GET 参数使用。
     * 
     * @param str 要编码的字符串
     * @return 编码过的字符串
     */
    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, Constants.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    /**
     * 用默认的字符集编码（UTF-8）解码指定的字符串，以便能够得到正确的原始参数.
     * 
     * @param str 要解码的字符串
     * @return 解码过的字符串
     */
    public static String urlDecode(String str) {
        try {
            if (str.indexOf("%") > -1) {
                // The url has not been decoded yet and it should be decoded with project default charset.
                return URLDecoder.decode(str, Constants.DEFAULT_CHARSET);
            } else {// if (str.length() == str.getBytes().length) {
                // Some web container, such as tomcat 5.0.x, will automatically decoded url with charset 'ISO-8859-1',
                // here we need to transform the string to 'UTF-8', the project default charset.
                return new String(str.getBytes("ISO-8859-1"), Constants.DEFAULT_CHARSET);
            }
            /*
             * else { System.out.println("3"); return str; }
             */
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }
}
