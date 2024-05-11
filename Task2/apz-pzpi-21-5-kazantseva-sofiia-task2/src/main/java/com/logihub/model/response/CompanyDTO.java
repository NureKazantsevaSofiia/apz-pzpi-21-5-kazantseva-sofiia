package com.logihub.model.response;

import com.logihub.model.entity.Company;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDTO {

    private Long id;

    private String name;

    private String logo;

    private String type;

    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.logo = company.getLogo();
        this.type = company.getType().toString();
    }
}
