<%@ include file="inc/header.simple.inc.jsp" %>

<div class="ui-overlay"><div class="ui-widget-overlay"></div>
	<div id="center-div" class="ui-widget-shadow ui-corner-all" style="width:400px; height:200px; margin:-108px -208px;"></div>
</div>

<div id="center-div" class="ui-widget ui-widget-content ui-corner-all" style="width:398px; height:198px; margin:-100px -200px;">
	<div class="ui-widget-header ui-corner-all">
		<span id="login-title" class="ui-dialog-title"><fmt:message key="term.userlogin" /></span>
	</div>
	<form id="loginForm" name="loginForm" action="./login" method="post">
	<input type="hidden" name="method" value="login" />        
		<table border="0" class="login">
			<tr>
				<td width="55"><label class="caption"><fmt:message key="term.username" /></label></td>
				<td class="left"><input type="text" id="username" name="username" class="text" style="font-family:tahoma, arial;" maxlength="63" size="30" value="admin" /></td>
			</tr>
			<tr>
				<td><label class="caption"><fmt:message key="term.password" /></label></td>
				<td class="left"><input type="password" id="password" name="password" class="text" style="font-family:tahoma, arial;" maxlength="63" size="30" value="111111" /></td>
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

<%@ include file="inc/footer.inc.jsp" %> 