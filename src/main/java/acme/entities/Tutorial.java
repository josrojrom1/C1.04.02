
package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.roles.Assistant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tutorial extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//Atributos ===========================

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}\\d\\d{3}")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abst;

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	@Positive
	protected double			totalTime;

	protected boolean			draftMode;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Assistant			assistant;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Course			course;

}
