
package acme.testing.assistant.session;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionUpdateTest extends TestHarness {

	@Autowired
	AssistantTutorialSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abst, final String sessionType, final String periodStart, final String periodFinish, final String link, final String tutorial) {

		super.signIn("assistant2", "assistant2");
		super.clickOnMenu("Assistant", "List my sessions");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("sessionType", sessionType);
		super.fillInputBoxIn("periodStart", periodStart);
		super.fillInputBoxIn("periodFinish", periodFinish);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");

		super.clickOnMenu("Assistant", "List my sessions");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abst", abst);
		super.checkInputBoxHasValue("sessionType_proxy", sessionType);
		super.checkInputBoxHasValue("periodStart", periodStart);
		super.checkInputBoxHasValue("periodFinish", periodFinish);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String abst, final String sessionType, final String periodStart, final String periodFinish, final String link, final String tutorial) {

		super.signIn("assistant2", "assistant2");
		super.clickOnMenu("Assistant", "List my sessions");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("sessionType", sessionType);
		super.fillInputBoxIn("periodStart", periodStart);
		super.fillInputBoxIn("periodFinish", periodFinish);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		//comprobar que ninguna persona que no sea la creadora de una tutoría pueda crear sesiones en esta
		//también comprueba que no se puedan crear sesiones si la tutoría ya ha sido publicada
		Collection<TutorialSession> sessions;
		String param;
		sessions = this.repository.findManyTutorialSessionByAssistant("assistant2");

		for (final TutorialSession session : sessions) {
			param = String.format("masterId=%d", session.getTutorial().getId());
			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial-session/update", param);
			super.checkPanicExists();

			super.signIn("assistant1", "assistant1");
			super.request("/assistant/tutorial-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/tutorial-session/update", param);
			super.checkPanicExists();
			super.signOut();

			if (!session.getTutorial().isDraftMode()) {
				super.signIn("assistant2", "assistant2");
				super.request("/assistant/tutorial-session/update", param);
				super.checkPanicExists();
				super.signOut();
			}
		}

	}
}
