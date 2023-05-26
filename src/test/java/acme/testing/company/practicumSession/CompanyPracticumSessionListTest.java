
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumSessionListTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordIndex, final String title, final String practicum) {
		//este test comprueba el listado de las sessiones de un practicum de una company
		super.signIn("company2", "company2");
		super.clickOnMenu("Company", "List my practica");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List sessions");

		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, practicum);
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		Collection<Practicum> practicums;
		String param;
		practicums = this.repository.findManyPracticumsByCompany("company2");
		for (final Practicum practicum : practicums) {

			param = String.format("masterId=%d", practicum.getId());
			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/list", param);
			super.checkPanicExists();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum-session/list", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
