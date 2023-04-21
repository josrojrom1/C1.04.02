<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:list>
	<acme:list-column code="student.activity.list.label.title" path="title" width="20%"/>
	<acme:list-column code="student.activity.list.label.abst" path="abst" width="50%"/>
	<acme:list-column  code="student.activity.list.label.activityType" path="activityType" choices="${choices} width="20%"/>
	<acme:list-column code="student.activity.list.label.masterId" path="masterId" width="20%"/>
</acme:list>
<acme:button code="student.activity.list.button.create" action="/student/create"/>