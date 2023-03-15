
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

import org.hibernate.validator.constraints.URL;

import acme.framework.components.accounts.Administrator;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	//Serialisation identifier-----------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes---------------------

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date				moment;

	@NotNull
	@Valid
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startDisplayPeriod;

	@NotNull
	@Valid
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endDisplayPeriod;

	@NotBlank
	@URL
	protected String			pictureLink;

	@NotBlank
	@URL
	protected String			webLink;

	@NotNull
	@Valid
	@ManyToOne
	protected Administrator		administrator;

}
