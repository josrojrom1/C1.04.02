
package acme.features.authenticated.student;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface AuthenticatedStudentRepository extends AbstractRepository {

	@Query("SELECT ua FROM UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("SELECT s FROM Student s where s.id = :id")
	public Student findOneStudentById(int id);

	@Query("SELECT s FROM Student s WHERE s.userAccount.id = :id")
	Student findOneStudentByUserAccountId(int id);

}
