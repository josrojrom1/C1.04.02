
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecturer extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	//Atributos------------------------------

	//Alma Mater
	@NotBlank
	@Length(max = 76)
	protected String			almaMater;

	//A résumé
	@NotBlank
	@Length(max = 101)
	protected String			resume;

	//Qualifications
	@NotBlank
	@Length(max = 101)
	protected String			qualifications;

	//Optional link
	@URL
	protected String			optionalLink;

}
