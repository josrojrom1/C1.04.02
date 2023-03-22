
package acme.features.authenticated.bulletin;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Bulletin;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedBulletinListService extends AbstractService<Authenticated, Bulletin> {

	@Autowired
	protected AuthenticatedBulletinRepository repository;


	@Override
	public void authorise() {
		boolean status = true;
		final int id;
		final Collection<Bulletin> bulletins;
		final Date deadLine;

		id = super.getRequest().getData("id", int.class);
		bulletins = this.repository.findAllBulletinsById(id);
		deadLine = MomentHelper.deltaFromCurrentMoment(-1, ChronoUnit.SECONDS);

		for (final Bulletin b : bulletins) {
			//Comprobamos que el moment del bulletin esta antes que un seg menos de la fecha actual
			status = MomentHelper.isBefore(b.getMoment(), deadLine);
			if (status == false)
				break;

		}
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void unbind(final Bulletin object) {

		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "moment", "title", "message", "flag", "link");
		super.getResponse().setData(tuple);

	}
}
