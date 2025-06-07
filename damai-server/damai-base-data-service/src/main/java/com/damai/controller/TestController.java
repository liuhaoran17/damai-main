package com.damai.controller;

import com.damai.common.ApiResponse;
import com.damai.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Tag(name = "test", description = "测试")
public class TestController {

    @Autowired
    private TestService testService;


    @Operation(summary  = "测试数据")
    @PostMapping(value = "/testData")
    public ApiResponse<Void> testData(HttpServletRequest request) {
        testService.testData(request);
        return ApiResponse.ok();
    }

    @Operation(summary  = "测试数据")
    @PostMapping(value = "/testData2")
    public ApiResponse<Void> testData2(HttpServletRequest request) {
        testService.testData2(request);
        return ApiResponse.ok();
    }
    
    @Operation(summary  = "测试数据")
    @PostMapping(value = "/testData3")
    public ApiResponse<Void> testData3(HttpServletRequest request) {
        testService.testData3(request);
        return ApiResponse.ok();
    }
}
