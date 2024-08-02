package com.webtech.jobms.job;

import com.webtech.jobms.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> findAll();
    void createJob(Job job);

    JobDTO findJobById(Long id);

    boolean deleteJobById(Long id);

    boolean updateJob(Long id, Job job);
}
