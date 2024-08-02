package com.webtech.jobms.job.impl;

import com.webtech.jobms.job.Job;
import com.webtech.jobms.job.JobRepository;
import com.webtech.jobms.job.JobService;
import com.webtech.jobms.job.clients.CompanyClient;
import com.webtech.jobms.job.clients.ReviewClient;
import com.webtech.jobms.job.dto.JobDTO;

import com.webtech.jobms.job.external.Company;
import com.webtech.jobms.job.external.Review;
import com.webtech.jobms.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CompanyClient companyClient;
    @Autowired
    private ReviewClient reviewClient;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private JobDTO convertToDto(Job job)
    {
        // RestTemplate restTemplate = new RestTemplate();
//        Company company = restTemplate.getForObject(
//                    "http://COMPANY-SERVICE:8083/companies/" + job.getCompanyId(),
//                    Company.class);
        Company company = companyClient.getCompany(job.getCompanyId());
        //exchange is for getting generic list object and getForObject is used when you need to get one object which you know the
        // type
//        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
//                "http://REVIEW-SERVICE:8084/reviews?companyId=" + job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>() {});
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        return JobMapper.mapToJobWithCompanyDto(job, company, reviews);
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO findJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertToDto(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try{
            jobRepository.deleteById(id);
            return  true;
        }catch (Exception e) {
            return  false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job job) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job jobToUpdate = jobOptional.get();
            jobToUpdate.setTitle(job.getTitle());
            jobToUpdate.setDescription(job.getDescription());
            jobToUpdate.setMinSalary(job.getMinSalary());
            jobToUpdate.setMaxSalary(job.getMaxSalary());
            jobToUpdate.setLocation(job.getLocation());
            jobRepository.save(jobToUpdate);
            return  true;
        }
        return false;
    }
}
