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
	<acme:message code="student.dashboard.form.title.general-indicators"/>
</h1>

<table class="table table-sm">


	<tr>
	
		<th><h2><acme:message code="student.dashboard.form.activities-section"/></h2></th>
	</tr>
	
	<tr>
		<th scope="row"><acme:message code="student.dashboard.form.label.total-num-theory-activities"/></th>	
		<td><acme:print value="${totalNumberTheoryWorkbookActivities}"/></td>
	</tr>
		
	<tr>
		<th scope="row"><acme:message code="student.dashboard.form.label.total-num-handson-activities"/></th>
		<td><acme:print value="${totalNumberHandsonWorkbookActivities}"/></td>
	
	</tr>
	
	<!-- LEARNING TIME OF LECTURES (AVG,DEV,MIN,MAX)-->
	<tr>
		<th scope="row"><acme:message code="student.dasboard.form.label.activity-learning-time-average"/></th>
		<td><acme:print value="${activityWorkbookTimeAverage}"/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message code="student.dasboard.form.label.activity-learning-time-deviation"/></th>
		<td><acme:print value="${activityWorkbookTimeDeviation}"/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message code="student.dasboard.form.label.activity-learning-time-maximum"/></th>
		<td><acme:print value="${activityWorkbookTimeMaximum}"/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message code="student.dasboard.form.label.activity-learning-time-minimum"/></th>
		<td><acme:print value="${activityWorkbookTimeMinimum}"/></td>
	</tr>
	
	<tr>
		<th><h2><acme:message code="student.dashboard.form.courses-enrolled-section"/></h2></th>
	</tr>
	
	
	<tr>
		<th scope="row"><acme:message code="student.dasboard.form.label.course-enrolled-time-average"/></th>
		<td><acme:print value="${courseEnrolledTimeAverage}"/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message code="student.dasboard.form.label.course-enrolled-time-deviation"/></th>
		<td><acme:print value="${courseEnrolledTimeDeviation}"/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message code="student.dasboard.form.label.course-enrolled-time-maximum"/></th>
		<td><acme:print value="${courseEnrolledTimeMaximum}"/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message code="student.dasboard.form.label.course-enrolled-time-minimum"/></th>
		<td><acme:print value="${courseEnrolledTimeMinimum}"/></td>
	</tr>
	
	
</table>
	
<acme:return/>
