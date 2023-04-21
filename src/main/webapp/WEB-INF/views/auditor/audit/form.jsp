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
	<acme:input-textbox code="auditor.audit.form.label.code" path="code"/>
	<acme:input-textbox code="auditor.audit.form.label.conclusion" path="conclusion"/>
	<acme:input-textbox code="auditor.audit.form.label.weakPoints" path="weakPoints"/>
	<acme:input-textbox code="auditor.audit.form.label.strongPoints" path="strongPoints"/>
	<acme:input-select code="auditor.audit.form.label.course" path="course" choices="${courseChoices}"/>
	<jstl:if test="${acme:anyOf(_command, 'show')}">
		<acme:input-textbox  code="auditor.audit.form.label.mark" path="mark" readonly = "true"/>
	</jstl:if>
	<!--<jstl:if test="${acme:anyOf(_command, 'show') && !draftMode}">
	<acme:input-textbox code="auditor.audit.form.label.mark" path="mark"/>
		<acme:input-textbox code="auditor.audit.form.label.course" path="course"/>
	</jstl:if>
	<jstl:if test="${acme:anyOf(_command, 'create|show') && draftMode}">
		<acme:input-select code="auditor.audit.form.label.course" path="course" choices="${courseChoices}"/>
		<acme:input-select code="auditor.audit.form.label.mark" path="mark" choices="${markChoices}"/>
	</jstl:if>-->
	
	<jstl:if test="${acme:anyOf(_command, 'show|delete|update|publish') && draftMode}">
		<acme:submit code="auditor.audit.form.button.update" action="/auditor/audit/update"/>
		<acme:submit code="auditor.audit.form.button.delete" action="/auditor/audit/delete"/>
		<acme:submit code="auditor.audit.form.button.publish" action="/auditor/audit/publish"/>
		<acme:button code="auditor.auditingRecord.list.button.createAuditingRecord" action="/auditor/auditing-record/create?id=${id}"/>
		
	</jstl:if>
	<jstl:if test="${_command == 'show' && draftMode == false}">
			<acme:button code="auditor.audit.form.button.addCorrection" action="/auditor/auditing-record/correct?id=${id}"/>
	</jstl:if>
	<jstl:if test="${acme:anyOf(_command, 'create')}">
		<acme:submit code="auditor.audit.form.button.create" action="/auditor/audit/create"/>
	</jstl:if>
	<jstl:if test="${!acme:anyOf(_command, 'create')}">
		<acme:button code="auditor.audit.form.button.auditingRecordList" action="/auditor/auditing-record/list?id=${id}"/>
	</jstl:if>
	
	
</acme:form>
