package com.geekhub.hw8.storage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FileInstance {
    public static final DateFormat FILE_CREATION_DATE_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

    private String fileName;
    private String fileExtension;
    private long fileSize;
    private String creationDate;
    private boolean isDirectory;

    public FileInstance(String fileName, boolean isDirectory, long fileSize, String creationDate) {
        this.fileName = fileName;
        this.isDirectory = isDirectory;
        this.fileSize = fileSize;
        this.creationDate = creationDate;

        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            if (isDirectory) {
                fileExtension = "folder";
            } else {
                fileExtension = "unknown";
            }
        } else {
            fileExtension = fileName.substring(lastDotIndex + 1);
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getFileExtension() {
        return fileExtension;
    }

}
