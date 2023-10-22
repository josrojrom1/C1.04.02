
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialUpdateTest extends TestHarness {

	@Autowired
	protected AssistantTutorialTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String abst, final String goals, final String totalTime, final String course) {

		super.signIn("assistant2", "assistant2");
		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("totalTime", totalTime);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abst", abst);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("totalTime", totalTime);
		super.checkInputBoxHasValue("course", course);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String title, final String abst, final String goals, final String totalTime, final String course) {
		super.signIn("assistant2", "assistant2");
		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("totalTime", totalTime);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialsFromUsername("assistant2");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("id=%d", tutorial.getId());
			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
