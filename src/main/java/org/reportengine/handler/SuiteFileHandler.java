package org.reportengine.handler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.reportengine.service.SuiteFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/suites/files")
public class SuiteFileHandler {

    @Autowired
    private SuiteFileService suiteFileService;

    @PostMapping("/uploadSingle/{suiteId}")
    public void uploadFile(@RequestParam("file") MultipartFile file,
            @PathVariable(name = "suiteId", required = true) String suiteId) {
        suiteFileService.storeFile(file, suiteId);
    }

    @PostMapping("/uploadMultiple/{suiteId}")
    public void uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
            @PathVariable(name = "suiteId", required = true) String suiteId) {
        for (MultipartFile file : files) {
            uploadFile(file, suiteId);
        }
    }

    @GetMapping("/list/{suiteId}")
    public List<String> list(@PathVariable(required = true) String suiteId) {
        return suiteFileService.list(suiteId);
    }

    @GetMapping("/download/{suiteId}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable(required = true) String suiteId,
            @PathVariable(required = true) String fileName,
            HttpServletRequest request) {
        // Load file as Resource
        Resource resource = suiteFileService.loadFileAsResource(fileName, suiteId);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            _logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{suiteId}")
    public Map<String, Object> delete(@PathVariable(required = true) String suiteId) {
        return suiteFileService.delete(suiteId);
    }

}
