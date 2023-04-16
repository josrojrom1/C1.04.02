
package acme.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
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
	@Pattern(regexp = "[A-Z]{1,3} \\d{3}", message = "{validation.course.code}")
	protected String			code;

	//Title (not blank, shorter than 76 char)
	@NotBlank
	@Length(max = 75)
	protected String			title;

	//Abstract (not blank, shorter than 101 char)
	@NotBlank
	@Length(max = 100)
	protected String			abst;

	//Course (theory course or hands-on course)(Purely theoretical courses rejected by the system)

	protected LessonType		courseType;

	//Retail price (positive or nought)
	@NotNull
	@Valid
	protected Money				retailPrice;

	//Optional link
	@URL
	protected String			link;

	protected boolean			draftMode;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				deadLine;


	//Atributo derivado "esta disponible"
	@Transient
	public boolean isAvailable() {
		boolean result;

		result = !this.draftMode && MomentHelper.isFuture(this.deadLine);

		return result;
	}

	//Atributo derivado tipo de curso en funcion de las lectures que tiene
	//	@Transient
	//	public boolean handsonCourse() {
	//		final boolean result;
	//		result =
	//		return result;
	//	}


	//Lecturer manyToOne()
	@ManyToOne()
	@NotNull
	@Valid
	protected Lecturer	lecturer;

	//Tutorial manyToOne() 
	@ManyToOne()
	@NotNull
	@Valid
	protected Tutorial	tutorial;

	//Tutorial manyToOne() 
	@ManyToOne()
	@NotNull
	@Valid
	protected Audit		audit;

	//Practicum manyToOne() 
	@ManyToOne()
	@NotNull
	@Valid
	protected Practicum	practicum;

}
