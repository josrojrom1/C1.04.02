<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<%--Caso 1, la práctica no está publicada --%>
	<%--Caso 2, la práctica está publicada pero no tiene addendum --%>
	<%--Caso 3, la práctica está publicada y tiene addendum --%>
	<jstl:if test="${acme:anyOf(_command, 'create')}">
		<acme:input-textbox code="company.practicum.show.label.code" path="code"/>
		<acme:input-textbox code="company.practicum.show.label.title" path="title"/>
		<acme:input-textbox code="company.practicum.show.label.abst" path="abst"/>
		<acme:input-textbox code="company.practicum.show.label.goals" path="goals"/>
		<acme:input-select code="company.practicum.show.label.course" path="course" choices="${courseChoices}"/>
			<acme:submit code="company.practicum.form.button.create" action="/company/practicum/create"/>
			<acme:submit code="company.practicum.form.button.create" action="/company/practicum/createAddendum"/>
	</jstl:if>
	
	<jstl:if test="${acme:anyOf(_command, 'show|delete|update|publish') && draftMode}">
		<acme:input-textbox code="company.practicum.show.label.code" path="code"/>
		<acme:input-textbox code="company.practicum.show.label.title" path="title"/>
		<acme:input-textbox code="company.practicum.show.label.abst" path="abst"/>
		<acme:input-textbox code="company.practicum.show.label.goals" path="goals"/>
		<acme:input-textbox code="company.practicum.show.label.totalTime" path="totalTime" readonly="True"/>
		<acme:submit code="company.practicum.form.button.update" action="/company/practicum/update"/>
		<acme:submit code="company.practicum.form.button.delete" action="/company/practicum/delete"/>
		<acme:submit code="company.practicum.form.button.publish" action="/company/practicum/publish"/>
	</jstl:if>
	
	<jstl:if test="${!draftMode && hasAddendum}">
		<acme:input-textbox code="company.practicum.show.label.code" path="code" readonly="True"/>
		<acme:input-textbox code="company.practicum.show.label.title" path="title" readonly="True"/>
		<acme:input-textbox code="company.practicum.show.label.abst" path="abst" readonly="True"/>
		<acme:input-textbox code="company.practicum.show.label.goals" path="goals" readonly="True"/>
		<acme:input-textbox code="company.practicum.show.label.totalTime" path="totalTime" readonly="True"/>
	</jstl:if>
</acme:form>