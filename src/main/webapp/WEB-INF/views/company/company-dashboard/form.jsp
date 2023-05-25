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

<h1>
	<acme:message code="lecturer.dashboard.form.title.general-indicators"/>
</h1>

<table class="table table-sm">


	<tr>
		<!-- TOTAL NUM THEORY/HANDS ON LECTURES)-->
		<th><h2><acme:message code="lecturer.dashboard.form.lectures-section"/></h2></th>
	</tr>
	
	<tr>
		<th scope="row"><acme:message code="lecturer.dashboard.form.label.total-num-theory-lectures"/></th>	
		<td><acme:print value="${totalNumOfTheoryLectures}"/></td>
	</tr>
		
	<tr>
		<th scope="row"><acme:message code="lecturer.dashboard.form.label.total-num-handson-lectures"/></th>
		<td><acme:print value="${totalNumOfHandsonLectures}"/></td>
	
	</tr>
	
	<!-- LEARNING TIME OF LECTURES (AVG,DEV,MIN,MAX)-->
	<tr>
		<th scope="row"><acme:message code="lecturer.dasboard.form.label.lecture-learning-time-average"/></th>
		<td><acme:print value="${lectureLearningTimeAverage}"/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message code="lecturer.dasboard.form.label.lecture-learning-time-deviation"/></th>
		<td><acme:print value="${lectureLearningTimeDeviation}"/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message code="lecturer.dasboard.form.label.lecture-learning-time-maximum"/></th>
		<td><acme:print value="${lectureLearningTimeMaximum}"/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message code="lecturer.dasboard.form.label.lecture-learning-time-minimum"/></th>
		<td><acme:print value="${lectureLearningTimeMinimum}"/></td>
	</tr>
	
	<!-- LEARNING TIME OF COURSES (AVG,DEV,MIN,MAX)-->	
	<tr>
		<th><h2><acme:message code="lecturer.dashboard.form.courses-section"/></h2></th>
	</tr>
	
	
	<tr>
		<th scope="row"><acme:message code="lecturer.dasboard.form.label.course-learning-time-average"/></th>
		<td><acme:print value="${courseLearningTimeAverage}"/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message code="lecturer.dasboard.form.label.course-learning-time-deviation"/></th>
		<td><acme:print value="${courseLearningTimeDeviation}"/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message code="lecturer.dasboard.form.label.course-learning-time-maximum"/></th>
		<td><acme:print value="${courseLearningTimeMaximum}"/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message code="lecturer.dasboard.form.label.course-learning-time-minimum"/></th>
		<td><acme:print value="${courseLearningTimeMinimum}"/></td>
	</tr>
	
	
</table>
	
<acme:return/>

