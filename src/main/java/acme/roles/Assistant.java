
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
public class Assistant extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	//Atributos =================================================

	//supervisor 
	@NotBlank
	@Length(max = 76)
	protected String			supervisor;

	//List of expertise fields
	@NotBlank
	@Length(max = 101)
	protected String			expertiseFields;

	//Resumé
	@NotBlank
	@Length(max = 101)
	protected String			resume;

	//optional link
	@URL
	protected String			optionalLink;
}
