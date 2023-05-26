
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionUpdateTest extends TestHarness {

	@Autowired
	CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abst, final String timePeriodStart, final String timePeriodEnd, final String link, final String practicum) {

		super.signIn("company2", "company2");
		super.clickOnMenu("Company", "List my practica");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List sessions");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("timePeriodStart", timePeriodStart);
		super.fillInputBoxIn("timePeriodEnd", timePeriodEnd);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");

		super.clickOnMenu("Company", "List my practica");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List sessions");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abst", abst);
		super.checkInputBoxHasValue("timePeriodStart", timePeriodStart);
		super.checkInputBoxHasValue("timePeriodEnd", timePeriodEnd);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String abst, final String timePeriodStart, final String timePeriodEnd, final String link, final String tutorial) {

		super.signIn("company2", "company2");
		super.clickOnMenu("Company", "List my practica");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List sessions");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("timePeriodStart", timePeriodStart);
		super.fillInputBoxIn("timePeriodEnd", timePeriodEnd);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		//comprobar que ninguna persona que no sea la creadora de una tutoría pueda crear sesiones en esta
		//también comprueba que no se puedan crear sesiones si la tutoría ya ha sido publicada
		Collection<PracticumSession> sessions;
		String param;
		sessions = this.repository.findManyPracticumSessionByCompany("company2");

		for (final PracticumSession session : sessions) {
			param = String.format("id=%d", session.getId());
			super.checkLinkExists("Sign in");
			super.request("/company1/practicum-session/update", param);
			super.checkPanicExists();

			super.signIn("company1", "company1");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company1/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();

			if (!session.getPracticum().isDraftMode()) {
				super.signIn("assistant2", "assistant2");
				super.request("/company/practicum-session/update", param);
				super.checkPanicExists();
				super.signOut();
			}
		}

	}
}
