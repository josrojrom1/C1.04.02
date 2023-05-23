
package acme.testing.assistant.session;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionCreateTest extends TestHarness {

	@Autowired
	AssistantTutorialSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abst, final String sessionType, final String periodStart, final String periodFinish, final String link, final String tutorial) {
		//este test comprueba el listado de las sessiones de una tutoría de un asistente
		super.signIn("assistant2", "assistant2");
		super.clickOnMenu("Assistant", "List my tutorials");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List sessions");
		super.clickOnButton("Create a session");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("sessionType", sessionType);
		super.fillInputBoxIn("periodStart", periodStart);
		super.fillInputBoxIn("periodFinish", periodFinish);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");

		super.clickOnMenu("Assistant", "List my tutorials");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List sessions");
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, tutorial);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String abst, final String sessionType, final String periodStart, final String periodFinish, final String link, final String tutorial) {
		//este test comprueba el listado de las sessiones de una tutoría de un asistente
		super.signIn("assistant2", "assistant2");
		super.clickOnMenu("Assistant", "List my tutorials");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List sessions");
		super.clickOnButton("Create a session");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("sessionType", sessionType);
		super.fillInputBoxIn("periodStart", periodStart);
		super.fillInputBoxIn("periodFinish", periodFinish);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");
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
			super.request("/assistant/tutorial-session/create", param);
			super.checkPanicExists();

			super.signIn("assistant1", "assistant1");
			super.request("/assistant/tutorial-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/tutorial-session/create", param);
			super.checkPanicExists();
			super.signOut();

			if (!session.getTutorial().isDraftMode()) {
				super.signIn("assistant2", "assistant2");
				super.request("/assistant/tutorial-session/create", param);
				super.checkPanicExists();
				super.signOut();
			}
		}

	}
}
