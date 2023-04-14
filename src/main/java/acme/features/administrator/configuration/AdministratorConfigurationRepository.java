
package acme.features.administrator.configuration;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.configuration.Configuration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorConfigurationRepository extends AbstractRepository {

	@Query("SELECT c FROM Configuration c WHERE c.id = :id")
	public Configuration findOneConfigurationById(int id);

	@Query("SELECT c FROM Configuration c")
	public Collection<Configuration> findAllConfiguration();

	@Query("SELECT c FROM Configuration c")
	public Configuration findConfiguration();
}
