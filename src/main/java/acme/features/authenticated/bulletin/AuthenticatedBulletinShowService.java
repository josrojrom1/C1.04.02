
package acme.features.authenticated.bulletin;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Bulletin;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedBulletinShowService extends AbstractService<Authenticated, Bulletin> {

	@Autowired
	protected AuthenticatedBulletinRepository repository;


	//Check method determines if the request has the required input data
	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);

	}

	//Authorise method determines if the request is legal or not
	@Override
	public void authorise() {
		final boolean status;
		final int id;
		final Bulletin bulletin;
		final Date deadLine;

		id = super.getRequest().getData("id", int.class);
		bulletin = this.repository.findOneBulletinById(id);
		deadLine = MomentHelper.deltaFromCurrentMoment(-1, ChronoUnit.SECONDS);
		//Comprobamos que el moment del bulletin esta antes que un seg menos de la fecha actual
		status = MomentHelper.isBefore(bulletin.getMoment(), deadLine);

		super.getResponse().setAuthorised(status);
	}

	//Load method stores in a buffer the retrieved request
	@Override
	public void load() {

		final Bulletin object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBulletinById(id);

		super.getBuffer().setData(object);

	}

	//Unbind method transforms data into tuples that are fed into the response
	@Override
	public void unbind(final Bulletin object) {

		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "moment", "title", "message", "flag", "link");
		super.getResponse().setData(tuple);

	}

}
