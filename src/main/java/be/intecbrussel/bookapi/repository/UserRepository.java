package be.intecbrussel.bookapi.repository;

import be.intecbrussel.bookapi.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AuthUser, String> {
}
