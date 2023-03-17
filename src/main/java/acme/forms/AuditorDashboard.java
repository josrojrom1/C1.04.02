
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	//Atributos-------------

	//GENERAL
	int							totalNumOfTheoryAudits;

	int							totalNumOfHandsonAudits;

	//auditing records in their audits
	Double						auditRAverage;

	Double						auditRDeviation;

	Double						auditRMinimun;

	Double						auditRMaximun;

	//time of the period lengths in their auditing records
	Double						periodAverage;

	Double						periodDeviation;

	Double						periodMinimun;

	Double						periodMaximun;

}
