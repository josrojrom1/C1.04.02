
package acme.features.administrator.offer;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Offer;
import acme.entities.configuration.Configuration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorOfferRepository extends AbstractRepository {

	@Query("select o from Offer o where o.id = :id")
	Offer findOneOfferById(int id);

	@Query("select o from Offer o")
	Collection<Offer> findAllOffers();

	@Query("SELECT c FROM Configuration c")
	Configuration findConfiguration();
}
