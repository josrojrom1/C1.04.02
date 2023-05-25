<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<jstl:if test="${correction}">
		<div class="correction" >

			<acme:list-column code="auditor.auditingRecord.list.label.subject" path="subject" width="20%"/>
			<acme:list-column code="auditor.auditingRecord.list.label.mark" path="mark" width="20%"/>
			<acme:list-column code="auditor.auditingRecord.list.label.auditor" path="auditor" width="20%"/>
			<acme:list-column code="auditor.auditingRecord.list.label.audit" path="audit" width="20%"/>
			<acme:list-column code="auditor.auditingRecord.list.label.correction" path="correction" width="20%"/>
		</div>
	</jstl:if>
	<jstl:if test="${!correction}">
		<acme:list-column code="auditor.auditingRecord.list.label.subject" path="subject" width="20%"/>
		<acme:list-column code="auditor.auditingRecord.list.label.mark" path="mark" width="20%"/>
		<acme:list-column code="auditor.auditingRecord.list.label.auditor" path="auditor" width="20%"/>
		<acme:list-column code="auditor.auditingRecord.list.label.audit" path="audit" width="20%"/>
		<acme:list-column code="auditor.auditingRecord.list.label.correction" path="correction" width="20%"/>
	</jstl:if>
</acme:list>

<style>
  .div {
  
    font-style: bold;
  }
</style>

