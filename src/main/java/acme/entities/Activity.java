
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
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
public class Activity extends AbstractEntity {

	//Serialisation identifier-----------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes---------------------

	@Length(max = 75)
	@NotBlank
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abst;

	@NotNull
	protected LessonType		activityType;

	@Temporal(TemporalType.TIMESTAMP)
	@Valid
	@NotNull
	protected Date				startTimePeriod;

	@Temporal(TemporalType.TIMESTAMP)
	@Valid
	@NotNull
	protected Date				endTimePeriod;

	@URL
	protected String			link;

	@NotNull
	@Valid
	@ManyToOne
	protected Enrolment			enrolment;

}
