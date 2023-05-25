<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.course.form.label.code" path="code"/>
	<acme:input-textbox code="student.course.form.label.title" path="title"/>
	<acme:input-textbox code="student.course.form.label.abst" path="abst"/>
	<acme:input-textbox code="student.course.form.label.courseType" path="courseType"/>
	<acme:input-integer code="student.course.form.label.retailPrice" path="retailPrice"/>
	<jstl:if test="${showExchange}">
	<acme:input-integer code="student.course.form.label.moneyExchange" path="moneyExchange"/>
	</jstl:if>
	<acme:input-url code="student.course.form.label.link" path="link" />
	<acme:input-textbox code="student.course.form.label.lecturer" path="lecturer"/>
	<acme:input-textbox code="student.course.form.label.lectures-title" path="title"/>

</acme:form>