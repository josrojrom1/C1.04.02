
package acme.testing.lecturer.course;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class LecturerCoursePublishTest extends TestHarness {

	@Autowired
	protected LecturerCourseTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex) {
		// HINT: En este test nos autenticamos como Lecturer, listamos los courses, seleccionamos uno de ellos y lo publicamos
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "List my courses");
		super.checkListingExists();
		super.clickOnListingRecord(1);
		super.checkFormExists();
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex) {
		// HINT: En este test intentamos publicar un course el cual aun no puede ser publicado, en este caso porque no tiene lectures
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "List my courses");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.checkNotButtonExists("Publish");
		super.checkNotErrorsExist();
		super.signOut();
	}
	//
	//	@Test
	//	public void test300Hacking() {
	//		// HINT: this test tries to publish a job with a role other than "Employer".
	//
	//		Collection<Job> jobs;
	//		String params;
	//
	//		jobs = this.repository.findManyJobsByEmployerUsername("employer1");
	//		for (final Job job : jobs)
	//			if (job.isDraftMode()) {
	//				params = String.format("id=%d", job.getId());
	//
	//				super.checkLinkExists("Sign in");
	//				super.request("/employer/job/publish", params);
	//				super.checkPanicExists();
	//
	//				super.signIn("administrator", "administrator");
	//				super.request("/employer/job/publish", params);
	//				super.checkPanicExists();
	//				super.signOut();
	//
	//				super.signIn("worker1", "worker1");
	//				super.request("/employer/job/publish", params);
	//				super.checkPanicExists();
	//				super.signOut();
	//			}
	//	}
	//
	//	@Test
	//	public void test301Hacking() {
	//		// HINT: this test tries to publish a published job that was registered by the principal.
	//
	//		Collection<Job> jobs;
	//		String params;
	//
	//		super.signIn("employer1", "employer1");
	//		jobs = this.repository.findManyJobsByEmployerUsername("employer1");
	//		for (final Job job : jobs)
	//			if (!job.isDraftMode()) {
	//				params = String.format("id=%d", job.getId());
	//				super.request("/employer/job/publish", params);
	//			}
	//		super.signOut();
	//	}
	//
	//	@Test
	//	public void test302Hacking() {
	//		// HINT: this test tries to publish a job that wasn't registered by the principal,
	//		// HINT+ be it published or unpublished.
	//
	//		Collection<Job> jobs;
	//		String params;
	//
	//		super.signIn("employer2", "employer2");
	//		jobs = this.repository.findManyJobsByEmployerUsername("employer1");
	//		for (final Job job : jobs) {
	//			params = String.format("id=%d", job.getId());
	//			super.request("/employer/job/publish", params);
	//		}
	//		super.signOut();
	//	}

}
