
package acme.features.authenticated.note;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
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

		super.getResponse().setAuthorised(true);

	}

	@Override
	public void load() {

		final Collection<Note> objects;

		objects = this.repository.findAllNotes();
		super.getResponse().setData(objects);
	}

	@Override
	public void unbind(final Note object) {

		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "moment", "title", "author", "message", "email", "link");
		super.getResponse().setData(tuple);

	}
}
