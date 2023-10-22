
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionShowTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test101Positive(final int recordIndex, final String title, final String abst, final String timePeriodStart, final String timePeriodEnd, final String link, final String practicum) {

		super.signIn("company2", "company2");
		super.clickOnMenu("Company", "List my practica");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.clickOnButton("List sessions");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abst", abst);
		super.checkInputBoxHasValue("timePeriodStart", timePeriodStart);
		super.checkInputBoxHasValue("timePeriodEnd", timePeriodEnd);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("practicum", practicum);

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		Collection<PracticumSession> sessions;
		String param;
		sessions = this.repository.findManyPracticumSessionByCompany("company2");
		for (final PracticumSession session : sessions) {
			param = String.format("id=%d", session.getId());
			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/show", param);
			super.checkPanicExists();

			super.signIn("company1", "company1");
			super.request("/company/practicum-session/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum-session/show", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
