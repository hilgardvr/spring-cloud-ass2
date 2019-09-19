package org.magnum.mobilecloud.video.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends CrudRepository<Video, Long> {
    List<Video> findAll();

    Video findById(long id);

    List<Video> findByName(String name);

    List<Video> findByDuration(long duration);
}
