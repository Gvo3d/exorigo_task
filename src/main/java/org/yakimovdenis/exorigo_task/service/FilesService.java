package org.yakimovdenis.exorigo_task.service;

import org.springframework.data.domain.Page;
import projectpackage.model.AuthEntities.User;
import projectpackage.model.Files.FileOnServer;

import java.util.List;

/**
 * Created by Admin on 10.02.2017.
 */
public interface FilesService {
    void save(FileOnServer file);
    List<FileOnServer> findByAlternativeLike(String searchable);
    FileOnServer findOne(Integer id);
    Page<FileOnServer> findAll(int startingCount, int endingCount, String sortingParameter, boolean ascend);
    List<FileOnServer> findAllPublicityTrueOrUserIsAuthor(User user, int startingCount, int offset, String sortingParameter, boolean ascend);
    long count();
}
