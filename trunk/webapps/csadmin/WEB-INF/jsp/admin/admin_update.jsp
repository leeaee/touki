<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/header.inc.jsp" %>
<%@ include file="../inc/sider.inc.jsp" %>

<div id="container">
<form id="adminUpdateForm" name="adminUpdateForm" action="./admin" method="post">

	<input type="hidden" name="validator" value="adminValidator" />

	<input type="hidden" name="method" value="doUpdate" />
	<input type="hidden" name="id" value="${admin.id}" />
	<input type="hidden" name="lastLogin" value="${admin.lastLogin}" />
	<input type="hidden" name="createTime" value="${admin.createTime}" />
	<input type="hidden" name="lastModify" value="${admin.lastModify}" />

	<div id="upper">
		<div id="upper-left"><fmt:message key="act.search" /></div>
		<div id="upper-main"><table width="100%" class="btnbar">
			<tr>
				<td width="100%"></td>
				<td><nobr>
					<input type="submit" value="<fmt:message key="act.save" />" class="bttn" />
					<input type="reset" value="<fmt:message key="act.reset" />" class="bttn" />
					<input type="button" value="<fmt:message key="act.back" />" class="bttn" onclick="location.href = './admin'" />
				</nobr></td>
			</tr>
		</table></div>
	</div>

	<div id="mainer">
	<table class="form">
		<tr>
			<td class="f" colspan="4"></td>
		</tr>
	    <tr>
	        <td class="keyf"><fmt:message key="prop.adminId" /></td>
	        <td class="v"><input type="text" name="name" size="30" maxlength="63" class="text" value="${admin.name}" readonly="readonly" /></td>
	        <td class="df"><fmt:message key="prop.truename" /></td>
	        <td class="v"><input type="text" name="trueName" size="30" maxlength="63" class="text" value="${admin.trueName}" /></td>
	    </tr>
	    <tr>
	        <td class="keyf"><fmt:message key="prop.password" /></td>
	        <td class="v"><input type="password" name="password" size="30" maxlength="20" class="text" /></td>
	        <td class="df"><fmt:message key="prop.pwdcfm" /></td>
	        <td class="v"><input type="password" name="pwdCfm" size="30" maxlength="20" class="text" /></td>
	    </tr>
	    <tr>
	        <td class="df"><fmt:message key="prop.phone" /></td>
	        <td class="v"><input type="text" name="phone" size="30" maxlength="20" class="text" value="${admin.phone}" /></td>
	        <td class="df"><fmt:message key="prop.mobile" /></td>
	        <td class="v"><input type="text" name="mobile" size="30" maxlength="20" class="text" value="${admin.mobile}" /></td>
	    </tr>
	    <tr>
	        <td class="df"><fmt:message key="prop.email" /></td>
	        <td class="v"><input type="text" name="email" size="30" maxlength="20" class="text" value="${admin.email}" /></td>
	        <td class="df"><fmt:message key="prop.state" /></td>
	        <td class="v"><html:select name="state" options="<%=Stateful.TEXT%>" style="combobox" selected="${admin.state}" hasNaOption="false" hasBlankOption="false" locale="${userLocale}" /></td>
	    </tr>
	    <tr>
	        <td class="df"><fmt:message key="entity.role" /></td>
	        <td class="v"><html:combobox name="role" options="${roles}" propValue="id" propText="name" style="combobox" selected="${admin.roleIds}" hasNaOption="false" hasBlankOption="false" locale="${userLocale}" /></td>
	        <td class="df"></td>
	        <td class="v"></td>
	    </tr>	    
		<tr>
			<td class="s" colspan="4"></td>
		</tr>    
	    <tr>
	        <td class="df"><fmt:message key="prop.description" /></td>
	        <td class="v" colspan="3"><textarea name="description">${admin.description}</textarea></td>
	    </tr>
		<tr>
			<td class="f" colspan="4"></td>
		</tr>
	</table>
	</div>

</form>
</div>

<%@ include file="../inc/footer.inc.jsp" %>
