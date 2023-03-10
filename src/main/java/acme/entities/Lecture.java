
package acme.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecture extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//Atributos------------------------------------

	//Title (not blank, shorter than 76 char)
	@NotBlank
	@Length(max = 76)
	protected String			title;

	//Abstract (not blank, shorter than 101 char)
	@NotBlank
	@Length(max = 101)
	protected String			abst;

	//Estimated learning time (in hours, positive or nought)
	@NotNull
	@PositiveOrZero
	protected Integer			learningTime;

	//Body (not blank, shorter than 101 char)
	@NotBlank
	@Length(max = 101)
	protected String			body;

	//Lecture type (theoretical or hands-on)
	@NotNull
	protected LessonType		lectureType;

	//Optional link with further information
	@URL
	protected String			link;

	//A course aggregates several lectures by the same lecturer
	@NotNull
	@Valid
	@ManyToOne()
	protected Course			course;

}
