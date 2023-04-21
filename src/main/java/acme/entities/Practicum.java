
package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//Attributes--------------------------

	//Code (not blank, unique and pattern)
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}\\d\\d{3}")
	protected String			code;

	//Title (not blank, shorter than 76 char)
	@NotBlank
	@Length(max = 75)
	protected String			title;

	//Abstract (not blank, shorter than 101 char)
	@NotBlank
	@Length(max = 100)
	protected String			abst;

	//Goals (not blank, shorter than 101 char)
	@NotBlank
	@Length(max = 100)
	protected String			goals;

	//Estimated total time (Practicum sessions time ± 10%)

	protected double			totalTime;

	protected boolean			draftMode;

	@ManyToOne()
	@Valid
	@NotNull
	protected Company			company;

	@ManyToOne()
	@Valid
	protected Course			course;

}
