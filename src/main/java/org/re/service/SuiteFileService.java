package org.re.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.re.exception.FileStorageException;
import org.re.exception.MyFileNotFoundException;
import org.re.model.entity.Suite;
import org.re.properties.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SuiteFileService {

    private final Path fileStorageLocation;

    @Autowired
    private SuiteService suiteService;

    @Autowired
    public SuiteFileService(FileStorageProperties fileProperties) {
        this.fileStorageLocation = Paths.get(fileProperties.getUploadRoot(), fileProperties.getSuiteLogs())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }
    }

    public String storeFile(MultipartFile file, String suiteId) {
        validateSuite(suiteId, true);
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        fileName = fileName.replaceAll("[^a-zA-Z0-9_\\.\\-]", "_");

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(suiteId).resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName, String suiteId) {
        validateSuite(suiteId, false);
        try {
            Path filePath = this.fileStorageLocation.resolve(suiteId).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public List<String> list(String suiteId) {
        validateSuite(suiteId, false);
        Path filePath = fileStorageLocation.resolve(suiteId).normalize();
        try {

            List<Path> files = Files.walk(filePath)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());

            List<String> fileNames = new ArrayList<>();
            for (Path file : files) {
                fileNames.add(file.getFileName().toString());
            }
            return fileNames;
        } catch (IOException ex) {
            _logger.trace("File not found, file:{}", filePath.getFileName().toString(), ex);
            return new ArrayList<>();
        }
    }

    public Map<String, Object> delete(String suiteId) {
        validateSuite(suiteId, false);
        List<String> success = new ArrayList<>();
        Map<String, Object> failure = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("failure", failure);

        Path filePath = fileStorageLocation.resolve(suiteId).normalize();
        if (filePath.toFile().exists()) {
            try {

                List<Path> files = Files.walk(filePath)
                        .filter(Files::isRegularFile)
                        .collect(Collectors.toList());

                for (Path file : files) {
                    try {
                        if (file.toFile().delete()) {
                            success.add(file.getFileName().toString());
                        } else {
                            failure.put("name", file.getFileName().toString());
                        }
                    } catch (Exception ex) {
                        failure.put("name", file.getFileName().toString());
                        failure.put("error", ex.getMessage());
                        _logger.error("Exception,", ex);
                    }
                }
                // delete directory
                if (!filePath.toFile().delete()) {
                    _logger.warn("Failed to delete a dir:{}", filePath.getFileName().toString());
                }
            } catch (IOException ex) {
                throw new MyFileNotFoundException("File not found " + filePath, ex);
            }
        }
        return result;
    }

    private void validateSuite(String suiteId, boolean create) {
        Optional<Suite> suite = suiteService.get(suiteId);
        if (suite.isPresent()) {
            if (create) {
                try {
                    Files.createDirectories(this.fileStorageLocation.resolve(suiteId));
                } catch (IOException ex) {
                    _logger.error("Unable to create suite directoy! {}/{}", fileStorageLocation.toString(), suiteId,
                            ex);
                    throw new RuntimeException(String.format("Unable to create suite directoy! %s/%s",
                            fileStorageLocation.toString(), suiteId));
                }
            }
        } else {
            throw new RuntimeException("Specified ID[" + suiteId + "] not found in suites list");
        }
    }
}
