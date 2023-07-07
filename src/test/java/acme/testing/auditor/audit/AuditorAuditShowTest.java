
package acme.testing.auditor.audit;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Audit;
import acme.testing.TestHarness;

public class AuditorAuditShowTest extends TestHarness {

	@Autowired
	protected AuditorAuditTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordIndex, final String code, final String conclusion, final String weakPoints, final String strongPoints, final String mark, final String course) {
		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Auditor", "List my audits");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("conclusion", conclusion);
		super.checkInputBoxHasValue("weakPoints", weakPoints);
		super.checkInputBoxHasValue("strongPoints", strongPoints);
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("mark", mark);
		super.signOut();
	}

	@Test
	public void test300hacking() {
		// HINT: this test tries to show tutorials to unauthorised principals, like anonymous or other assistants
		Collection<Audit> audits;
		String param;
		audits = this.repository.findManyAuditsFromUsername("auditor1");
		for (final Audit audit : audits) {

			param = String.format("id=%d", audit.getId());
			super.checkLinkExists("Sign in");
			super.request("/auditor/audit/show", param);
			super.checkPanicExists();

			super.signIn("assistant2", "assistant2");
			super.request("/auditor/audit/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/auditor/audit/show", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
