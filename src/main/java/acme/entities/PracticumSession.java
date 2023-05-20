
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PracticumSession extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//Attributes--------------------------

	//Title (not blank, shorter than 76 char)
	@NotBlank
	@Length(max = 75)
	protected String			title;

	//Abstract (not blank, shorter than 101 char)
	@NotBlank
	@Length(max = 100)
	protected String			abst;

	//Time period (one week ahead, one week long)
	@NotNull
	@Valid
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				timePeriodStart;

	@NotNull
	@Valid
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				timePeriodEnd;

	//Optional link
	@URL
	protected String			link;

	//If this session is an addendum session
	protected boolean			isAddendum;

	@NotNull
	@Valid
	@ManyToOne
	protected Practicum			practicum;

}
