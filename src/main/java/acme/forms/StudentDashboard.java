
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

	protected int				totalNumberHandsonWorkbookActivities;

	protected int				totalNumberTheoryWorkbookActivities;

	protected Double			activityWorkbookTimeAverage;

	protected Double			activityWorkbookTimeDeviation;

	protected Double			activityWorkbookTimeMaximum;

	protected Double			activityWorkbookTimeMinimum;

	protected Double			courseEnrolledTimeAverage;

	protected Double			courseEnrolledTimeDeviation;

	protected Double			courseEnrolledTimeMaximum;

	protected Double			courseEnrolledTimeMinimum;

}
