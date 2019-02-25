package org.reportengine.handler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.reportengine.model.entity.Suite;
import org.reportengine.service.SuiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/suites")
public class SuiteHandler {

    @Autowired
    private SuiteService suiteService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public void add(@Valid @RequestBody Suite suite) {
        suiteService.insert(suite);
    }

    @PutMapping
    public void update(@Valid @RequestBody Suite suite) {
        suiteService.update(suite);
    }

    @GetMapping
    public List<Suite> get(@RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(name = "labels", required = false, defaultValue = "{}") String stringLabels)
            throws JsonParseException, JsonMappingException, IOException {
        @SuppressWarnings("unchecked")
        Map<String, String> labels = objectMapper.readValue(stringLabels, Map.class);
        return suiteService.getAll(name, type, labels);
    }

    @GetMapping("/{uid}")
    public Optional<Suite> get(@PathVariable("uid") String uid) {
        return suiteService.get(uid);
    }

}
