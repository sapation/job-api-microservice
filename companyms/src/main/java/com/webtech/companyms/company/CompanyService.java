package com.webtech.companyms.company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    Company getCompanyById(Long id);
    void createCompany(Company company);
    boolean deleteCompany(Long id);
    boolean updateCompany(Company company, Long id);
}
