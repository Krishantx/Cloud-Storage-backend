package com.github.krishantx.Cloud_Security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.nio.file.Path;
import java.util.Date;

@Entity
public class FileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileId;
    private String fileName;
    private Date lastModified;

    public FileModel() {
    }
    public FileModel(int fileId, String fileName, Date lastModified) {
        this.fileName = fileName;
        this.fileId = fileId;
        this.lastModified = lastModified;
    }
    public FileModel(String fileName) {
        this.fileName = fileName;
        this.lastModified = new Date();
    }

    public int getFileId() {
        return this.fileId;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return "FileModel{" +
                "fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                ", lastModified=" + lastModified +
                '}';
    }
}
