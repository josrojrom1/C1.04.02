
package acme.features.administrator.banner;

import java.time.Duration;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.utility.SpamDetector;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository	repository;

	@Autowired
	protected SpamDetector					textValidator;

	// AbstractService interface ----------------------------------------------


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
		Banner object;
		Date moment;
		moment = MomentHelper.getCurrentMoment();

		object = new Banner();
		object.setMoment(moment);
		object.setStartDisplayPeriod(moment);
		object.setEndDisplayPeriod(moment);
		object.setPictureLink("");
		object.setWebLink("");
		object.setSlogan("");

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
		if (!super.getBuffer().getErrors().hasErrors("startDisplayPeriod"))
			super.state(object.getStartDisplayPeriod().after(object.getMoment()), "startDisplayPeriod", "administrator.banner.form.error.date");

		if (!super.getBuffer().getErrors().hasErrors("endDisplayPeriod"))
			super.state(Duration.between(object.getStartDisplayPeriod().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), object.getEndDisplayPeriod().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()).toDays() >= 7,
				"endDisplayPeriod", "administrator.banner.form.error.date");

		if (!super.getBuffer().getErrors().hasErrors("slogan")) {
			String validar;
			validar = object.getSlogan();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "slogan", "administrator.banner.error.spam");
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "moment", "startDisplayPeriod", "endDisplayPeriod", "pictureLink", "webLink", "slogan");

		super.getResponse().setData(tuple);
	}

}
