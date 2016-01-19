package com.geekhub.hw9.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FileInstance {
    private static final DateFormat FILE_CREATION_DATE_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

    private String fileName;
    private String fileExtension;
    private long fileSize;
    private long creationDate;
    private String creationDateString;
    private boolean isDirectory;

    public FileInstance(String fileName, boolean isDirectory, long fileSize, Long creationDate) {
        this.fileName = fileName;
        this.isDirectory = isDirectory;
        this.fileSize = fileSize;
        this.creationDate = creationDate;
        this.creationDateString = FILE_CREATION_DATE_TIME_FORMAT.format(creationDate);

        if (isDirectory) {
            fileExtension = "folder";
        } else {
            int lastDotIndex = fileName.lastIndexOf(".");
            if (lastDotIndex == -1) {
                fileExtension = "unknown";
            } else {
                fileExtension = fileName.substring(lastDotIndex + 1).toLowerCase();
            }
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean getIsDirectory() {
        return isDirectory;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationDateString() {
        return creationDateString;
    }

    public void setCreationDateString(String creationDateString) {
        this.creationDateString = creationDateString;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public static FileInstance getFileDataFromPath(Path directoryItem) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(directoryItem, BasicFileAttributes.class);

        String name = directoryItem.getFileName().toString();
        boolean isDirectory = attrs.isDirectory();
        long size = Files.size(directoryItem);
        long creationTime = attrs.creationTime().toMillis();

        return new FileInstance(name, isDirectory, size, creationTime);
    }

}
