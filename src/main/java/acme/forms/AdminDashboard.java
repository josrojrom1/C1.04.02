
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	//Atributos-------------

	//Number of principals per role
	Map<String, Integer>		totalNumOfPrincipal;

	//Ratio of peeps with email and link
	Double						ratioOfPeeps;

	//Ratio of non-critical and critical bulletins

	Double						ratioOfCriticalBulletins;

	Double						ratioOfNonCriticalBulletins;

	//Metrics of the budget in offers grouped by currency
	Double						budgetAverage;

	Double						budgetDeviation;

	Double						budgetMinimun;

	Double						budgetMaximun;

	//Metrics on the number of notes posted in the past 10 weeks
	Double						numOfNotesAverage;

	Double						numOfNotesDeviation;

	Double						numOfNotesMinimun;

	Double						numOfNotesMaximun;

}
