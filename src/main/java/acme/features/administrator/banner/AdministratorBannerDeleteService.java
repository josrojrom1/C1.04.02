
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerDeleteService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Banner banner;
		int id;

		id = super.getRequest().getData("id", int.class);
		banner = this.repository.findOneBannerById(id);
		status = banner != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		Banner object;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "moment", "startDisplayPeriod", "endDisplayPeriod", "pictureLink", "webLink", "slogan");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "moment", "startDisplayPeriod", "endDisplayPeriod", "pictureLink", "webLink", "slogan");

		super.getResponse().setData(tuple);
	}

}
