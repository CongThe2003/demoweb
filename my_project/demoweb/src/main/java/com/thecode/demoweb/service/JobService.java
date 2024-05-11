package com.thecode.demoweb.service;

import com.thecode.demoweb.entity.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface JobService {

    public ArrayList<Job> findAll();

    public Optional<Job> findById(int id);

    void save(Job theJob);

    void delete(int theId);

    List<Job> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);

}
