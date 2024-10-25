package com.example.demo.util.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int status;
    private boolean success;
    private T data;
    private Object meta;

    // Constructor without meta
    public ApiResponse(int status, boolean success, T data) {
        this.status = status;
        this.success = success;
        this.data = data;
        this.meta = null; // Meta is optional
    }
}

