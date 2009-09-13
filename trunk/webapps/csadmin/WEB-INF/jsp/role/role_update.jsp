<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/header.inc.jsp" %>
<%@ include file="../inc/sider.inc.jsp" %>

<div id="container">
<form id="roleCreateForm" name="roleCreateForm" action="./role" method="post">

	<input type="hidden" name="validator" value="roleValidator" />
	
	<input type="hidden" name="method" value="doUpdate" />
	<input type="hidden" name="id" value="${role.id}" />
	<input type="hidden" name="createTime" value="${role.createTime}" />
	<input type="hidden" name="lastModify" value="${role.lastModify}" />

	<div id="upper">
		<div id="upper-left"><fmt:message key="act.search" /></div>
		<div id="upper-main"><table width="100%" class="btnbar">
			<tr>
				<td width="100%"></td>
				<td><nobr>
					<input type="submit" value="<fmt:message key="act.save" />" class="bttn" />
					<input type="reset" value="<fmt:message key="act.reset" />" class="bttn" />
					<input type="button" value="<fmt:message key="act.back" />" class="bttn" onclick="location.href = './role'" />
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
	        <td class="keyf"><fmt:message key="prop.name" /></td>
	        <td class="v"><input type="text" name="name" size="30" value="${role.name}" maxlength="63" class="text" readonly="readonly" /></td>
	    </tr>
	    <tr>
	        <td class="keyf"><fmt:message key="entity.authority" /></td>
	        <td class="v"><html:checkarea name="authority" options="${authorities}" propValue="id" propText="displayName" checked="${role.authIds}" areaStyle="ckarea" style="multck" locale="${userLocale}" /></td>
	    </tr>
	    <tr>
	        <td class="df"><fmt:message key="prop.description" /></td>
	        <td class="v" colspan="3"><textarea name="description">${role.description}</textarea></td>
	    </tr>
		<tr>
			<td class="f" colspan="4"></td>
		</tr>
	</table>
	</div>
	
</form>
</div>

<%@ include file="../inc/footer.inc.jsp" %>
