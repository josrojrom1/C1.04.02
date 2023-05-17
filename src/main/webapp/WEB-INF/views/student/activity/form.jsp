<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.activity.form.label.title" path="title"/>
	<acme:input-textbox code="student.activity.form.label.abst" path="abst"/>
	<acme:input-select code="student.activity.form.label.activityType" path="activityType" choices="${lessonChoices}"/>
	<acme:input-moment code="student.activity.form.label.startTimePeriod" path="startTimePeriod"/>
	<acme:input-moment code="student.activity.form.label.endTimePeriod" path="endTimePeriod"/>
	<acme:input-url code="student.activity.form.label.link" path="link" />
	<acme:input-select code="student.activity.form.label.enrolment" path="enrolment" choices="${choices}" readonly="${readEnrolment}"/>
	
	<jstl:if test="${acme:anyOf(_command, 'show|delete|update') && isFinalised}">
		<acme:submit code="student.activity.form.button.update" action="/student/activity/update"/>
		<acme:submit code="student.activity.form.button.delete" action="/student/activity/delete"/>
	</jstl:if>
	<jstl:if test="${acme:anyOf(_command, 'create')}">
		<acme:submit code="student.activity.form.button.create" action="/student/activity/create"/>
	</jstl:if>
</acme:form>