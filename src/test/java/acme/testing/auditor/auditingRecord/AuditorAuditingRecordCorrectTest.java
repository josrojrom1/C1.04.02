
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordCorrectTest extends TestHarness {

	@Autowired
	AuditorAuditingRecordTestRepository repository;

	//	@ParameterizedTest
	//	@CsvFileSource(resources = "/auditor/auditingRecord/correct-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	public void test100Positive(final int recordIndex, final String subject, final String assessment, final String startPeriod, final String endPeriod, final String mark, final String link, final String correction) {
	//		//este test comprueba la creacion de los registros de una auditoria de un auditor
	//		super.signIn("auditor1", "auditor1");
	//		super.clickOnMenu("Auditor", "List my audits");
	//		super.sortListing(0, "asc");
	//		super.clickOnListingRecord(recordIndex);
	//		super.clickOnButton("Add Correction");
	//
	//		super.fillInputBoxIn("subject", subject);
	//		super.fillInputBoxIn("assessment", assessment);
	//		super.fillInputBoxIn("startPeriod", startPeriod);
	//		super.fillInputBoxIn("endPeriod", endPeriod);
	//		super.fillInputBoxIn("mark", mark);
	//		super.fillInputBoxIn("link", link);
	//		super.fillInputBoxIn("confirm", correction);
	//
	//		super.clickOnSubmit("Add correct");
	//
	//		super.clickOnMenu("Auditor", "List my audits");
	//		super.sortListing(0, "asc");
	//		super.clickOnListingRecord(recordIndex);
	//		super.clickOnButton("Auditing records");
	//		super.sortListing(0, "asc");
	//		super.checkColumnHasValue(recordIndex, 0, subject);
	//		super.checkColumnHasValue(recordIndex, 1, mark);
	//
	//		super.signOut();
	//	}


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/correct-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String subject, final String assessment, final String startPeriod, final String endPeriod, final String mark, final String link, final String correction) {
		//este test comprueba la creacion de los registros de una auditoria de un auditor
		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Auditor", "List my audits");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("Add Correction");

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("confirm", correction);

		super.clickOnSubmit("Add correct");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<AuditingRecord> records;
		String param;
		records = this.repository.findManyAuditingRecordByAssistant("auditor1");

		for (final AuditingRecord record : records) {
			param = String.format("id=%d", record.getAudit().getId());
			super.checkLinkExists("Sign in");
			super.request("/auditor/auditing-record/correct", param);
			super.checkPanicExists();

			super.signIn("auditor2", "auditor2");
			super.request("/auditor/auditing-record/correct", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/auditor/auditing-record/correct", param);
			super.checkPanicExists();
			super.signOut();

		}

	}

}
