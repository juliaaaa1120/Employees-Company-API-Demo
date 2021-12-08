package com.afs.restapi.repository;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.NoCompanyFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();

    public CompanyRepository() {
        companies.add(new Company(1, "OOCL", Arrays.asList(
                new Employee(1, "Julia", 18, "Female", 100000),
                new Employee(2, "Jason", 18, "Male", 100000),
                new Employee(3, "Klaus", 18, "Male", 100000)
        )));
        companies.add(new Company(2, "DHL", Arrays.asList(
                new Employee(4, "Joanne", 18, "Female", 100000),
                new Employee(5, "John", 18, "Male", 100000),
                new Employee(6, "Johnson", 18, "Male", 100000),
                new Employee(7, "Nicole", 18, "Female", 100000)
        )));
        companies.add(new Company(3, "SF Express", Arrays.asList(
                new Employee(8, "Gloria", 18, "Female", 100000)
        )));
    }


    public List<Company> findAll() {
        return companies;
    }

    public Company findById(Integer id) {
        return companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(NoCompanyFoundException::new);
    }

    public List<Employee> findAllEmployeesByCompanyId(Integer id) {
        return companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .map(Company::getEmployees)
                .orElseThrow(NoCompanyFoundException::new);
    }

    public List<Company> findByPage(Integer page, Integer pageSize) {
        return companies.stream()
                .skip((long)(page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company create(Company company) {
        Integer nextId = companies.stream()
                .mapToInt(Company::getId)
                .max()
                .orElse(0) + 1;
        company.setId(nextId);
        companies.add(company);
        return company;
    }

    public Company save(Integer id, Company updatedCompany) {
        Company company = findById(id);
        companies.remove(company);
        companies.add(updatedCompany);
        return updatedCompany;
    }

    public Company remove(Integer id) {
        Company company = findById(id);
        companies.remove(company);
        return company;
    }

    public void clearAll() {
        companies.clear();
    }
}
