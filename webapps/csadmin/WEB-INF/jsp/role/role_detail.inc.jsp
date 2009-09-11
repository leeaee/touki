<%@ include file="../inc/lib.inc.jsp" %>

<table class="detail">
	<tr>
		<td class="f" colspan="2"></td>
	</tr>
    <tr>
        <td class="keyf"><fmt:message key="prop.name"/></td>
        <td class="v">${role.name}</td>
    </tr>
    <tr>
        <td class="keyf"><fmt:message key="prop.description"/></td>
        <td class="v">${role.description}</td>
    </tr>
    <tr>
        <td class="keyf"><fmt:message key="prop.createtime"/></td>
        <td class="v">${f:time(role.createTime)}</td>
    </tr>
    <tr>
        <td class="keyf"><fmt:message key="prop.modifytime"/></td>
        <td class="v">${f:time(role.lastModify)}</td>
    </tr>
	<tr>			
		<td class="f" colspan="2"></td>
	</tr>
</table>
