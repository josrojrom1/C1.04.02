
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDashboard extends AbstractForm {

	//Serialisation identifier-----------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes---------------------

	protected Integer			totalNumberHandsonWorkbookActivities;

	protected Integer			totalNumberTheoryWorkbookActivities;

	protected Double			workbookAverage;

	protected Double			workbookDeviation;

	protected Double			workbookMaxPeriod;

	protected Double			workbookMinPeriod;

	protected Double			enrolmentAverage;

	protected Double			enrolmentDeviation;

	protected Double			enrolmentMaxTime;

	protected Double			enrolmentMinTime;

}
