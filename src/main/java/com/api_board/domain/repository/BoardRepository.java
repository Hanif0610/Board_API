package com.api_board.domain.repository;

import com.api_board.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findAllByOrderByCreateDateAsc();
    Optional<Board> findById(Integer id);
    void deleteById(Integer id);
}
