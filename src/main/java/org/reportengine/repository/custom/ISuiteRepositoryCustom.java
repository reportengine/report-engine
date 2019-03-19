package org.reportengine.repository.custom;

import java.util.List;
import java.util.Map;

import org.reportengine.model.entity.Suite;

public interface ISuiteRepositoryCustom {

    public List<Suite> findAll(Map<String, String> labels);

    public List<Suite> findAll(String type, Map<String, String> labels);

    public List<Suite> findAll(String name, String type, Boolean ready, Map<String, String> labels);
}
