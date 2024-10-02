package com.thecode.demoweb.service;

import com.thecode.demoweb.entity.Job;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public interface JobService {

    public ArrayList<Job> findAll();

    public List<Job> findById(Long id);

    void save(Job theJob);

    void delete(int theId);

    List<Job> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);

    Page<Job> getAllPage(Integer pageNo);


}
