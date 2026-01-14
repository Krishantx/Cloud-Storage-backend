package com.github.krishantx.Cloud_Security.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Entity
public class FileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileId;
    private String fileName;
    private Date lastModified;
    @ManyToOne
    @Autowired
    @JsonIgnoreProperties({"password, username"})
    private UserModel owner;

    public FileModel() {
    }
    public FileModel(int fileId, String fileName, Date lastModified) {
        this.fileName = fileName;
        this.fileId = fileId;
        this.lastModified = lastModified;
    }
    public FileModel(String fileName, UserModel owner) {
        this.fileName = fileName;
        this.lastModified = new Date();
        this.owner = owner;
    }
    public FileModel(String fileName) {
        this.fileName = fileName;
        this.lastModified = new Date();
    }

    public UserModel getOwner() {
        return owner;
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
