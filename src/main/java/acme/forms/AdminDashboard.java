
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	//Atributos-------------

	//Number of principals per role
	Integer						totalNumOf;

	//Ratio of peeps with email and link
	Double						emailAndLinkPeepRatio;

	//Ratio of non-critical and critical bulletins

	Double						criticalBulletinRatio;

	Double						nonCriticalBulletinRatio;

	//Metrics of the budget in offers grouped by currency
	//(Faltan las currencies grouping)
	Double						budgetAverage;

	Double						budgetDeviation;

	Double						budgetMinimun;

	Double						budgetMaximun;

	//Metrics on the number of notes posted in the past 10 weeks
	Double						notesAverage;

	Double						notesDeviation;

	Double						notesMinimun;

	Double						notesMaximun;

}
