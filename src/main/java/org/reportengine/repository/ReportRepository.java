package org.reportengine.repository;

import java.util.List;
import java.util.Optional;

import org.reportengine.model.entity.ReportConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ReportRepository extends MongoRepository<ReportConfig, String> {

    Optional<ReportConfig> findByName(String name);

    List<ReportConfig> findByType(String type);

    @Query(value="{}", fields = "{'id': 1, 'name': 1, 'type': 1}")
    List<ReportConfig> findAllLessInfo();

}
