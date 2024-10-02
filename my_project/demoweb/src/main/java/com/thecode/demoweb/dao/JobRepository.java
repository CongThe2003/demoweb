package com.thecode.demoweb.dao;

import com.thecode.demoweb.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job,Integer> {

    List<Job> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);


    List<Job> findAllByIdUser(Long id);
}
