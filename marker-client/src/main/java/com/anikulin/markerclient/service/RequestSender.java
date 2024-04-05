package com.anikulin.markerclient.service;

import com.anikulin.markerclient.domain.Rack;
import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@FeignClient(name = "marker-core", url = "core:5000")
public interface RequestSender {

    @PostMapping("/marks")
    void createMarks(@RequestBody Set<Rack> racks);

    @GetMapping("/download")
    ResponseEntity<Resource> downloadMarks();
}
