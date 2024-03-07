package com.bugflix.weblog.common.healthcheck;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class HealthCheckController {

    @GetMapping("/api/v1/healthcheck")
    public ResponseEntity<Void> healthcheck() {
        return ResponseEntity.ok().build();
    }
}