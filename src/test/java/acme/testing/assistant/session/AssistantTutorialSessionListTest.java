
package acme.testing.assistant.session;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AssistantTutorialSessionListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/list-mine-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordIndex, final String title, final String tutorial) {
		//Este test comprueba el listado de todas las sesiones de un asistente
		super.signIn("assistant2", "assistant2");
		super.clickOnMenu("Assistant", "List my sessions");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, tutorial);
		super.signOut();
	}
	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test101positive(final int recordIndex, final String title, final String tutorial) {
		//este test comprueba el listado de las sessiones de una tutoría de un asistente
		super.signIn("assistant2", "assistant2");
		super.clickOnMenu("Assistant", "List my tutorials");
		super.sortListing(0, "desc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List sessions");

		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, tutorial);
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/assistant/tutorial-session/list");
		super.checkPanicExists();

		super.signIn("lecturer1", "lecturer1");
		super.request("/assistant/tutorial-session/list");
		super.checkPanicExists();
		super.signOut();
	}
}
