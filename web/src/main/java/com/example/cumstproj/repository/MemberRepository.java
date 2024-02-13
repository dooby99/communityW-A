package com.example.cumstproj.repository;

import com.example.cumstproj.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByNameAndPw(String name, String pw);
    Optional<Member> findByName(String name);
}
