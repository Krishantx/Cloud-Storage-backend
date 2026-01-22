package com.github.krishantx.Cloud_Security.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileId;

    private String fileName;

    @Builder.Default
    private Date lastModified = new Date();

    @ManyToOne
    @JsonIgnoreProperties({"password", "username"})
    private UserModel owner;

    public FileModel(String fileName, UserModel owner) {
        this.fileName = fileName;
        this.lastModified = new Date();
        this.owner = owner;
    }

    public FileModel(String fileName) {
        this.fileName = fileName;
        this.lastModified = new Date();
    }
}