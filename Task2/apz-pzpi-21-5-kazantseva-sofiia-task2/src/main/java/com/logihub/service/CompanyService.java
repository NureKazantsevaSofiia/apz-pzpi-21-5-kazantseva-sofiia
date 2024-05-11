package com.logihub.service;

import com.logihub.exception.AuthException;
import com.logihub.exception.UserException;
import com.logihub.model.enums.CompanyType;
import com.logihub.model.request.CompanyRequest;
import com.logihub.model.response.CompanyDTO;

import java.util.List;

public interface CompanyService {

    CompanyDTO addTruckCompany(CompanyRequest newTruckCompany) throws AuthException;

    CompanyDTO addParkingCompany(CompanyRequest newParkingCompany) throws AuthException;

    List<CompanyDTO> getAllCompanies(CompanyType type);

    List<CompanyDTO> getCompaniesByName(CompanyType type, String name);

    CompanyDTO updateCompany(String email, Long userId, Long companyId, CompanyRequest companyRequest)
            throws AuthException, UserException;
}
