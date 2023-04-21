
package acme.entities.configuration;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Configuration extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@NotBlank
	protected String			systemCurrency;

	@NotBlank
	protected String			acceptedCurrencies;

	@NotBlank
	@Pattern(regexp = "^(\\([\\w\\s\\d';áéíóúÁÉÍÓÚçÇ€$&@]*,\\d.\\d{1,2}\\),)*\\([\\w\\s\\d'áéíóúÁÉÍÓÚçÇ€$&@]*,\\d.\\d{1,2}\\)$", message = "{acme.validation.configuration.spam-terms}")
	protected String			spamTerms;

	@PositiveOrZero
	@Max(1)
	protected double			spamThreshold;

}
