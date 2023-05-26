
package acme.testing.company.practicum;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Tutorial;
import acme.testing.TestHarness;

public class CompanyPracticumShowTest extends TestHarness {

	@Autowired
	protected CompanyPracticumTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordIndex, final String code, final String title, final String abst, final String goals, final String totalTime, final String course) {
		super.signIn("company1", "company1");
		super.clickOnMenu("Company", "List my practica");
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

	@Test
	public void test300hacking() {
		// HINT: this test tries to show tutorials to unauthorised principals, like anonymous or other assistants
		Collection<Tutorial> tutorials;
		String param;
		tutorials = this.repository.findManyPracticumsFromUsername("company1");
		for (final Tutorial tutorial : tutorials) {

			param = String.format("id=%d", tutorial.getId());
			super.checkLinkExists("Sign in");
			super.request("/company/practicum/show", param);
			super.checkPanicExists();

			super.signIn("company2", "company2");
			super.request("/company/practicum/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum/show", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
