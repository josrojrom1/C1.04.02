
package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Enrolment extends AbstractEntity {

	//Serialisation identifier-----------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes---------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(max = 76)
	protected String			motivation;

	@NotBlank
	@Length(max = 101)
	protected String			goals;

	protected Integer			workTime;

}
