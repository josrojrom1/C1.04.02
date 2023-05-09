
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	//Atributos-------------

	//GENERAL
	protected Integer			totalNumOfTheoryLectures;
	protected Integer			totalNumOfHandsonLectures;

	//LEARNING TIME OF LECTURES
	protected Double			lectureLearningTimeAverage;

	protected Double			lectureLearningTimeDeviation;

	protected Double			lectureLearningTimeMinimum;

	protected Double			lectureLearningTimeMaximum;

	//LEARNING TIME OF COURSES
	protected Double			courseLearningTimeAverage;

	protected Double			courseLearningTimeDeviation;

	protected Double			courseLearningTimeMinimum;

	protected Double			courseLearningTimeMaximum;

}
