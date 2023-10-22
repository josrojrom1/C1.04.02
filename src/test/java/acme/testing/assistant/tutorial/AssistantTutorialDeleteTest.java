
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialDeleteTest extends TestHarness {

	@Autowired
	protected AssistantTutorialTestRepository repository;


	@Test
	public void test100Positive() {
		super.signIn("assistant2", "assistant2");
		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();
		super.signOut();
	}

	@Test
	public void test300hacking() {
		Collection<Tutorial> tutorials;
		String param;
		tutorials = this.repository.findManyTutorialsFromUsername("assistant1");
		for (final Tutorial tutorial : tutorials) {

			param = String.format("id=%d", tutorial.getId());
			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial/delete", param);
			super.checkPanicExists();

			super.signIn("assistant2", "assistant2");
			super.request("/assistant/tutorial/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/tutorial/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
