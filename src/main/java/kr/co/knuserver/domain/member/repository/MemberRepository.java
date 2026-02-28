package kr.co.knuserver.domain.member.repository;

import kr.co.knuserver.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.role = 'ADMIN'")
    Optional<Member> findAdmin();

    Optional<Member> findByLoginId(String loginId);
}
