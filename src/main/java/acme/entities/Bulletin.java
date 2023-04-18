
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Bulletin extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//Atributos--------------

	//Instantiation moment (in the past)
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date				moment;

	//Title (not blank, shorter than 76 char)
	@NotBlank
	@Length(max = 75)
	protected String			title;

	//Message (not blank, shorter than 101 char)
	@NotBlank
	@Length(max = 100)
	protected String			message;

	//Flag (critical or not)
	@NotNull
	protected FlagType			flag;

	//Optional link
	@URL
	protected String			link;
}
