
package acme.roles;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Assistant extends AbstractRole {

	private static final long		serialVersionUID	= 1L;

	//Atributos =================================================

	//supervisor 
	@NotBlank
	@Length(max = 75)
	protected String				supervisor;

	//List of expertise fields
	@NotBlank
	@Length(max = 100)
	protected String				expertiseFields;

	//Resumé
	@NotBlank
	@Length(max = 100)
	protected String				resume;

	//optional link
	@URL
	protected String				link;

	@OneToMany()
	protected Set<TutorialSession>	tutorialSession;

	@OneToMany()
	protected Set<Tutorial>			tutorial;

}
