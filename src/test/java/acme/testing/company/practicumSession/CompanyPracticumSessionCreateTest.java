
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionCreateTest extends TestHarness {

	@Autowired
	CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/Practicumsession/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abst, final String timePeriodStart, final String timePeriodEnd, final String link, final String practicum) {
		//este test comprueba el listado de las sessiones de un practicum de una company
		super.signIn("company1", "company1");
		super.clickOnMenu("Company", "List my practica");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List sessions");
		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("timePeriodStart", timePeriodStart);
		super.fillInputBoxIn("timePeriodEnd", timePeriodEnd);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");

		super.clickOnMenu("Company", "List my practica");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List sessions");
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, practicum);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String abst, final String timePeriodStart, final String timePeriodEnd, final String link, final String practicum) {
		//este test comprueba el listado de las sessiones de un practicum de una company
		super.signIn("company1", "company1");
		super.clickOnMenu("Company", "List my practica");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List sessions");
		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("timePeriodStart", timePeriodStart);
		super.fillInputBoxIn("timePeriodEnd", timePeriodEnd);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		//comprobar que ninguna persona que no sea la creadora de un practicum pueda crear sesiones en esta
		//también comprueba que no se puedan crear sesiones si el practicum ya ha sido publicado
		Collection<PracticumSession> sessions;
		String param;
		sessions = this.repository.findManyPracticumSessionByCompany("company1");

		for (final PracticumSession session : sessions) {
			param = String.format("masterId=%d", session.getPracticum().getId());
			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();

			super.signIn("company1", "company1");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();

			if (!session.getPracticum().isDraftMode()) {
				super.signIn("company2", "company2");
				super.request("/company/practicum-session/create", param);
				super.checkPanicExists();
				super.signOut();
			}
		}

	}
}
