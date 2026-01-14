package com.github.krishantx.Cloud_Security.repo;

import com.github.krishantx.Cloud_Security.model.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepo extends JpaRepository<FileModel, Integer> {

    List<FileModel> findByOwnerUsername(String username);

}
