package org.reportengine.service;

import java.util.List;
import java.util.Optional;

import org.reportengine.exception.ResourceNotFoundException;
import org.reportengine.model.entity.ReportConfig;
import org.reportengine.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public void insert(ReportConfig reportConfig) {
        reportRepository.save(reportConfig);
    }

    public List<ReportConfig> getAll(boolean detailed) {
        if (detailed) {
            return reportRepository.findAll();
        } else {
            return reportRepository.findAllLessInfo();
        }
    }

    public ReportConfig get(String idOrName) {
        Optional<ReportConfig> config = reportRepository.findById(idOrName);
        if (config.isPresent()) {
            return config.get();
        } else {
            return reportRepository.findByName(idOrName).orElseThrow(
                    () -> new ResourceNotFoundException("report", "id", idOrName));
        }

    }
}
