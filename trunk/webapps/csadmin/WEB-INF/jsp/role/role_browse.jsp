<%@ page language="java" buffer="64kb" pageEncoding="UTF-8" errorPage="../exception.jsp" %>
<%@ taglib prefix="display" uri="/WEB-INF/tld/displaytag.tld" %>
<%@ include file="../inc/header.inc.jsp" %>
<%@ include file="../inc/sider.inc.jsp" %>

<div id="container">
<form id="listForm" name="roleListForm" action="./role" method="get">

	<input type="hidden" name="method" value="" />
	<input type="hidden" name="page.sortStyle" id="sortStyle" value="${param['page.sortStyle']}"/>
	
	<div id="upper">
		<div id="upper-left"></div>
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
		<display:table name="${page.result}" uid="role" class="data" sort="page" requestURI="./role">
			<display:column headerStyle="width:32px" title="${htmlHeaderCheck}" class="left" headerClass="left">
				<input type="checkbox" id="chk_${role_rowNum}" name="chk_${role_rowNum}" value="${role.id}" class="ckbox" />
			</display:column>
			<display:column headerStyle="width: 20%" titleKey="prop.name" property="name" sortable="true" sortProperty="name" />
			<display:column titleKey="prop.description" property="description" sortable="true" sortProperty="description" />
			<display:column headerStyle="width: 20%" titleKey="prop.createtime" property="createTime" sortable="true" sortProperty="createTime" decorator="dateDecorator" class="right" headerClass="right" />
			<display:column headerStyle="width: 20%" titleKey="prop.modifytime" property="lastModify" sortable="true" sortProperty="lastModify" decorator="dateDecorator" class="right" headerClass="right" />
		</display:table>
	</div>
	
	<div id="search">
		<table class="datanav">
			<tr>
				<th><fmt:message key="term.searchform" /></th>
			</tr>			
			<tr><td>
				<div class="label"><fmt:message key="prop.name"/></div>
				<input type="text" name="filter_LIKE_name" size="28" maxlength="127" class="para" value="${param['filter_LIKE_name']}" />
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
		initDataTable('<fmt:message key="act.ok" />');
	});
	
</script>

<%@ include file="../inc/footer.inc.jsp" %>
