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
		<jstl:when test="${_command == 'show'}">
			<h2><acme:message code="lecturer.courseOfLecture.form.label.lecture.delete"/></h2>
			<acme:input-textbox code="lecturer.courseOfLecture.form.label.lecture.title" path="lecture" readonly="true"/>
			<h2><acme:message code="lecturer.courseOfLecture.form.label.course.delete"/></h2>
			<acme:input-textbox code="lecturer.courseOfLecture.form.label.course.title" path="course" readonly="true"/>
			<acme:submit code="lecturer.courseOfLecture.button.delete" action="/lecturer/course-of-lecture/delete"/>
			
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:input-textbox code="lecturer.courseOfLecture.form.label.course.title" path="courseTitle" readonly="true"/>
			<acme:input-select code="lecturer.courseOfLecture.form.label.lectures" path="lecture" choices="${lectures}"/>
	
			<acme:submit code="lecturer.courseOfLecture.form.button.create" action="/lecturer/course-of-lecture/create"/>
		</jstl:when>


	</jstl:choose>



	
	
	
	
	
	

</acme:form>