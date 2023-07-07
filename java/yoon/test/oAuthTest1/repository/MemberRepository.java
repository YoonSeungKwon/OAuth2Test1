package yoon.test.oAuthTest1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yoon.test.oAuthTest1.entity.Members;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Members, Long> {

    Optional<Members> findByEmail(String email);

}
