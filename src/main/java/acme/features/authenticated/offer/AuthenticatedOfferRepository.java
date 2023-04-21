
package acme.features.authenticated.offer;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Offer;
import acme.entities.configuration.Configuration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedOfferRepository extends AbstractRepository {

	@Query("select o from Offer o where o.id = :id")
	Offer findOneOfferById(int id);

	@Query("select o from Offer o")
	Collection<Offer> findAllOffers();

	@Query("SELECT o from Offer o where o.moment >= :deadline")
	Collection<Offer> findRecentOffers(Date deadline);

	@Query("SELECT c FROM Configuration c")
	Configuration findConfiguration();

}
