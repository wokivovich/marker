package com.anikulin.markercore.controller;

import com.anikulin.markercore.domain.Rack;
import com.anikulin.markercore.service.MarksService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@RestController
@RequestMapping(
        path = "",
        produces = "application/json"
)
@CrossOrigin(origins = {"http://localhost:3000", "http://ui:3000"})
public class MarksController {

    private final MarksService marksService;

    public MarksController(MarksService marksService) {
        this.marksService = marksService;
    }

    @PostMapping("/marks")
    public void generateMarks(@RequestBody Set<Rack> racks) {
        marksService.generateMarks(racks);

    }

    @GetMapping("/download")
    private ResponseEntity<Resource> downloadMarks() throws IOException {
        Path filePath = Paths.get("res.xlsx");
        File file = filePath.toFile();
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(filePath));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }
}
