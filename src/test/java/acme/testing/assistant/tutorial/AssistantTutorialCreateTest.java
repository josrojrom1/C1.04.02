
package acme.testing.assistant.tutorial;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AssistantTutorialCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String abst, final String goals, final String totalTime, final String course) {

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();

		super.clickOnButton("Create a tutorial");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("totalTime", totalTime);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();

		super.sortListing(0, "desc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abst", abst);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("totalTime", totalTime);
		super.checkInputBoxHasValue("course", course);

		super.clickOnButton("List sessions");
		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}
	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String title, final String abst, final String goals, final String totalTime, final String course) {
		//comprobar que en todos los campos se comprueben las validaciones, por ejemplo, notBlank o datos erróneos
		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();

		super.clickOnButton("Create a tutorial");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("totalTime", totalTime);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		//probar que solo los principales autorizados puedan crear tutorías
		super.checkLinkExists("Sign in");
		super.request("/assistant/tutorial/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/assistant/tutorial/create");
		super.checkPanicExists();
		super.signOut();
	}
}
