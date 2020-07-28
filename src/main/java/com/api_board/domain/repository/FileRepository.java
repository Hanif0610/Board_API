package com.api_board.domain.repository;

import com.api_board.domain.entity.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Integer> {
}
