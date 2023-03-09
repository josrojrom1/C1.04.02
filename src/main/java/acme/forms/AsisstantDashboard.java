
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AsisstantDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	//Atributos-------------

	//GENERAL
	protected Integer			totalNumOfTheoryTutorials;
	protected Integer			totalNumOfHandsonTutorials;

	//LEARNING TIME OF tutorials
	protected Double			tutorialAverage;

	protected Double			tutorialDeviation;

	protected Double			tutorialMinimun;

	protected Double			tutorialMaximun;

	//LEARNING TIME OF sessions
	protected Double			sessionsAverage;

	protected Double			sessionsDeviation;

	protected Double			sessionsMinimun;

	protected Double			sessionsMaximun;

}
