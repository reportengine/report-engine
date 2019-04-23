package org.re.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.re.Tasks;
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
    private final Path fileTmpLocation;

    @Autowired
    private SuiteService suiteService;

    @Autowired
    public SuiteFileService(FileStorageProperties fileProperties) {
        this.fileStorageLocation = Paths.get(fileProperties.getUploadRoot(), fileProperties.getSuiteLogs())
                .toAbsolutePath().normalize();
        this.fileTmpLocation = Paths.get(fileProperties.getUploadRoot(), fileProperties.getTmp())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
            Files.createDirectories(this.fileTmpLocation);
        } catch (Exception ex) {
            throw new FileStorageException(
                    "Could not create the directory where the uploaded [or] tmp files will be stored.", ex);
        }
    }

    private Path getArchiveFilePath(String suiteId) {
        return this.fileStorageLocation.resolve(String.format("%s.zip", suiteId));
    }

    private String normalizeFileName(String fileName) {
        // Normalize file name
        String fileNameFinal = StringUtils.cleanPath(fileName);
        fileNameFinal = fileNameFinal.replaceAll("[^a-zA-Z0-9_\\.\\-]", "_");
        // Check if the file's name contains invalid characters
        if (fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        return fileNameFinal;
    }

    public String storeFile(MultipartFile file, String suiteId) {
        validateSuite(suiteId);
        // Normalize file name
        String fileName = normalizeFileName(file.getOriginalFilename());
        try {
            // create zip file and include this file
            Archive archive = new Archive(getArchiveFilePath(suiteId).toString(), fileTmpLocation);
            archive.addEntry(fileName, file.getBytes());
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName, String suiteId) {
        validateSuite(suiteId);
        Path targetPath = null;
        try {
            String fileNameFinal = normalizeFileName(fileName);
            Archive archive = new Archive(getArchiveFilePath(suiteId).toString(), fileTmpLocation);
            targetPath = this.fileTmpLocation.resolve(suiteId).resolve(fileNameFinal).normalize();
            archive.extractEntry(fileNameFinal, targetPath);
            Resource resource = new UrlResource(targetPath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (IOException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        } finally {
            if (targetPath != null && targetPath.toFile().exists()) {
                Tasks.MARKED_FOR_DELETION.add(targetPath);
            }
        }
    }

    public Resource loadArchiveFileAsResource(String suiteId) {
        validateSuite(suiteId);
        Path targetPath = getArchiveFilePath(suiteId);
        try {
            Resource resource = new UrlResource(targetPath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + targetPath);
            }
        } catch (IOException ex) {
            throw new MyFileNotFoundException("File not found " + targetPath, ex);
        }
    }

    public List<String> list(String suiteId) {
        validateSuite(suiteId);
        Path filePath = getArchiveFilePath(suiteId);
        try {
            Archive archive = new Archive(filePath.toString(), fileTmpLocation);
            return archive.listEntries();
        } catch (IOException ex) {
            _logger.error("Exception, SuiteId:{}", suiteId, ex);
            return new ArrayList<>();
        }
    }

    public void delete(String suiteId) {
        validateSuite(suiteId);
        Path archiveFilePath = getArchiveFilePath(suiteId);
        Path tmpFilePath = fileTmpLocation.resolve(suiteId).normalize();
        deleteResource(archiveFilePath);
        deleteResource(tmpFilePath);
    }

    private void deleteResource(Path targetPath) {
        if (targetPath.toFile().exists()) {
            if (targetPath.toFile().isDirectory()) {
                try {
                    FileUtils.deleteDirectory(targetPath.toFile());
                } catch (IOException ex) {
                    _logger.error("Unable to delete a directory: {}", targetPath.toString(), ex);
                }
            } else {
                targetPath.toFile().delete();
            }
        }
    }

    private void validateSuite(String suiteId) {
        Optional<Suite> suite = suiteService.get(suiteId);
        if (!suite.isPresent()) {
            throw new RuntimeException("Specified ID[" + suiteId + "] not found in suites list");
        }
    }

}
