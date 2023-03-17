
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
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
public class Note extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//Atributos--------------

	//Instantiation moment
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				moment;

	//Title (not blank, shorter than 101 char)
	@NotBlank
	@Length(max = 100)
	protected String			title;

	//Author (not blank, shorter than 76 char)
	@NotBlank
	@Length(max = 75)
	protected String			author;

	//Message (not blank, shorter than 101 char)
	@NotBlank
	@Length(max = 100)
	protected String			message;

	//Email address (optional)
	@Email
	protected String			email;

	//Optional link
	@URL
	protected String			link;
}
