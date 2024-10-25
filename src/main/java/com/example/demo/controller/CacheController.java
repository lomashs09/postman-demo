package com.example.demo.controller;

import com.example.demo.service.CacheService;
import com.example.demo.util.Constant;
import com.example.demo.util.vo.ApiResponse;
import com.example.demo.util.vo.CacheRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(Constant.API_VERSION + "/cache")
@RestController
public class CacheController {
    private static final Logger logger = LoggerFactory.getLogger(CacheController.class);

    @Autowired
    private CacheService cacheService;

    @GetMapping("/{key}")
    public ResponseEntity<ApiResponse<String>> getCache(@PathVariable String key) {
        logger.info("Received request to fetch all caches");
        String value = cacheService.getCacheValue(key);
        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                true,
                value
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<String>> createCache(@RequestBody CacheRequest request) {
        logger.info("Create cache");
        cacheService.addOrUpdateCache(request);
        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                true,
                ""
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<String>> updateCache(@RequestBody CacheRequest request) {
        logger.info("Updating cache");
        cacheService.addOrUpdateCache(request);
        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                true,
                ""
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
