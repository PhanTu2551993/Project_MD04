package ra.project_md04.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ra.project_md04.model.entity.Users;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long>, PagingAndSortingRepository<Users,Long> {
    Optional<Users> findUsersByUsername(String username);
}
