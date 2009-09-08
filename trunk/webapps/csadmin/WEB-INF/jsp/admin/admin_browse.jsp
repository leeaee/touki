<%@ page language="java" buffer="64kb" pageEncoding="UTF-8" errorPage="../exception.jsp" %>
<%@ taglib prefix="display" uri="/WEB-INF/tld/displaytag.tld" %>
<%@ include file="../inc/header.inc.jsp" %>
<%@ include file="../inc/sider.inc.jsp" %>

<div id="container">
	
<form id="listForm" name="adminListForm" action="./admin" method="get">

	<input type="hidden" name="method" value="" />
	<input type="hidden" name="page.sortStyle" id="sortStyle" value="${param['page.sortStyle']}"/>	
		
	<div id="upper">
		<div id="upper-left"><fmt:message key="act.search" /></div>
		<div id="upper-main"><table width="100%" class="btnbar">
			<tr>
				<td width="100%"></td>
				<td><nobr>
					<input type="button" id="query" value="<fmt:message key="act.search" />" class="bttn" onclick="submitForm(this.form, 'browse')" />			
					<input type="button" id="query" value="<fmt:message key="act.create" />" class="bttn" onclick="submitForm(this.form, 'create')" />			
					<input type="button" id="edit" value="<fmt:message key="act.edit" />" class="sbttn" onclick="submitForm(this.form, 'update')" disabled="disabled" />
					<input type="button" id="delete" value="<fmt:message key="act.delete" />" class="mbttn" onclick="confirmSubmit(this.form, 'delete', '<fmt:message key="script.admin.del" />')" disabled="disabled" />
				</nobr></td>
			</tr>
		</table></div>
	</div>

	<div id="mainer">
		<display:table name="${page.result}" uid="admin" class="data" sort="page" requestURI="./admin">
			<display:column headerStyle="width:32px" title="${htmlHeaderCheck}" class="left" headerClass="left">
				<input type="checkbox" id="chk_${admin_rowNum}" name="chk_${admin_rowNum}" value="${admin.id}" class="ckbox" />
			</display:column>
			<display:column titleKey="prop.adminId" property="name" sortable="true" sortProperty="name" />
			<display:column titleKey="prop.truename" property="trueName" sortable="true" sortProperty="trueName" />
			<display:column titleKey="prop.phone" property="phone" sortable="true" sortProperty="phone" />
			<display:column titleKey="prop.mobile" property="mobile" sortable="true" sortProperty="mobile" />
			<display:column titleKey="prop.email" property="email" sortable="true" sortProperty="email" />
			<display:column titleKey="prop.state" sortable="true" sortProperty="state">${f:state(admin.state, userLocale)}</display:column>
			<display:column titleKey="prop.adminRole" property="roleNames" sortable="false" />
			<display:column titleKey="prop.lastlogintime" property="lastLogin" sortable="true" sortProperty="lastLogin" decorator="dateDecorator" class="right" headerClass="right" />
		</display:table>
	</div>
	
	<div id="search">
		<table class="datanav">
			<tr>
				<th><fmt:message key="term.searchform" /></th>
			</tr>			
			<tr><td>
				<div class="label"><fmt:message key="prop.adminId"/></div>
				<input type="text" name="filter_LIKE_name" size="28" maxlength="127" class="para" value="${param['filter_LIKE_name']}" />
			</td></tr>
			<tr><td>
				<div class="label"><fmt:message key="prop.truename"/></div>
				<input type="text" name="filter_LIKE_trueName" size="28" maxlength="127" class="para" value="${param['filter_LIKE_trueName']}" />
			</td></tr>
			<tr><td>
				<div class="label"><fmt:message key="prop.phone"/></div>
				<input type="text" name="filter_LIKE_phone" size="28" maxlength="127" class="para" value="${param['filter_LIKE_phone']}" />
			</td></tr>
			<tr><td>
				<div class="label"><fmt:message key="prop.mobile"/></div>
				<input type="text" name="filter_LIKE_mobile" size="28" maxlength="127" class="para" value="${param['filter_LIKE_mobile']}" />
			</td></tr>
			<tr><td>
				<div class="label"><fmt:message key="prop.email"/></div>
				<input type="text" name="filters_LIKE_email" size="28" maxlength="127" class="para" value="${param['filter_LIKE_email']}" />
			</td></tr>
			<tr><td>
				<div class="label"><fmt:message key="prop.state"/></div>
				<html:select name="filter_EQ_state" options="<%=Stateful.TEXT%>" style="para" selected="${param['filter_EQ_state']}" hasNaOption="false" locale="${userLocale}" />
			</td></tr>
		</table>
	</div>
	
	<div id="downer"><html:navigator name="downerNav" pageInfo="${page}" style="btnbar" locale="${userLocale}" /></div>
	
</form>

<div id="dialog" title="<fmt:message key="term.detailinfo" />" class="dialog">
</div>

</div>

<script type="text/javascript">

	$(function() {
		initDataTable('detail', '<fmt:message key="act.ok" />');
	});
	
</script>

<%@ include file="../inc/footer.inc.jsp" %>
