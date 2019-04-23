package org.re.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Archive {

    private static final int COMPRESSION_LEVEL = Deflater.DEFAULT_COMPRESSION;
    private static final String TMP_DIR = "archive_tmp";

    private File file;
    private Path tmpLocation;

    public Archive(String zipFilePath, Path tmpLocation) throws IOException {
        file = new File(zipFilePath);
        this.tmpLocation = tmpLocation.resolve(TMP_DIR);
    }

    public boolean exists() {
        return file.exists();
    }

    public void addEntry(String name, byte[] content) throws IOException {
        // if zip file exists extract and add new file in to it. then compress
        Path uncompressFiles = null;
        if (exists()) {
            String filename = file.getName();
            if (filename.toLowerCase().endsWith(".zip")) {
                filename = filename.substring(0, filename.length() - 4); // removing ".zip"
            }
            uncompressFiles = tmpLocation.resolve(filename);
            extractAll(uncompressFiles.toString());
            File newFile = uncompressFiles.resolve(name).toFile();
            FileOutputStream fout = new FileOutputStream(newFile);
            fout.write(content);
            fout.close();
        }
        FileOutputStream fout = new FileOutputStream(file);
        ZipOutputStream zout = null;
        try {
            zout = new ZipOutputStream(new BufferedOutputStream(fout));
            zout.setLevel(COMPRESSION_LEVEL);
            if (exists()) {
                for (File _file : uncompressFiles.toFile().listFiles()) {
                    ZipEntry entry = new ZipEntry(_file.getName());
                    zout.putNextEntry(entry);
                    zout.write(Files.readAllBytes(_file.toPath()));
                    zout.closeEntry();
                }
            } else {
                ZipEntry entry = new ZipEntry(name);
                zout.putNextEntry(entry);
                zout.write(content);
                zout.closeEntry();
            }
        } finally {
            if (zout != null) {
                zout.close();
            }
        }
        if (exists()) {
            delete(uncompressFiles);
        }
    }

    public void extractEntry(String sourceFileName, Path targetPath) throws IOException {
        if (exists()) {
            ZipFile zipFile = new ZipFile(file);
            try {
                ZipEntry entry = zipFile.getEntry(sourceFileName);
                InputStream inputStream = zipFile.getInputStream(entry);
                // if target path not found create dirs
                if (!targetPath.toFile().getParentFile().exists()) {
                    targetPath.toFile().getParentFile().mkdirs();
                }
                Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            } finally {
                if (zipFile != null) {
                    zipFile.close();
                }
            }
        }
    }

    public void extractAll(String destinationDir) throws IOException {
        File destDir = new File(destinationDir);
        if (destDir.exists()) {
            delete(Paths.get(destinationDir));
        }
        // create dirs
        destDir.mkdirs();
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();

    }

    private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    public List<String> listEntries() throws ZipException, IOException {
        List<String> files = new ArrayList<>();
        if (exists()) {
            ZipFile zipFile = new ZipFile(file);
            try {
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    files.add(entry.getName());
                }
            } finally {
                if (zipFile != null) {
                    zipFile.close();
                }
            }
        }
        return files;
    }

    public void delete(Path targetPath) {
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
}
