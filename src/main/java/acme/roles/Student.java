
package acme.roles;

import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student extends AbstractRole {

	//Serialisation identifier-----------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes---------------------

	@NotBlank
	@Length(max = 76)
	protected String			statement;

	@NotBlank
	@Size(max = 101)
	protected List<String>		strongFeatures;

	@NotBlank
	@Size(max = 101)
	protected List<String>		weakFeatures;

	@URL
	protected String			optionalLink;
}
