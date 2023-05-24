
package acme.testing.assistant.tutorial;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AssistantTutorialListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/list-all-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordIndex, final String code, final String title, final String abst, final String goals, final String totalTime) {
		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/assistant/tutorial/list");
		super.checkPanicExists();
		super.signIn("lecturer1", "lecturer1");
		super.request("/assistant/tutorial/list");
		super.checkPanicExists();
		super.signOut();
	}
}
