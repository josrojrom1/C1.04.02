<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>
	<%--Caso 1, la práctica no está publicada --%>
	<%--Caso 2, la práctica está publicada pero no tiene addendum --%>
	<%--Caso 3, la práctica está publicada y tiene addendum --%>
<acme:form>
	<acme:input-textbox code="company.practicum.show.label.code" path="code"/>
	<acme:input-textbox code="company.practicum.show.label.title" path="title"/>
	<acme:input-textbox code="company.practicum.show.label.abst" path="abst"/>
	<acme:input-textbox code="company.practicum.show.label.goals" path="goals"/>
	<acme:input-double code="company.practicum.show.label.totalTime" path="totalTime"/>
	<acme:input-select code="company.practicum.show.label.course" path="course" choices="${courseChoices}"/>
	
	<jstl:if test="${acme:anyOf(_command, 'show|delete|update|publish' && draftmode)}">
		<acme:submit code="company.practicum.form.button.update" action="/company/practicum/update"/>
		<acme:submit code="company.practicum.form.button.delete" action="/company/practicum/delete"/>
		<acme:submit code="company.practicum.form.button.publish" action="/company/practicum/publish"/>
	</jstl:if>
	<jstl:if test="${acme:anyOf(_command, 'create')}">
		<acme:submit code="company.practicum.form.button.create" action="/company/practicum/create"/>
	</jstl:if>
	<jstl:if test="${acme:anyOf(_command, 'show|delete|update|publish')}">
		<acme:button code="company.practicum.form.button.sessions" action="/company/practicum-session/list?masterId=${id}"/>
	</jstl:if>
</acme:form>