<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.activity.show.label.title" path="title"/>
	<acme:input-textbox code="student.activity.show.label.abst" path="abst"/>
	<acme:input-select code="student.activity.show.label.activityType" path="activityType" choices="${choices}"/>
	<acme:input-moment code="student.activity.show.label.startTimePeriod" path="startTimePeriod"/>
	<acme:input-moment code="student.activity.show.label.endTimePeriod" path="startTimePeriod"/>
	<acme:input-integer code="student.activity.show.label.masterId" path="masterId"/>
	<acme:input-url code="student.activity.show.label.link" path="link" />
	
	<jstl:if test="${acme:anyOf(_command, 'show|delete|update|finalise')}">
		<acme:submit code="student.activity.form.button.update" action="/student/activity/update"/>
		<acme:submit code="student.activity.form.button.delete" action="/student/activity/delete"/>
	
	</jstl:if>
	<jstl:if test="${acme:anyOf(_command, 'create')}">
		<acme:submit code="student.activity.form.button.create" action="/student/activity/create"/>
	</jstl:if>
</acme:form>