package org.interview.review01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


@Slf4j
public class ManagerService {

    private static final Set<String> EXCLUDE_MNG_SET = Set.of("FAIL1", "FAIL2");
    private static final String DEFAULT_NAME = "default";
    private static final String DEFAULT_EMAIL = "default@gmail.com";

    private final EmployeeService employeeService = new EmployeeService();
    private final CompanyService companyService = new CompanyService();

    public List<ManagerDataByIdResponse> getManagerDataById(String id) {
        var companyListResponseListOptional = companyService.getCompanyListById(id);
        if (companyListResponseListOptional.isEmpty() || companyListResponseListOptional.get().isEmpty()) {
            String errorText = String.format("Не удалось получить список компаний по id=%s", id);
            log.error(errorText);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorText);
        }
        List<CompanyListResponse> companyListResponseList = companyListResponseListOptional.get();
        List<ManagerDataByIdResponse> managerDataByIdResponseList = new ArrayList<>();
        List<CompletableFuture<ManagerDataByIdResponse>> completableFutureList = new ArrayList<>();
        for (CompanyListResponse companyListResponse : companyListResponseList) {
            CompletableFuture<ManagerDataByIdResponse> completableFuture = CompletableFuture
                    .supplyAsync(() -> fillCompanyAndManagerData(companyListResponse))
                    .completeOnTimeout(fillDefaultManagerData(companyListResponse), 5, TimeUnit.SECONDS);
            completableFutureList.add(completableFuture);
        }
        for (CompletableFuture<ManagerDataByIdResponse> managerDataByCusResponseFuture : completableFutureList) {
            try {
                managerDataByIdResponseList.add(managerDataByCusResponseFuture.get());
            } catch (ExecutionException executionException) {
                log.error(executionException.getMessage(), executionException);
            } catch (InterruptedException interruptedException) {
                log.error(interruptedException.getMessage(), interruptedException);
                Thread.currentThread().interrupt();
            }
        }
        return managerDataByIdResponseList;
    }


    private ManagerDataByIdResponse fillCompanyAndManagerData(CompanyListResponse companyListResponse) {
        ManagerDataByIdResponse managerDataByIdResponse = new ManagerDataByIdResponse();
        managerDataByIdResponse.setId(companyListResponse.getId());
        managerDataByIdResponse.setAddInfo(companyListResponse.getInfo());
        ManagerData managerData;
        try {
            managerData = getManagerData(companyListResponse.getManager(), companyListResponse.getId());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            managerData = new ManagerData(DEFAULT_NAME, DEFAULT_EMAIL);
        }
        managerDataByIdResponse.setFullName(managerData.getFullName());
        managerDataByIdResponse.setEmail(managerData.getEmail());
        return managerDataByIdResponse;
    }

    private ManagerDataByIdResponse fillDefaultManagerData(CompanyListResponse companyListResponse) {
        ManagerDataByIdResponse managerDataByIdResponse = new ManagerDataByIdResponse();
        managerDataByIdResponse.setAddInfo(companyListResponse.getInfo());
        managerDataByIdResponse.setFullName(DEFAULT_NAME);
        managerDataByIdResponse.setEmail(DEFAULT_EMAIL);
        return managerDataByIdResponse;
    }

    private ManagerData getManagerData(String manager, String id) {
        if (EXCLUDE_MNG_SET.contains(manager)) {
            return new ManagerData(DEFAULT_NAME, DEFAULT_EMAIL);
        }
        return employeeService
                .getManagerData(manager)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Не найден Менеджер для id=%s", id)));
    }



    public static void main(String[] args) {
        ManagerService managerService = new ManagerService();
        List<ManagerDataByIdResponse> result = managerService.getManagerDataById("1");

        for (ManagerDataByIdResponse item : result) {
            log.info("RESULT: " + item);
        }
    }
}