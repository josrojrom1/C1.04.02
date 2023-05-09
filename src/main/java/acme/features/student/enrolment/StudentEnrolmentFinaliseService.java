
package acme.features.student.enrolment;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentFinaliseService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Enrolment enrolment;

		id = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(id);
		status = !enrolment.getIsFinalised() && super.getRequest().getPrincipal().hasRole(enrolment.getStudent());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Enrolment enrolment = this.repository.findEnrolmentById(id);
		super.getBuffer().setData(enrolment);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		super.bind(object, "creditCardHolder", "lowerNibble");

	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		final String cch = this.getRequest().getData("creditCardHolder", String.class);
		final Date expiryDate = this.getRequest().getData("expiryDate", Date.class);
		final String ccv = this.getRequest().getData("cvc", String.class);
		final String upperNibble = this.getRequest().getData("upperNibble", String.class);
		final String lowerNibble = this.getRequest().getData("lowerNibble", String.class);

		final Boolean isCCHAccepted = cch != null;
		final Boolean isExpiryDateAccepted = expiryDate != null;
		final Boolean isCCVAccepted = ccv != null;
		final boolean isUpperNibbleAccepted = upperNibble != null;
		final boolean isLowerNibbleAccepted = lowerNibble != null;

		super.state(ccv.length() == 3, "creditCardHolder", "authentication.note.form.error.notAccepted");
		super.state(upperNibble.length() == 8, "upperNibble", "authentication.note.form.error.notAccepted");

		super.state(isCCHAccepted, "creditCardHolder", "authentication.note.form.error.finalisationError");
		super.state(isExpiryDateAccepted, "expiryDate", "authentication.note.form.error.notAccepted");
		super.state(isCCVAccepted, "cvc", "authentication.note.form.error.notAccepted");
		super.state(isLowerNibbleAccepted, "lowerNibble", "authentication.note.form.error.finalisationError");
		super.state(isUpperNibbleAccepted, "upperNibble", "authentication.note.form.error.notAccepted");

	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;
		object.setIsFinalised(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "motivation", "goals", "workTime", "course", "creditCardHolder", "lowerNibble", "isFinalised");

		super.getResponse().setData(tuple);
	}

}
