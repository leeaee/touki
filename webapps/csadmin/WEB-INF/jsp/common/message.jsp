<%@ page import="
	java.util.Locale,
	cn.touki.web.core.servlet.Constants,
	cn.touki.i18n.I18NMessage"
	buffer="64kb"
	pageEncoding="UTF-8"
	isErrorPage="true"
%>

<%@ taglib prefix="web" uri="/WEB-INF/tld/web.tld" %>

<%@ include file="../inc/header.simple.inc.jsp" %>

<div id="container">

<div class="ui-overlay"><div class="ui-widget-overlay"></div>
	<div id="center-div" class="ui-widget-shadow ui-corner-all" style="width:400px; height:220px; margin:-158px -208px;"></div>
</div>

<div id="center-div" class="ui-widget ui-widget-content ui-corner-all" style="width:398px; height:218px; margin:-150px -200px;">
	<div class="ui-widget-header ui-corner-all">
		<span id="login-title" class="ui-dialog-title"><fmt:message key="term.userlogin" /></span>
	</div>
<web:message message="<%=(I18NMessage)(request.getAttribute(Constants.MESSAGE)) %>" />
</div>

</div>

<%@ include file="../inc/footer.inc.jsp" %>