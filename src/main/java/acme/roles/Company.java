
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
public class Company extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	//Attributes------------------------------

	//Name
	@NotBlank
	@Length(max = 75)
	protected String			name;

	//VAT number
	@NotBlank
	@Length(max = 25)
	protected String			vat;

	//Summary
	@NotBlank
	@Length(max = 100)
	protected String			summary;

	//Optional link
	@URL
	protected String			link;

}
