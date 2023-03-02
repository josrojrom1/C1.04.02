
package acme.entities;

import java.time.Duration;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Session extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//Atributos--------------------------
	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			abst;

	protected SessionType		sessionType;

	//TODO
	protected Duration			period;

	@URL
	protected String			optionalLink;
}
