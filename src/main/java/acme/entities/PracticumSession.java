
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.Future;
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
	@Length(max = 76)
	protected String			title;

	//Abstract (not blank, shorter than 101 char)
	@NotBlank
	@Length(max = 101)
	protected String			abst;

	//Time period (one week ahead, one week long)
	@NotNull
	@Future
	protected Date				timePeriodStart;

	@NotNull
	@Future
	protected Date				timePeriodEnd;

	//Optional link
	@URL
	protected String			link;

}
