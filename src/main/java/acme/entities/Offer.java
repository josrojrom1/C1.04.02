
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Offer extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//Attributes--------------------------

	//Moment (in the past)
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				moment;

	//Heading (not blank, shorter than 76 char)
	@NotBlank
	@Length(max = 75)
	protected String			heading;

	//Summary (not blank, shorter than 101 char)
	@NotBlank
	@Length(max = 100)
	protected String			summary;

	//Time period (one day ahead of moment, one week long)
	@NotNull
	@Valid
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				timePeriodStart;

	@NotNull
	@Valid
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				timePeriodEnd;

	//Price (positive or nought)
	@NotNull
	@Valid
	protected Money				retailPrice;

	//Optional link
	@URL
	protected String			link;

}
