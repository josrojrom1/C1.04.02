
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
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Audit extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//Atributos------------------------------------

	//Code (pattern “[A-Z]{1,3}[0-9][0-9]{3}”, not blank, unique)
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}\\d\\d{3}")
	protected String			code;

	//Conclusion (not blank, shorter than 101 characters)
	@NotBlank
	@Length(max = 100)
	protected String			conclusion;

	//Weak Points (not blank, shorter than 101 characters)
	@NotBlank
	@Length(max = 100)
	protected String			weakPoints;

	//Strong Points (not blank, shorter than 101 characters)
	@NotBlank
	@Length(max = 100)
	protected String			strongPoints;

	//Mark (computed as the mode of the marks in the corresponding
	//auditing records; ties must be bro-ken arbitrarily if necessary)

	protected Mark				mark;

	@ManyToOne()
	@Valid
	protected Auditor			auditor;

	@ManyToOne()
	@Valid
	@NotNull
	protected Course			course;

	protected boolean			draftMode;
}
