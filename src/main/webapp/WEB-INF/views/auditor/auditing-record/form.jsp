<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.auditingRecord.form.label.subject" path="subject"/>
	<acme:input-textbox code="auditor.auditingRecord.form.label.conclusion" path="assessment"/>
	<acme:input-textbox code="auditor.auditingRecord.form.label.startPeriod" path="startPeriod"/>
	<acme:input-textbox code="auditor.auditingRecord.form.label.endPeriod" path="endPeriod"/>
	<acme:input-select code="auditor.auditingRecord.form.label.mark" path="mark" choices="${markChoices}"/>
	<acme:input-textbox code="auditor.auditingRecord.form.label.link" path="link"/>
	
	<!--<jstl:if test="${acme:anyOf(_command, 'show') && !draftMode}">
	<acme:input-textbox code="auditor.audit.form.label.mark" path="mark"/>
		<acme:input-textbox code="auditor.audit.form.label.course" path="course"/>
	</jstl:if>
	<jstl:if test="${acme:anyOf(_command, 'create|show') && draftMode}">
		<acme:input-select code="auditor.audit.form.label.course" path="course" choices="${courseChoices}"/>
		<acme:input-select code="auditor.audit.form.label.mark" path="mark" choices="${markChoices}"/>
	</jstl:if>-->
	
	<jstl:if test="${acme:anyOf(_command, 'show|delete|update') && draftMode}">
		<acme:submit code="auditor.auditingRecord.form.button.update" action="/auditor/auditing-record/update"/>
		<acme:submit code="auditor.auditingRecord.form.button.delete" action="/auditor/auditing-record/delete"/>
	</jstl:if>
	<jstl:if test="${acme:anyOf(_command, 'create')}">
		<acme:submit code="auditor.auditingRecord.form.button.create" action="/auditor/auditing-record/create?id=${id}"/>
	</jstl:if>
	<jstl:if test="${acme:anyOf(_command, 'correct')}">
		<acme:input-checkbox code="auditor.auditingRecord.form.label.confirmation" path="confirm"/>
		<acme:submit code="auditor.auditingRecord.form.button.correct" action="/auditor/auditing-record/correct?id=${id}"/>
	</jstl:if>
	
	
</acme:form>
