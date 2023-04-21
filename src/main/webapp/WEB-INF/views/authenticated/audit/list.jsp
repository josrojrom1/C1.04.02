<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.audit.list.label.code" path="code" width="20%"/>
	<acme:list-column code="authenticated.audit.list.label.mark" path="mark" width="20%"/>
	<acme:list-column code="authenticated.audit.list.label.auditor" path="auditor" width="20%"/>
	<acme:list-column code="authenticated.audit.list.label.course" path="course" width="20%"/>

	
	
</acme:list>
