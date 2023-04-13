
package acme.features.authenticated.note;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteListService extends AbstractService<Authenticated, Note> {

	@Autowired
	protected AuthenticatedNoteRepository repository;


	@Override
	public void check() {

		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {

		boolean status;
		int id;
		Note note;
		Date deadline;

		id = super.getRequest().getData("id", int.class);
		note = this.repository.findOneNoteById(id);
		deadline = MomentHelper.deltaFromCurrentMoment(-30, ChronoUnit.DAYS);
		status = MomentHelper.isAfter(note.getMoment(), deadline);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {

		Collection<Note> objects;

		objects = this.repository.findAllNotes();
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Note object) {

		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "moment", "title", "author", "message", "email", "link");
		super.getResponse().setData(tuple);

	}
}
