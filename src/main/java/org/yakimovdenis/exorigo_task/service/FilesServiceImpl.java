package org.yakimovdenis.exorigo_task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import projectpackage.model.AuthEntities.User;
import projectpackage.model.Files.FileOnServer;
import projectpackage.repositories.FilesRepositories.CustomFilesRepository;
import projectpackage.repositories.FilesRepositories.FilesRepository;
import projectpackage.support.SortingTool;

import java.util.List;

/**
 * Created by Admin on 10.02.2017.
 */
@Service
public class FilesServiceImpl implements FilesService {

    @Autowired
    FilesRepository filesRepository;

    @Autowired
    CustomFilesRepository customFilesRepository;

    @Override
    public void save(FileOnServer file) {
        filesRepository.save(file);
    }

    @Override
    public List<FileOnServer> findByAlternativeLike(String searchable) {
        return filesRepository.findByAlternativeContaining(searchable);
    }

    @Override
    public FileOnServer findOne(Integer id) {
        return filesRepository.findOne(id);
    }

    public Page<FileOnServer> findAll(int startingCount, int endingCount, String sortingParameter, boolean ascend){
        return filesRepository.findAll(new PageRequest(startingCount, endingCount, SortingTool.getSort(sortingParameter, ascend)));
    }

    public List<FileOnServer> findAllPublicityTrueOrUserIsAuthor(User user, int startingCount, int quantity, String sortingParameter, boolean ascend){
        return customFilesRepository.findAllPublicityTrueOrUserIsAuthor(user, startingCount, quantity, sortingParameter, ascend);
    }

    @Override
    public long count() {
        return filesRepository.count();
    }


}
