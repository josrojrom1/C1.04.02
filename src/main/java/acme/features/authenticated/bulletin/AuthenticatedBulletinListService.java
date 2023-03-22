
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


	//Check method determines if the request has the required input data
	@Override
	public void check() {
		//boolean status;
		//status = super.getRequest().hasData("id", int.class);
		//super.getResponse().setChecked(status);

		super.getResponse().setChecked(true);

	}

	@Override
	public void authorise() {
		//final boolean status;
		//final int id;
		//final Collection<Bulletin> bulletins;
		//final Date deadLine;

		//id = super.getRequest().getData("id", int.class);
		//bulletins = this.repository.findAllBulletinsById(id);
		//deadLine = MomentHelper.deltaFromCurrentMoment(-1, ChronoUnit.SECONDS);

		//for (final Bulletin b : bulletins) {
		//Comprobamos que el moment del bulletin esta antes que un seg menos de la fecha actual
		//status = MomentHelper.isBefore(b.getMoment(), deadLine);
		//if (status == false)
		//break;

		//}

		//status = !super.getRequest().getPrincipal().hasRole(Authenticated.class);

		//super.getResponse().setAuthorised(status);

		super.getResponse().setAuthorised(true);

	}

	@Override
	public void load() {

		final Collection<Bulletin> objects;
		final int id;

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
