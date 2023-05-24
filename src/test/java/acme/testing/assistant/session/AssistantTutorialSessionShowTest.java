
package acme.testing.assistant.session;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionShowTest extends TestHarness {

	@Autowired
	protected AssistantTutorialSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/show-mine-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abst, final String sessionType, final String periodStart, final String periodFinish, final String link, final String tutorial) {

		super.signIn("assistant2", "assistant2");
		super.clickOnMenu("Assistant", "List my sessions");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abst", abst);
		//super.checkInputBoxHasValue("sessionType", sessionType);
		super.checkInputBoxHasValue("periodStart", periodStart);
		super.checkInputBoxHasValue("periodFinish", periodFinish);
		super.checkInputBoxHasValue("link", link);
		//super.checkInputBoxHasValue("tutorial", tutorial);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test101Positive(final int recordIndex, final String title, final String abst, final String sessionType, final String periodStart, final String periodFinish, final String link, final String tutorial) {

		super.signIn("assistant2", "assistant2");
		super.clickOnMenu("Assistant", "List my tutorials");
		super.sortListing(0, "desc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.clickOnButton("List sessions");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abst", abst);
		//super.checkInputBoxHasValue("sessionType", sessionType);
		super.checkInputBoxHasValue("periodStart", periodStart);
		super.checkInputBoxHasValue("periodFinish", periodFinish);
		super.checkInputBoxHasValue("link", link);
		//super.checkInputBoxHasValue("tutorial", tutorial);

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
			super.request("/assistant/tutorial-session/show", param);
			super.checkPanicExists();

			super.signIn("assistant1", "assistant1");
			super.request("/assistant/tutorial-session/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/tutorial-session/show", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
