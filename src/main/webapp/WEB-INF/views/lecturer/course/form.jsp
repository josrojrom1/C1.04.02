<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:form>
	<acme:input-textbox code="lecturer.course.form.label.code" path="code"/>
	<acme:input-textbox code="lecturer.course.form.label.title" path="title"/>
	<acme:input-textarea code="lecturer.course.form.label.abst" path="abst"/>
	<acme:input-textbox code="lecturer.course.form.label.courseType" path="courseType"/>
	<acme:input-money code="lecturer.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-textbox code="lecturer.course.form.label.link" path="link"/>
	
	
	<!-- SELECTS/OPCIONES -->
	<acme:input-select code="lecturer.course.form.label.tutorial" path="tutorial" choices="${tutorials}"/>
	<acme:input-select code="lecturer.course.form.label.audit" path="audit"  choices="${audits}"/>
	<acme:input-select code="lecturer.course.form.label.practicum" path="practicum"  choices="${practicums}"/>

	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="lecturer.course.form.button.update" action="/lecturer/course/update"/>
			<acme:submit code="lecturer.course.form.button.delete" action="/lecturer/course/delete"/>
			<acme:submit code="lecturer.course.form.button.publish" action="/lecturer/course/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.course.form.button.create" action="/lecturer/course/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>