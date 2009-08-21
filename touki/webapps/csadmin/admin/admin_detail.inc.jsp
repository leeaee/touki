<%@ include file="/inc/lib.inc.jsp" %>

<table class="detail">
	<tr>
		<td class="f" colspan="2"></td>
	</tr>
    <tr>
        <td class="keyf"><fmt:message key="prop.admin.id"/></td>
        <td class="v">${admin.adminId}</td>
    </tr>
    <tr>
        <td class="keyf"><fmt:message key="prop.truename"/></td>
        <td class="v">${admin.trueName}</td>
    </tr>
    <tr>
        <td class="keyf"><fmt:message key="prop.phone"/></td>
        <td class="v">${admin.phone}</td>
    </tr>
    <tr>
        <td class="keyf"><fmt:message key="prop.mobile"/></td>
        <td class="v">${admin.mobile}</td>
    </tr>
    <tr>
        <td class="keyf"><fmt:message key="prop.email"/></td>
        <td class="v">${admin.email}</td>
    </tr>
    <tr>
        <td class="keyf"><fmt:message key="prop.state"/></td>
        <td class="v">${f:state(admin.state, userLocale)}</td>
    </tr>
    <tr>
        <td class="keyf"><fmt:message key="prop.lastlogintime"/></td>
        <td class="v">${f:time(admin.lastLogin)}</td>
    </tr>
    <tr>
        <td class="keyf"><fmt:message key="prop.createtime"/></td>
        <td class="v">${f:time(admin.createTime)}</td>
    </tr>
    <tr>
        <td class="keyf"><fmt:message key="prop.modifytime"/></td>
        <td class="v">${f:time(admin.lastModify)}</td>
    </tr>
    <tr>
        <td class="keyf"><fmt:message key="prop.description"/></td>
        <td class="v">${admin.description}</td>
    </tr>
	<tr>			
		<td class="f" colspan="2"></td>
	</tr>
</table>
