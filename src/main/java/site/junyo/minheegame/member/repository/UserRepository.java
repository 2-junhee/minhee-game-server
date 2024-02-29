package site.junyo.minheegame.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.junyo.minheegame.member.domain.User;


public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.id = :username")
    User findByUsername(@Param("username") String username);

}
