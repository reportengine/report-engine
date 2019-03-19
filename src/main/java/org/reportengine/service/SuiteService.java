package org.reportengine.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.reportengine.model.DeleteSuites;
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

    @Autowired
    private MetricsService metricsService;

    @Autowired
    private SuiteFileService suiteFileService;

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
        if (name != null || type != null || (labels != null && !labels.isEmpty())) {
            return suiteRepository.findAll(name, type, ready, labels);
        } else {
            return suiteRepository.findAll();
        }
    }

    private Map<String, Object> deleteSuite(Suite suite) {
        Map<String, Object> result = suiteFileService.delete(suite.getId());
        suiteRepository.delete(suite);
        metricsService.delete(suite);
        return result;
    }

    public Map<String, Object> delete(String suiteId) {
        Optional<Suite> suite = get(suiteId);
        if (suite.isPresent()) {
            Map<String, Object> data = deleteSuite(suite.get());
            data.put("suiteId", suiteId);
            data.put("deleted", true);
            return data;
        }
        Map<String, Object> data = new HashMap<>();
        data.put("suiteId", suiteId);
        data.put("deleted", false);
        return data;
    }

    public List<Map<String, Object>> deleteAll(DeleteSuites suites) {
        if (suites.isValid()) {
            List<Map<String, Object>> result = new ArrayList<>();
            List<Suite> suiteList = getAll(suites.getName(), suites.getType(), suites.getLabels(), suites.getReady());
            for (Suite suite : suiteList) {
                Map<String, Object> data = deleteSuite(suite);
                result.add(data);
            }
            return result;
        }
        return new ArrayList<Map<String, Object>>();
    }
}
