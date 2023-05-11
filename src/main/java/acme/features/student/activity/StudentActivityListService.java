
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Activity;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityListService extends AbstractService<Student, Activity> {

	@Autowired
	StudentActivityRepository repository;


	@Override
	public void check() {

		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Student.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Principal principal;
		Collection<Activity> activities;

		principal = super.getRequest().getPrincipal();
		activities = this.repository.findAllActivitiesFromStudentId(principal.getActiveRoleId());

		super.getBuffer().setData(activities);
	}

	@Override

	public void unbind(final Activity object) {
		assert object != null;
		Tuple tuple;
		final boolean showCreate;

		showCreate = object.getEnrolment().getIsFinalised() && super.getRequest().getPrincipal().hasRole(Student.class);

		super.getResponse().setGlobal("showCreate", showCreate);
		tuple = super.unbind(object, "title", "abst");

		tuple.put("enrolment", object.getEnrolment().getCode());
		tuple.put("showCreate", showCreate);

		super.getResponse().setData(tuple);
	}

}
