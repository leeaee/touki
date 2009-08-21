<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/inc/header.inc.jsp" %>
<%@ include file="/inc/sider.inc.jsp" %>

<div id="result"></div>
<div id="main-content">
<form id="adminCreateForm" name="adminCreateForm" action="./admin.do" method="post">

<input type="hidden" name="method" value="adminDoCreate" />

<table width="100%" height="28" cellpadding="0" cellspacing="0" border="0" class="btnbar">
	<tr>
		<td width="100%"></td>
		<td><input type="submit" value="<fmt:message key="act.save" />" class="bttn" /></td>
		<td><input type="reset" value="<fmt:message key="act.reset" />" class="bttn" /></td>
		<td><input type="button" value="<fmt:message key="act.back" />" class="bttn" onclick="location.href = './admin.do'" /></td>
	</tr>
</table>

<input type="hidden" name="validator" value="adminValidator" />

<table class="form">
	<tr>
		<td class="f" colspan="4"></td>
	</tr>
    <tr>
        <td class="keyf"><fmt:message key="prop.admin.id" /></td>
        <td class="v"><input type="text" name="adminId" size="30" maxlength="63" class="text" /></td>
        <td class="df"><fmt:message key="prop.truename" /></td>
        <td class="v"><input type="text" name="trueName" size="30" maxlength="63" class="text" /></td>
    </tr>
    <tr>
        <td class="keyf"><fmt:message key="prop.password" /></td>
        <td class="v"><input type="password" name="password" size="30" maxlength="20" class="text" /></td>
        <td class="df"><fmt:message key="prop.pwdcfm" /></td>
        <td class="v"><input type="password" name="pwdCfm" size="30" maxlength="20" class="text" /></td>
    </tr>
    <tr>
        <td class="df"><fmt:message key="prop.phone" /></td>
        <td class="v"><input type="text" name="phone" size="30" maxlength="20" class="text" /></td>
        <td class="df"><fmt:message key="prop.mobile" /></td>
        <td class="v"><input type="text" name="mobile" size="30" maxlength="20" class="text" /></td>
    </tr>
    <tr>
        <td class="df"><fmt:message key="prop.email" /></td>
        <td class="v"><input type="text" name="email" size="30" maxlength="20" class="text" /></td>
        <td class="df"><fmt:message key="prop.state" /></td>
        <td class="v"><html:select name="state" options="<%=Stateful.TEXT%>" style="slct" selected="${admin.state}" hasNaOption="false" hasBlankOption="false" locale="${userLocale}" /></td>
    </tr>
	<tr>
		<td class="s" colspan="4"></td>
	</tr>    
    <tr>
        <td class="df"><fmt:message key="prop.description" /></td>
        <td class="v" colspan="3"><textarea name="description"></textarea></td>
    </tr>
	<tr>
		<td class="f" colspan="4"></td>
	</tr>
</table>

</form>
</div>

<script type="text/javascript">

	$(function() {
        $('#adminCreateForm').ajaxForm(function(msg) { 
            alert("Thank you for your comment!" + msg); 
        });		
	});	

</script>

<%@ include file="/inc/footer.inc.jsp" %>
