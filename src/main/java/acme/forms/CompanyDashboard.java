
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
	Integer						totalNumOfTyHPractica;

	//length metrics of sessions in their practica
	Double						practicaSAverage;

	Double						practicaSDeviation;

	Double						practicaSMinimun;

	Double						practicaSMaximun;

	//mterics of the period lengths in their practica
	Double						periodAverage;

	Double						periodDeviation;

	Double						periodMinimun;

	Double						periodMaximun;

}
