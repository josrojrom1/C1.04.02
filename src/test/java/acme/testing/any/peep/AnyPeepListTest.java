
package acme.testing.any.peep;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyPeepListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/list-all-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String nick, final String moment, final String title) {
		//Rol Anonymous;
		super.clickOnMenu("Peeps", "List of Peeps");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, moment);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, nick);
	}
	//No se puede hacer test negativo en los tests

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/list-all-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Positive(final int recordIndex, final String nick, final String moment, final String title) {
		//Rol Lecturer
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Peeps", "List of Peeps");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, moment);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, nick);
		super.signOut();
	}

}
