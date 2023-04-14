<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>

	<acme:input-moment code="any.peep.form.label.moment" path="moment"/>
	<acme:input-textbox code="any.peep.form.label.title" path="title"/>
	<jstl:if test="${_command == 'create'}">
		<acme:input-textbox code="any.peep.form.label.nick" path="nick" readonly="true"/>
		</jstl:if>
		<jstl:if test="${_command == 'create'}">
			<acme:input-textbox code="any.peep.form.label.nick" path="nick"/>
		</jstl:if>
	<acme:input-textarea code="any.peep.form.label.message" path="message"/>
	<acme:input-email code="any.peep.form.label.email" path="email"/>
	<acme:input-url code="any.peep.form.label.link" path="link"/>
	
	<jstl:if test="${_command == 'create'}">
		<acme:submit code="any.peep.form.button.create" action="/any/peep/create"/>
	</jstl:if>
	
</acme:form>

