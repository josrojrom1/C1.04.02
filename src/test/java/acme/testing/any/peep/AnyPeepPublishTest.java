
package acme.testing.any.peep;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyPeepPublishTest extends TestHarness {

	//TEST POSITIVO
	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String moment, final String title, final String nick, final String message, final String email, final String link) {
		// HINT: En este test, listamos las peeps, creamos una de ellas y la publicamos
		super.clickOnMenu("Peeps", "List of Peeps");
		super.checkListingExists();
		super.clickOnButton("Publish");
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();
		super.clickOnMenu("Peeps", "List of Peeps");
		super.checkListingExists();
		super.sortListing(1, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("message", message);
		super.checkInputBoxHasValue("email", email);
		super.checkInputBoxHasValue("link", link);

	}
	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/publish-logged-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test101Positive(final int recordIndex, final String moment, final String title, final String nick, final String message, final String email, final String link) {
		// HINT: En este test, listamos las peeps, creamos una de ellas y la publicamos
		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Peeps", "List of Peeps");
		super.checkListingExists();
		super.clickOnButton("Publish");
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("nick", nick);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();
		super.clickOnMenu("Peeps", "List of Peeps");
		super.checkListingExists();
		super.sortListing(1, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("nick", nick);
		super.checkInputBoxHasValue("message", message);
		super.checkInputBoxHasValue("email", email);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	//TEST NEGATIVO
	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String moment, final String title, final String nick, final String message, final String email, final String link) {
		// HINT: En este test intentamos crear peeps con datos incorrectos.

		super.clickOnMenu("Peeps", "List of Peeps");
		super.checkListingExists();
		super.clickOnButton("Publish");
		super.checkFormExists();
		super.fillInputBoxIn("moment", moment);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist();
	}

	//TEST NEGATIVO
	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/publish-logged-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test201Negative(final int recordIndex, final String moment, final String title, final String nick, final String message, final String email, final String link) {
		// HINT: En este test intentamos crear peeps con datos incorrectos.
		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Peeps", "List of Peeps");
		super.checkListingExists();
		super.clickOnButton("Publish");
		super.checkFormExists();
		super.fillInputBoxIn("moment", moment);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("nick", nick);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist();
		super.signOut();
	}

}
