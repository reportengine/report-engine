package org.re;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Tasks {

    public static final ConcurrentLinkedQueue<Path> MARKED_FOR_DELETION = new ConcurrentLinkedQueue<>();

    @Scheduled(fixedRate = 30 * 1000L)
    public void deleteFiles() {
        while (MARKED_FOR_DELETION.size() > 0) {
            Path targetPath = MARKED_FOR_DELETION.remove();
            File targetFile = targetPath.toFile();
            long timestamp = System.currentTimeMillis() - 10000L;
            if (targetFile.exists()) {
                if (targetFile.lastModified() <= timestamp) { // delete if file create 10 seconds before
                    if (targetPath.toFile().isDirectory()) {
                        try {
                            FileUtils.deleteDirectory(targetPath.toFile());
                        } catch (IOException ex) {
                            _logger.error("Unable to delete a directory: {}", targetPath.toString(), ex);
                        }
                    } else {
                        targetPath.toFile().delete();
                    }
                } else {
                    MARKED_FOR_DELETION.add(targetPath);
                }
            }
        }
    }
}
