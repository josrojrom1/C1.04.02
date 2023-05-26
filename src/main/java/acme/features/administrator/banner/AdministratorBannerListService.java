
package acme.features.administrator.banner;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerListService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository repository;


	@Override
	public void check() {
		//		boolean status;
		//		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Administrator.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Banner> objects;
		objects = this.repository.findAllBanner();
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "moment", "webLink", "slogan");

		super.getResponse().setData(tuple);

	}
}
