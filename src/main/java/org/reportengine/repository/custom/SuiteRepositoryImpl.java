package org.reportengine.repository.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.reportengine.model.entity.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SuiteRepositoryImpl implements ISuiteRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Suite> findAll(Map<String, String> labels) {
        return findAll(null, null, null, labels);
    }

    public List<Suite> findAll(String type, Map<String, String> labels) {
        return findAll(null, type, null, labels);
    }

    public List<Suite> findAll(String name, String type, Boolean ready, Map<String, String> labels) {
        _logger.debug("Input data[type:{}, labels:{}]", type, labels);
        List<Criteria> criteriaList = new ArrayList<>();

        // add labels condition
        if (!labels.isEmpty()) {
            for (String label : labels.keySet()) {
                criteriaList.add(Criteria.where("labels." + label).is(labels.get(label)));
            }
        }

        // add name condition
        if (name != null) {
            criteriaList.add(Criteria.where("name").is(name));
        }

        // add type condition
        if (type != null) {
            criteriaList.add(Criteria.where("type").is(type));
        }

        // add ready condition
        if (ready != null) {
            criteriaList.add(Criteria.where("ready").is(ready));
        }

        Query query = new Query();

        if (!criteriaList.isEmpty()) {
            // convert list to array
            Criteria[] criteriaArray = new Criteria[criteriaList.size()];
            criteriaList.toArray(criteriaArray);
            query.addCriteria(new Criteria().andOperator(criteriaArray));
        }

        query.with(new Sort(Direction.ASC, "createdAt"));

        _logger.debug("{}", query);
        return mongoTemplate.find(query, Suite.class);
    }
}
