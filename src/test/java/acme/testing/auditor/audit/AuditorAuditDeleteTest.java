
package acme.testing.auditor.audit;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Audit;
import acme.testing.TestHarness;

public class AuditorAuditDeleteTest extends TestHarness {

	@Autowired
	protected AuditorAuditTestRepository repository;


	@Test
	public void test100Positive() {
		super.signIn("auditor2", "auditor2");
		super.clickOnMenu("Auditor", "List my audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();
		super.signOut();
	}
	@Test
	public void test300Hacking() {
		Collection<Audit> audits;
		String param;
		audits = this.repository.findManyAuditsFromUsername("auditor2");
		for (final Audit audit : audits) {
			param = String.format("id=%d", audit.getId());
			super.checkLinkExists("Sign in");
			super.request("/auditor/audit/delete", param);
			super.checkPanicExists();

			super.signIn("auditor1", "auditor1");
			super.request("/assistant/audit/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/audit/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
