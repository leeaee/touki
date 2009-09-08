<%@ page import="
	java.util.Locale,
	cn.touki.web.core.servlet.Constants,
	cn.touki.i18n.I18NException,
	cn.touki.web.exception.WebException"
	buffer="64kb"
	pageEncoding="UTF-8"
	isErrorPage="true"
%>

<%@ taglib prefix="web" uri="/WEB-INF/tld/web.tld" %>

<%@ include file="inc/header.simple.inc.jsp" %>

<div id="container">

<table id="mainTable" width="100%" height="400" bgColor="#FFFFFF" Style="height: 400px; border-top: 1 solid #999999; ">
    <tr>

		<td width="100%" valign="top" align="center">
        <web:exception exception="<%=exception%>" debug="true" />
        </td>
    </tr>
</table>

</div>

<%@ include file="inc/footer.inc.jsp" %>