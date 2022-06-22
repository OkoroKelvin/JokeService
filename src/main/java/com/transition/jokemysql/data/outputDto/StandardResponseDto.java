package com.transition.jokemysql.data.outputDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.transition.jokemysql.data.inputDto.ApiFieldError;
import com.transition.jokemysql.data.inputDto.PageInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StandardResponseDto implements Serializable {
    @JsonProperty
    protected Status status;
    @JsonProperty
    protected ApiFieldError data;
    @JsonProperty
    protected List<ApiFieldError> errors = new ArrayList<>();
    @JsonProperty
    protected PageInfo page;

    public StandardResponseDto() {
    }

    StandardResponseDto(Status status) {
        this.status = status;
    }

    public StandardResponseDto(Status status, ApiFieldError data) {
        this.status = status;
        this.data = data;
    }

    public StandardResponseDto(Status status, List<ApiFieldError> errors) {
        this.status = status;
        this.errors = errors;
    }

    public StandardResponseDto(Status status, PageInfo page) {
        this.status = status;
        this.page = page;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ApiFieldError getData() {
        return data;
    }

    public void setData(ApiFieldError data) {
        this.data = data;
    }

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }

    public List<ApiFieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<ApiFieldError> errors) {
        this.errors = errors;
    }
}
