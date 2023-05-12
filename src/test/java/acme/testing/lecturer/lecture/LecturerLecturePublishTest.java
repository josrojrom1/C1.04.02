
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Course;
import acme.testing.TestHarness;

public class LecturerLecturePublishTest extends TestHarness {

	@Autowired
	protected LecturerLectureTestRepository repository;

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

	@Test
	public void test300Hacking() {
		// HINT: this test tries to publish a course with a role other than "Employer".

		final Collection<Course> courses;
		String params;

		courses = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course course : courses)
			if (course.isDraftMode()) {
				params = String.format("id=%d", course.getId());

				super.checkLinkExists("Sign in");
				super.request("/lecturer/course/publish", params);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/lecturer/course/publish", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/lecturer/course/publish", params);
				super.checkPanicExists();
				super.signOut();
			}
	}

	@Test
	public void test301Hacking() {
		// HINT: En este test intentamos publicar un curso ya publicado por otro Lecturer distinto.

		Collection<Course> courses;
		String params;

		super.signIn("lecturer1", "lecturer1");
		courses = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course course : courses)
			if (!course.isDraftMode()) {
				params = String.format("id=%d", course.getId());
				super.request("/lecturer/course/publish", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {
		// HINT: En este test intentamos publicar un curso que no pertenece al lecturer

		Collection<Course> courses;
		String params;

		super.signIn("lecturer2", "lecturer2");
		courses = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course course : courses) {
			params = String.format("id=%d", course.getId());
			super.request("/lecturer/course/publish", params);
		}
		super.signOut();
	}

}
