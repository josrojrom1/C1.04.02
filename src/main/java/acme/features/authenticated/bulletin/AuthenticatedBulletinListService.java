
package acme.features.authenticated.bulletin;

import org.springframework.stereotype.Service;

import acme.entities.Bulletin;
import acme.framework.components.accounts.Authenticated;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedBulletinListService extends AbstractService<Authenticated, Bulletin> {

}
