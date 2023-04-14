<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:form>
	<acme:input-textbox code="any.course.form.label.code" path="code"/>
	<acme:input-textbox code="any.course.form.label.title" path="title"/>
	<acme:input-textarea code="any.course.form.label.abst" path="abst"/>
	<acme:input-textbox code="any.course.form.label.courseType" path="courseType"/>
	<acme:input-textbox code="any.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-textbox code="any.course.form.label.lecturer" path="lecturer"/>
	<acme:input-textbox code="any.course.form.label.tutorial" path="tutorial"/>
	<acme:input-textbox code="any.course.form.label.audit" path="audit"/>
	<acme:input-textbox code="any.course.form.label.practicum" path="practicum"/>
</acme:form>