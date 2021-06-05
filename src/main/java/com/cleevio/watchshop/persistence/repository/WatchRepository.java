package com.cleevio.watchshop.persistence.repository;

import com.cleevio.watchshop.persistence.entity.Watch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WatchRepository extends CrudRepository<Watch, UUID> {
    List<Watch> findAll();
    List<Watch> findByTitle(String title);
}
