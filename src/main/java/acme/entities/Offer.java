
package acme.entities;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;

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
	@NotEmpty
	@Past
	protected Instant			moment;

	//Heading (not blank, shorter than 76 char)
	@NotBlank
	@Length(max = 76)
	protected String			heading;

	//Summary (not blank, shorter than 101 char)
	@NotBlank
	@Length(max = 101)
	protected String			summary;

	//Time period (one day ahead of moment, one week long)
	@NotNull
	@Future
	protected Date				timePeriodStart;

	@NotNull
	@Future
	protected Date				timePeriodEnd;

	//Price (positive or nought)
	@PositiveOrZero
	protected Money				retailPrice;

	//Optional link
	@URL
	protected String			link;

}
