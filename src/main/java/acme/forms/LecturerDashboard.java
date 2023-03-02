
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
	protected Double			lectureAverage;

	protected Double			lectureDeviation;

	protected Double			lectureMinimun;

	protected Double			lectureMaximun;

	//LEARNING TIME OF COURSES
	protected Double			courseAverage;

	protected Double			courseDeviation;

	protected Double			courseMinimun;

	protected Double			courseMaximun;

}
