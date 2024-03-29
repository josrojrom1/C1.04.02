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

	<jstl:choose>	 
	
		<jstl:when test="${acme:anyOf(_command, 'show|delete|publish') && draftMode== true}">
			<acme:input-textbox code="lecturer.lecture.form.label.title" path="title"/>
			<acme:input-textarea code="lecturer.lecture.form.label.abst" path="abst"/>
			<acme:input-integer code="lecturer.lecture.form.label.learningTime" path="learningTime"/>
			<acme:input-textarea code="lecturer.lecture.form.label.body" path="body"/>
			<acme:input-select code="lecturer.lecture.form.label.lectureType"  path="lectureType"  choices="${lectureTypes}"/>
			<acme:input-url code="lecturer.lecture.form.label.link" path="link"/>
		
			<acme:submit code="lecturer.lecture.form.button.update" action="/lecturer/lecture/update?id=${id}"/>
			<acme:submit code="lecturer.lecture.form.button.publish" action="/lecturer/lecture/publish"/>
			<acme:submit code="lecturer.lecture.form.button.delete" action="/lecturer/lecture/delete"/>
			
		</jstl:when>	
		
		<jstl:when test="${_command == 'show' && draftMode== false}">
			<acme:input-textbox code="lecturer.lecture.form.label.title" path="title" readonly="True"/>
			<acme:input-textarea code="lecturer.lecture.form.label.abst" path="abst" readonly="True"/>
			<acme:input-integer code="lecturer.lecture.form.label.learningTime" path="learningTime" readonly="True"/>
			<acme:input-textarea code="lecturer.lecture.form.label.body" path="body" readonly="True"/>
			<acme:input-select code="lecturer.lecture.form.label.lectureType"  path="lectureType"  choices="${lectureTypes}" readonly="True"/>
			<acme:input-url code="lecturer.lecture.form.label.link" path="link" readonly="True"/>		
		</jstl:when>

		<jstl:when test="${_command == 'create'}">
			<acme:input-textbox code="lecturer.lecture.form.label.title" path="title"/>
			<acme:input-textarea code="lecturer.lecture.form.label.abst" path="abst"/>
			<acme:input-integer code="lecturer.lecture.form.label.learningTime" path="learningTime"/>
			<acme:input-textarea code="lecturer.lecture.form.label.body" path="body"/>
			<acme:input-select code="lecturer.lecture.form.label.lectureType"  path="lectureType"  choices="${lectureTypes}"/>
			<acme:input-url code="lecturer.lecture.form.label.link" path="link"/>
		
			<acme:submit code="lecturer.lecture.form.button.create" action="/lecturer/lecture/create"/>
		</jstl:when>
		
		<jstl:when test="${_command == 'update'}">
			<acme:input-textbox code="lecturer.lecture.form.label.title" path="title"/>
			<acme:input-textarea code="lecturer.lecture.form.label.abst" path="abst"/>
			<acme:input-integer code="lecturer.lecture.form.label.learningTime" path="learningTime"/>
			<acme:input-textarea code="lecturer.lecture.form.label.body" path="body"/>
			<acme:input-select code="lecturer.lecture.form.label.lectureType"  path="lectureType"  choices="${lectureTypes}"/>
			<acme:input-url code="lecturer.lecture.form.label.link" path="link"/>
		
			<acme:submit code="lecturer.lecture.form.button.update" action="/lecturer/lecture/update"/>
		</jstl:when>

			
		
	</jstl:choose>
</acme:form>
