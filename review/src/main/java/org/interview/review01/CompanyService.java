package org.interview.review01;

import java.util.List;
import java.util.Optional;

public class CompanyService {

    public Optional<List<CompanyListResponse>> getCompanyListById(String id) {

        List<CompanyListResponse> list = List.of(
                CompanyListResponse.builder().id(id).manager("manager1").info("info1").build(),
                CompanyListResponse.builder().id(id).manager("manager2").info("info2").build(),
                CompanyListResponse.builder().id(id).manager("manager3").info("info3").build(),
                CompanyListResponse.builder().id(id).manager("manager4").info("info4").build()
        );

        return Optional.of(list);
    }



}
