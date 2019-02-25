package org.reportengine.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.reportengine.model.entity.Suite;
import org.reportengine.repository.SuiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SuiteService {

    @Autowired
    private SuiteRepository suiteRepository;

    public void insert(Suite suite) {
        suite.setCreatedAt(System.currentTimeMillis());
        suiteRepository.save(suite);
    }

    public void update(Suite suite) {
        suiteRepository.save(suite);
    }

    public Optional<Suite> get(String id) {
        return suiteRepository.findById(id);
    }

    public List<Suite> getAll(String type, Map<String, String> labels) {
        return getAll(null, type, labels, null);
    }

    public List<Suite> getAll(String name, String type, Map<String, String> labels) {
        return getAll(name, type, labels, null);
    }
    
    public List<Suite> getAll(String type, Map<String, String> labels, Boolean ready) {
        return getAll(null, type, labels, ready);
    }

    public List<Suite> getAll(String name, String type, Map<String, String> labels, Boolean ready) {
        _logger.debug("Query data[ready:{}, name:{}, type:{}, labels:{}]", ready, name, type, labels);

        if (name != null) {
            return suiteRepository.findByName(name);
        } else if (type != null) {
            if (labels.isEmpty()) {
                return suiteRepository.findByType(type);
            } else {
                return suiteRepository.findAll(ready, type, labels);
            }
        } else if (!labels.isEmpty()) {
            return suiteRepository.findAll(labels);
        } else {
            return suiteRepository.findAll();
        }
    }
}
