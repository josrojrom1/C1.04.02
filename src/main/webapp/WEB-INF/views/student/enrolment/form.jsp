<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<jstl:set var="f" value="${isFinalised}"/>

<acme:form>
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' || _command == 'update'|| _command == 'delete' || _command == 'create'}">
			<acme:input-textbox code="student.enrolment.show.label.code" path="code"/>
		<acme:input-textbox code="student.enrolment.show.label.motivation" path="motivation"/>
		<acme:input-textbox code="student.enrolment.show.label.goals" path="goals"/>
		<acme:input-double code="student.enrolment.show.label.workTime" path= "workTime" readonly="true"/>
		<acme:input-select code="student.enrolment.show.label.course" path="course" choices="${courseChoices}"/>
			
			<jstl:choose>
				<jstl:when test="${_command == 'show' || _command == 'update'|| _command == 'delete'}">
					<jstl:choose>
						<jstl:when test="${f == false}">
							<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
							<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
							<acme:button code="student.enrolment.form.button.finalise" action="/student/enrolment/finalise?id=${id}"/>							
						</jstl:when>
					</jstl:choose>
					
					
				</jstl:when>
				<jstl:when test="${_command == 'create'}">
					<acme:submit code="student.enrolment.form.button.create" action="/student/enrolment/create"/>
				</jstl:when>
			</jstl:choose>
		</jstl:when>
		
		<jstl:when test="${_command == 'finalise'}">
			<acme:input-textbox readonly = "${true}" code="student.enrolment.form.label.code" path="code"/>	
			<acme:input-textbox code="student.enrolment.form.label.creditCardHolder" path="creditCardHolder"/>
			<acme:input-moment code="student.enrolment.form.label.expiryDate" path="expiryDate"/>
			<acme:input-integer code="student.enrolment.form.label.cvc" path="cvc"/>
			<acme:input-integer code="student.enrolment.form.label.upperNibble" path="upperNibble"/>
			<acme:input-integer code="student.enrolment.form.label.lowerNibble" path="lowerNibble"/>

			<acme:submit code="student.enrolment.form.button.finalise" action="/student/enrolment/finalise?id=${id}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>