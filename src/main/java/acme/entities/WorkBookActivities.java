
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
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
public class WorkBookActivities extends AbstractEntity {

	//Serialisation identifier-----------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes---------------------

	@Length(max = 76)
	@NotBlank
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			abst;

	@NotNull
	protected ActivityType		activityType;

	@Temporal(TemporalType.TIMESTAMP)
	@Future
	@Past
	protected Date				timePeriod;

	@URL
	protected String			optionalLink;

}
