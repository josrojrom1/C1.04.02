
package acme.features.authenticated.note;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.utility.SpamDetector;

@Service
public class AuthenticatedNoteCreateService extends AbstractService<Authenticated, Note> {

	@Autowired
	protected AuthenticatedNoteRepository	repository;

	@Autowired
	protected SpamDetector					textValidator;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Authenticated.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Note object;
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		object = new Note();
		object.setTitle("");
		object.setMoment(moment);
		object.setMessage("");
		object.setAuthor("");
		object.setEmail("");
		object.setLink("");

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Note object) {
		assert object != null;

		super.bind(object, "moment", "title", "author", "message", "email", "link");
	}

	@Override
	public void validate(final Note object) {
		assert object != null;

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			String validar;
			validar = object.getTitle();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "title", "assistant.tutorial.form.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("message")) {
			String validar;
			validar = object.getMessage();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "message", "assistant.tutorial.form.error.spam");
		}
	}

	@Override
	public void perform(final Note object) {
		assert object != null;

		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Note object) {
		assert object != null;
		Tuple tuple;
		Principal principal;
		principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		final UserAccount userAccount = this.repository.findUserAccountById(userAccountId);
		final String authorName = userAccount.getIdentity().getName();
		final String authorSurname = userAccount.getIdentity().getSurname();
		final String authorUsername = userAccount.getUsername();
		final String authorFinalFormat = "<" + authorUsername + "> - <" + authorSurname + ", " + authorName + ">";

		tuple = super.unbind(object, "moment", "title", "author", "message", "email", "link");
		tuple.put("author", authorFinalFormat);
		tuple.put("confirmation", false);
		tuple.put("readonly", false);

		super.getResponse().setData(tuple);
	}

}
