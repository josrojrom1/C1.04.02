
package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//Atributos--------------------------

	//Code (not blank, unique and pattern)
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3} \\d{3}")
	protected String			code;

	//Title (not blank, shorter than 76 char)
	@NotBlank
	@Length(max = 75)
	protected String			title;

	//Abstract (not blank, shorter than 101 char)
	@NotBlank
	@Length(max = 100)
	protected String			abst;

	//Course (theory course or hands-on course)
	protected LessonType		courseType;

	//Retail price (positive or nought)(Purely theoretical courses rejected by the system)
	@Valid
	protected Money				retailPrice;

	//Optional link
	@URL
	protected String			link;

	//manyToOne() con NotNull y valid --> lecturer
	@ManyToOne()
	@NotNull
	@Valid
	protected Lecturer			lecturer;

	//Enrolment manyToOne() 
	//@ManyToOne()
	//@NotNull
	//@Valid
	//protected Enrolment			enrolment;

	//Tutorial manyToOne() 
	@ManyToOne()
	@NotNull
	@Valid
	protected Tutorial			tutorial;

	//Tutorial manyToOne() 
	@ManyToOne()
	@NotNull
	@Valid
	protected Audit				audit;

	//Practicum manyToOne() 
	@ManyToOne()
	@NotNull
	@Valid
	protected Practicum			practicum;

}
