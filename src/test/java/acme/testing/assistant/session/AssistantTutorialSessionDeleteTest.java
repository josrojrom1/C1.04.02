
package acme.testing.assistant.session;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionDeleteTest extends TestHarness {

	@Autowired
	protected AssistantTutorialSessionTestRepository repository;


	@Test
	public void test100Positive() {
		super.signIn("assistant2", "assistant2");
		super.clickOnMenu("Assistant", "List my sessions");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();
		super.signOut();
	}
	@Test
	public void test300Hacking() {
		Collection<TutorialSession> sessions;
		String param;
		sessions = this.repository.findManyTutorialSessionByAssistant("assistant2");
		for (final TutorialSession session : sessions) {
			param = String.format("id=%d", session.getId());
			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial-session/delete", param);
			super.checkPanicExists();

			super.signIn("assistant1", "assistant1");
			super.request("/assistant/tutorial-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/tutorial-session/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
