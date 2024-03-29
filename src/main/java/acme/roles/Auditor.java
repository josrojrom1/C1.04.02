
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
public class Auditor extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	//Atributos------------------------------

	//firm
	@NotBlank
	@Length(max = 75)
	protected String			firm;

	//Professional ID
	@NotBlank
	@Length(max = 25)
	protected String			professionalId;

	//Certifications
	@NotBlank
	@Length(max = 100)
	protected String			certifications;

	//Optional link
	@URL
	protected String			link;

}
