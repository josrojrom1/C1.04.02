<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:form>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:input-textbox code="lecturer.course.form.label.code" path="code"/>
			<acme:input-textbox code="lecturer.course.form.label.title" path="title"/>
			<acme:input-textarea code="lecturer.course.form.label.abst" path="abst"/>
			<acme:input-money code="lecturer.course.form.label.retailPrice" path="retailPrice"/>
			<acme:input-textbox code="lecturer.course.form.label.link" path="link"/>
			
			<acme:submit code="lecturer.course.form.button.update" action="/lecturer/course/update"/>
			<acme:submit code="lecturer.course.form.button.delete" action="/lecturer/course/delete"/>
			<acme:submit code="lecturer.course.form.button.publish" action="/lecturer/course/publish"/>
		</jstl:when>
		
		<jstl:when test="${acme:anyOf(_command, 'show') && draftMode == false}">
			<acme:input-textbox code="lecturer.course.form.label.code" path="code" readonly = "True"/>
			<acme:input-textbox code="lecturer.course.form.label.title" path="title" readonly = "True"/>
			<acme:input-textarea code="lecturer.course.form.label.abst" path="abst" readonly = "True"/>
			<acme:input-money code="lecturer.course.form.label.retailPrice" path="retailPrice" readonly = "True"/>
			<acme:input-textbox code="lecturer.course.form.label.link" path="link" readonly = "True"/>
			
			<acme:submit code="lecturer.course.form.button.delete" action="/lecturer/course/delete"/>
		</jstl:when>
		
		
		<jstl:when test="${_command == 'create'}">
			<acme:input-textbox code="lecturer.course.form.label.code" path="code"/>
			<acme:input-textbox code="lecturer.course.form.label.title" path="title"/>
			<acme:input-textarea code="lecturer.course.form.label.abst" path="abst"/>
			<acme:input-money code="lecturer.course.form.label.retailPrice" path="retailPrice"/>
			<acme:input-textbox code="lecturer.course.form.label.link" path="link"/>
			
			<acme:submit code="lecturer.course.form.button.create" action="/lecturer/course/create"/>
		</jstl:when>
		
		
	</jstl:choose>
	
	
	
</acme:form>