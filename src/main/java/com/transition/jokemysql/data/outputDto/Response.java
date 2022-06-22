package com.transition.jokemysql.data.outputDto;

import com.transition.jokemysql.data.inputDto.ApiFieldError;
import com.transition.jokemysql.data.inputDto.PageInfo;
import com.transition.jokemysql.data.model.Joke;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;


public class Response extends StandardResponseDto {
    private List<String> reports;
    private String report;

    public Response(){

    }

    public Response(Status status){
        this.status = status;
    }

    public Response(Status status, ApiFieldError data) {
        this.data = data;
        this.status = status;
    }

    public Response(List<String> reports, Status status, PageInfo page){
        super(status);
        this.reports = reports;
        this.page = page;
    }

    public Response(String report, Status status) {
        super(status);
        this.report = report;
    }

    public Response(Status status, String report) {
        super(status);
        this. report=  report;
    }

    public Response(List<String> reports, Status status) {
        super(status);
        this.reports = reports;
    }

    public List<String> getReports() {
        return reports;
    }

    public void setReports(List<String> reports) {
        this.reports = reports;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
