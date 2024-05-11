package com.thecode.demoweb.service;

import com.thecode.demoweb.dao.JobRepository;
import com.thecode.demoweb.entity.Job;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    JobRepository jobRepository;

    public JobServiceImpl(JobRepository theJobRepository) {
        this.jobRepository = theJobRepository;
    }

    @Override
    public ArrayList<Job> findAll() {
        return (ArrayList<Job>) jobRepository.findAll();
    }

    @Override
    public Optional<Job> findById(int id) {
        return jobRepository.findById(id);
    }

    @Transactional
    @Override
    public void save(Job theJob) {
        jobRepository.save(theJob);
    }

    @Transactional
    @Override
    public void delete(int theId) {
        jobRepository.deleteById(theId);
    }

    @Override
    public List<Job> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword) {
        return jobRepository.findByTitleContainingOrContentContaining(titleKeyword,contentKeyword);
    }


}
