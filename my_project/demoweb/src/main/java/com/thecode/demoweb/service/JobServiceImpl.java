package com.thecode.demoweb.service;

import com.thecode.demoweb.dao.JobRepository;
import com.thecode.demoweb.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public List<Job> findById(Long id) {
        return jobRepository.findAllByIdUser(id);
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

    @Override
    public Page<Job> getAllPage(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1,3);
        return this.jobRepository.findAll(pageable);
    }


}
