<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.audit.list.label.code" path="code" width="20%"/>
	<acme:list-column code="auditor.audit.list.label.auditor" path="auditor" width="20%"/>
	<acme:list-column code="auditor.audit.list.label.course" path="course" width="20%"/>

	
	
</acme:list>

<acme:button code="auditor.audit.list.button.create" action="/auditor/audit/create"/>
