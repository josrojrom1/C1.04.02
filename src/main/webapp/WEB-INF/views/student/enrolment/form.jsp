<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.enrolment.show.label.code" path="code"/>
	<acme:input-textbox code="student.enrolment.show.label.motivation" path="motivation"/>
	<acme:input-textbox code="student.enrolment.show.label.goals" path="goals"/>
	<acme:input-integer code="student.enrolment.show.label.workTime" path="workTime"/>
	<acme:input-textbox code="student.enrolment.show.label.creditCardHolder" path="creditCardHolder"/>
	<acme:input-textbox code="student.enrolment.show.label.lowerNibble" path="lowerNibble"/>
	<acme:input-select code="student.enrolment.show.label.course" path="course" choices="${courseChoices}"/>
	
	<jstl:if test="${acme:anyOf(_command, 'show|delete|update|finalise') && !isFinalised}">
		<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
		<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
		<acme:submit code="student.enrolment.form.button.finalise" action="/student/enrolment/finalise"/>
	</jstl:if>
	<jstl:if test="${acme:anyOf(_command, 'create')}">
		<acme:submit code="student.enrolment.form.button.create" action="/student/enrolemt/create"/>
	</jstl:if>
</acme:form>