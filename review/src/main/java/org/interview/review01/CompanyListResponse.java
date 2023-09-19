package org.interview.review01;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CompanyListResponse {
    private final String id;
    private final String manager;
    private final String info;
}
