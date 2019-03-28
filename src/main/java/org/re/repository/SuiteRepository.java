package org.re.repository;

import java.util.List;

import org.re.model.entity.Suite;
import org.re.repository.custom.ISuiteRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuiteRepository extends MongoRepository<Suite, String>, ISuiteRepositoryCustom {

    public List<Suite> findByName(String name);

    public List<Suite> findByType(String type);

}
