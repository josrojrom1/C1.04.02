
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	//Atributos-------------

	//GENERAL
	int							totalNumOfTheoryCourseByMonth;

	int							totalNumOfHandsonCourseByMonth;

	//length metrics of sessions in their practica
	Double						periodLenghtOfSessionDeviation;

	Double						periodLenghtOfSessionAverage;

	Double						periodLenghtOfSessionMinimun;

	Double						periodLenghtOfSessionMaximun;

	//metrics of the period lengths in their practica
	Double						periodLenghtOfPracticaDeviation;

	Double						periodLenghtOfPracticaAverage;

	Double						periodLenghtOfPracticaMinimun;

	Double						periodLenghtOfPracticaMaximun;

}
