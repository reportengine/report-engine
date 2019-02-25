package org.reportengine.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.reportengine.exception.FileStorageException;
import org.reportengine.exception.MyFileNotFoundException;
import org.reportengine.model.entity.Suite;
import org.reportengine.properties.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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

    public String storeFile(MultipartFile file, String id) {
        validateSuite(id);
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(id).resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName, String id) {
        validateSuite(id);
        try {
            Path filePath = this.fileStorageLocation.resolve(id).resolve(fileName).normalize();
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

    public List<String> list(String id) {
        validateSuite(id);
        Path filePath = fileStorageLocation.resolve(id).normalize();
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
            throw new MyFileNotFoundException("File not found " + filePath, ex);
        }
    }

    private void validateSuite(String id) {
        Optional<Suite> suite = suiteService.get(id);
        if (suite.isPresent()) {
            try {
                Files.createDirectories(this.fileStorageLocation.resolve(id));
            } catch (IOException ex) {
                _logger.error("Unable to create suite directoy! {}/{}", fileStorageLocation.toString(), id, ex);
                throw new RuntimeException(String.format("Unable to create suite directoy! %s/%s",
                        fileStorageLocation.toString(), id));
            }
        } else {
            throw new RuntimeException("Specified ID[" + id + "] not found in suites list");
        }
    }
}
