
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
import acme.roles.Assistant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TutorialSession extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//Atributos--------------------------
	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abst;

	protected LessonType		sessionType;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				periodStart;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				periodFinish;

	@URL
	protected String			link;

	@ManyToOne()
	@NotNull
	@Valid
	protected Assistant			assistant;

	@ManyToOne()
	@NotNull
	@Valid
	protected Tutorial			tutorial;
}
