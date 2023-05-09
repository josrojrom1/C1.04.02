
package acme.utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import main.SpamManagement;

@Service
public class SpamDetector {

	@Autowired
	protected AdministratorConfigurationRepository repository;


	public boolean spamChecker(final String text) {
		final Configuration sysConfig = this.repository.findConfiguration();
		final Map<String, Double> terms = this.termStringToMap(sysConfig.getSpamTerms());
		return SpamManagement.checkSpam(text, terms, sysConfig.getSpamThreshold());
	}

	private Map<String, Double> termStringToMap(final String terms) {
		final HashMap<String, Double> result = new HashMap<>();

		final String[] parsedTerms = terms.replace("(", "").replace(")", "").split(",");

		for (int i = 0; i < parsedTerms.length - 2; i += 2)
			result.put(parsedTerms[i], Double.parseDouble(parsedTerms[i + 1]));

		return result;
	}

}
