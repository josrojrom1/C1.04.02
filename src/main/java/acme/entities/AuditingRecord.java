
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditingRecord extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//Atributos--------------------------

	//Subject (not blank, shorter than 76 characters)
	@NotBlank
	@Length(max = 76)
	protected String			subject;

	//Assessment (not blank, shorter than 101 characters)
	@NotBlank
	@Length(max = 101)
	protected String			assessment;

	//Period (in the past, at least one hour long -- servicio)
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startPeriod;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endPeriod;

	//Mark (“A+”, “A”, “B”, “C”, “F”, or “F-“)
	@NotNull
	protected Mark				mark;

	//Optional link
	@URL
	protected String			link;

	@ManyToOne()
	@Valid
	@NotNull
	protected Auditor			auditor;

	@ManyToOne()
	@Valid
	@NotNull
	protected Audit				audit;

}
