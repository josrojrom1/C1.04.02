
package acme.features.student.enrolment;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;
import acme.utility.SpamDetector;

@Service
public class StudentEnrolmentFinaliseService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository	repository;
	@Autowired
	protected SpamDetector					textValidator;

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
		status = enrolment != null && !enrolment.getIsFinalised() && super.getRequest().getPrincipal().hasRole(enrolment.getStudent()) && enrolment.getStudent().getId() == super.getRequest().getPrincipal().getActiveRoleId();

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
		final String cvc = this.getRequest().getData("cvc", String.class);
		final String upperNibble = this.getRequest().getData("upperNibble", String.class);
		final String lowerNibble = this.getRequest().getData("lowerNibble", String.class);

		final Boolean isCCHAccepted = cch != null && cch != "";
		final Boolean isExpiryDateAccepted = expiryDate != null;
		final Boolean isCCVAccepted = cvc != null;
		final boolean isUpperNibbleAccepted = upperNibble != null;
		final boolean isLowerNibbleAccepted = lowerNibble != null;

		super.state(isExpiryDateAccepted, "expiryDate", "authentication.note.form.error.notAccepted");
		super.state(isLowerNibbleAccepted, "lowerNibble", "authentication.note.form.error.finalisationError");
		super.state(isCCHAccepted, "creditCardHolder", "authentication.note.form.error.finalisationError");
		super.state(isCCVAccepted, "cvc", "authentication.note.form.error.notAccepted");
		super.state(isUpperNibbleAccepted, "upperNibble", "authentication.note.form.error.notAccepted");

		if (!super.getBuffer().getErrors().hasErrors("creditCardHolder")) {
			String validar;
			validar = object.getCreditCardHolder();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "*", "student.enrolment.form.error.spam");

		}

		if (!super.getBuffer().getErrors().hasErrors("cvc")) {
			super.state(String.valueOf(cvc).length() == 3, "cvc", "authentication.note.form.error.notAccepted");
			super.state(this.canConvertToInt(cvc), "cvc", "authentication.note.form.error.notAccepted");
		}

		if (!super.getBuffer().getErrors().hasErrors("upperNibble")) {
			super.state(String.valueOf(upperNibble).length() == 8, "upperNibble", "authentication.note.form.error.notAccepted");
			super.state(this.canConvertToInt(upperNibble), "upperNibble", "authentication.note.form.error.notAccepted");
		}

		if (!super.getBuffer().getErrors().hasErrors("lowerNibble")) {
			super.state(String.valueOf(lowerNibble).length() == 4, "lowerNibble", "authentication.note.form.error.notAccepted");
			super.state(this.canConvertToInt(lowerNibble), "lowerNibble", "authentication.note.form.error.notAccepted");
		}

		if (!super.getBuffer().getErrors().hasErrors("expiryDate"))
			super.state(expiryDate.after(MomentHelper.getCurrentMoment()), "expiryDate", "authentication.note.form.error.notAccepted");

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

		tuple = super.unbind(object, "code", "motivation", "goals", "course", "creditCardHolder", "lowerNibble", "isFinalised");

		super.getResponse().setData(tuple);
	}

	public boolean canConvertToInt(final String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (final NumberFormatException e) {
			return false;
		}
	}

}
