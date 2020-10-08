package com.api_board.domain.repository;

import com.api_board.domain.entity.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends CrudRepository<Image, Integer> {

    List<Image> findByBoardId(Integer boardId);
    void deleteByBoardId(Integer boardId);
}
