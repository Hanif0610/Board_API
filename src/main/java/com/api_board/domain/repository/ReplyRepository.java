package com.api_board.domain.repository;

import com.api_board.domain.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    Reply findByRno(Integer rno);
    Optional<Reply> deleteByRno(Integer rno);
}
