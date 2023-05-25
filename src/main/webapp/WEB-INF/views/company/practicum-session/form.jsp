<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="company.practicumSession.form.label.title" path="title"/>
	<acme:input-textbox code="company.practicumSession.form.label.abst" path="abst"/>
	<acme:input-moment code="company.practicumSession.form.label.timePeriodStart" path="timePeriodStart"/>
	<acme:input-moment code="company.practicumSession.form.label.timePeriodEnd" path="timePeriodEnd"/>
	<acme:input-url code="company.practicumSession.form.label.link" path="link"/>
	<acme:input-select code="company.practicumSession.form.label.practicum" path="practicum" choices="${choices}" readonly="${readPracticum}"/>
	
	<jstl:if test="${acme:anyOf(_command, 'show|delete|update|publish') && draftMode}">
		<acme:submit code="company.practicumSession.form.button.update" action="/company/practicum-session/update"/>
		<acme:submit code="company.practicumSession.form.button.delete" action="/company/practicum-session/delete"/>
	</jstl:if>
	<jstl:if test="${acme:anyOf(_command, 'create')}">
		<acme:submit code="company.practicumSession.form.button.create" action="/company/practicum-session/create?masterId=${id}"/>
	</jstl:if>
	<jstl:if test="${!draftMode && !hasAddendum}">
		<acme:submit code="company.practicumSession.form.button.addendum" action="/company/practicum-session/addendum?masterId=${masterId}"/>
	</jstl:if>
</acme:form>