
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	//Atributos-------------

	//GENERAL
	protected Integer			totalNumOfTyHAudits;

	//auditing records in their audits
	protected Double			auditRAverage;

	protected Double			auditRDeviation;

	protected Double			auditRMinimun;

	protected Double			auditRMaximun;

	//time of the period lengths in their auditing records
	protected Double			periodAverage;

	protected Double			periodDeviation;

	protected Double			periodMinimun;

	protected Double			periodMaximun;

}
