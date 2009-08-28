<%@ page import="
	java.util.Locale,
	cn.touki.web.core.servlet.Constants,
	cn.touki.i18n.I18NMessage"
	buffer="64kb"
	pageEncoding="UTF-8"
	isErrorPage="true"
%>

<%@ taglib prefix="web" uri="/WEB-INF/tld/web.tld" %>

<%@ include file="inc/header.simple.inc.jsp" %>

<table id="btnTable" width="100%" height="30" cellpadding="0" cellspacing="0" border="0" class="btnbar">
	<tr>
		<td width="7"><IMG SRC="imgs/padding.gif" WIDTH="7" HEIGHT="7" BORDER="0" ALT=""></td>

		<td width="100%"></td>

		<td width="7"><IMG SRC="imgs/padding.gif" WIDTH="7" HEIGHT="7" BORDER="0" ALT=""></td>
	</tr>
</table>

<table id="mainTable" bgColor="#FFFFFF" Style="width: 100%; height: 400px; border-top: 1 solid #999999; ">
    <tr>
		<td width="100%" valign="top" align="center">
            <web:message message="<%=(I18NMessage)(request.getAttribute(Constants.MESSAGE)) %>" />
        </td>
    </tr>
</table>

<%@ include file="/inc/footer.inc.jsp" %>