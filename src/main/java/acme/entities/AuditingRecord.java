
package acme.entities;

import java.sql.Date;

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
public class AuditingRecord extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//Atributos--------------------------

	//Subject (not blank, shorter than 76 characters)
	@NotBlank
	@Length(max = 76)
	protected String			subject;

	//Assessment (not blank, shorter than 101 characters)
	@NotBlank
	@Length(max = 101)
	protected String			assessment;

	//Period (in the past, at least one hour long -- servicio?)
	protected Date				startPeriod;
	protected Date				endPeriod;

	//Mark (“A+”, “A”, “B”, “C”, “F”, or “F-“)
	protected Mark				mark;

	//Optional link
	@URL
	protected String			optionalLink;

}
