
package acme.features.authenticated.bulletin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Bulletin;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedBulletinListService extends AbstractService<Authenticated, Bulletin> {

	@Autowired
	protected AuthenticatedBulletinRepository repository;


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

		final Collection<Bulletin> objects;

		//id = super.getRequest().getData("id", int.class);
		objects = this.repository.findAllBulletins();

		super.getBuffer().setData(objects);

	}

	@Override
	public void unbind(final Bulletin object) {
		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "moment", "title", "message", "flag", "link");
		super.getResponse().setData(tuple);

	}
}
