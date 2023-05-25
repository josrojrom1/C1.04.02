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
	<acme:message code="assistant.dashboard.form.title.general-indicators"/>
</h1>

<table class="table table-sm">


	<tr>
		<!-- TOTAL NUM THEORY/HANDS ON LECTURES)-->
		<th><h2><acme:message code="assistant.dashboard.form.lectures-section"/></h2></th>
	</tr>
	
	<tr>
		<th scope="row"><acme:message code="assistant.dashboard.form.label.total-num-theory"/></th>	
		<td><acme:print value="${totalNumOfTheoryTutorials}"/></td>
	</tr>
		
	<tr>
		<th scope="row"><acme:message code="assistant.dashboard.form.label.total-num-handson"/></th>
		<td><acme:print value="${totalNumOfHandsonTutorials}"/></td>
	
	</tr>

	
</table>
	
<acme:return/>

