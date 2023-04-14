
package acme.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CourseOfLectures extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	//Atributos (ID de curso e ID de lectura)

	@ManyToOne()
	@Valid
	@NotNull
	protected Course			course;

	@ManyToOne()
	@Valid
	@NotNull
	protected Lecture			lecture;

	//Atributos derivados

	//	protected notPureTheoreticalCourses() {
	//		
	//	}

}
