<%@ page import="org.springframework.security.ui.AbstractProcessingFilter"%>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter"%>
<%@ page import="org.springframework.security.AuthenticationException"%>
<%@ include file="inc/header.simple.inc.jsp" %>

<div id="container" style="padding: 0; margin: 0;">

<div class="ui-overlay"><div class="ui-widget-overlay"></div>
	<div id="center-div" class="ui-widget-shadow ui-corner-all" style="width:400px; height:250px; margin:-178px -208px;"></div>
</div>

<div id="center-div" class="ui-widget ui-widget-content ui-corner-all" style="width:398px; height:248px; margin:-170px -200px;">
	<div class="ui-widget-header ui-corner-all">
		<span id="login-title" class="ui-dialog-title"><fmt:message key="term.userlogin" /></span>
	</div>
	<form id="loginForm" name="loginForm" action="./j_spring_security_check" method="post">
		<table class="login">
			<tr>
				<td width="55"><label class="caption"><fmt:message key="term.username" /></label></td>
				<td class="left"><input type="text" id="username" name="j_username" class="text" style="font-family:tahoma, arial;" maxlength="63" size="30" value="admin" /></td>
			</tr>
			<tr>
				<td><label class="caption"><fmt:message key="term.password" /></label></td>
				<td class="left"><input type="password" id="password" name="j_password" class="text" style="font-family:tahoma, arial;" maxlength="63" size="30" value="111111" /></td>
			</tr>
			<tr>
				<td><input type="checkbox" name="_spring_security_remember_me" /></td>
				<td class="left">Remember ME</td>
			</tr>
			<tr>
				<td colspan="2" class="bottom">
					<input type="submit" id="submit" class="bttn" value="<fmt:message key="act.login" />" />
					<input type="reset" id="reset" class="bttn" value="<fmt:message key="act.reset" />" />
				</td>
			</tr>                
		</table>
	</form>
</div>

</div>

<%@ include file="inc/footer.inc.jsp" %> 