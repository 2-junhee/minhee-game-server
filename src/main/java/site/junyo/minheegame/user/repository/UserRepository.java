package site.junyo.minheegame.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.junyo.minheegame.user.domain.User;


public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.uuid = :username")
    User findByUuid(@Param("username") String username);


}
