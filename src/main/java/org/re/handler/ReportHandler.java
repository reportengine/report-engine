package org.re.handler;

import java.util.List;

import javax.validation.Valid;

import org.re.model.entity.ReportConfig;
import org.re.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportHandler {

    @Autowired
    private ReportService reportService;

    @PostMapping
    public void add(@Valid @RequestBody ReportConfig reportConfig) {
        reportService.insert(reportConfig);
    }

    @GetMapping
    public List<ReportConfig> getAll(
            @RequestParam(name = "detailed", required = false, defaultValue = "false") Boolean detailed) {
        return reportService.getAll(detailed);
    }

    @GetMapping("/{id}")
    public ReportConfig get(@PathVariable("id") String id) {
        return reportService.get(id);
    }

    @DeleteMapping("/{suiteId}")
    public void delete(@PathVariable("id") String id) {
        reportService.delete(id);
    }

}
