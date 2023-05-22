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

<h2>
	<acme:message code="auditor.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.total-num-theory-audits"/>
		</th>
		<td>
			<acme:print value="${totalNumOfTheoryAudits}"/>
		</td>
		
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.total-num-handson-audits"/>
		</th>
		<td>
			<acme:print value="${totalNumOfHandsonAudits}"/>
		</td>
	</tr>
	
	<!-- LEARNING TIME OF LECTURES (AVG,DEV,MIN,MAX)-->
	
	
	
	
	
	
	
	
	
	
	
	<!-- 
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.average-number-applications-worker"/>
		</th>
		<td>
			<acme:print value="${averageNumberOfApplicationsPerWorker}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.average-number-applications-employer"/>
		</th>
		<td>
			<acme:print value="${avegageNumberOfApplicationsPerEmployer}"/>
		</td>
	</tr>
	 -->	
</table>

<h2>
	<acme:message code="auditor.dashboard.form.title.application-statuses"/>
</h2>

<div>
	<canvas id="canvas"></canvas>
</div>

<!-- 

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"PENDING", "ACCEPTED", "REJECTED"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${ratioOfPendingApplications}"/>, 
						<jstl:out value="${ratioOfAcceptedApplications}"/>, 
						<jstl:out value="${ratioOfRejectedApplications}"/>
					]
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 1.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("canvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>
 -->
<acme:return/>

