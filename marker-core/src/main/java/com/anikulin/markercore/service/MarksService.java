package com.anikulin.markercore.service;

import com.anikulin.markercore.domain.Mark;
import com.anikulin.markercore.domain.Rack;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

@Service
public class MarksService {

    private final RackToMarkConverter converter;
    private final Writer writer;


    public MarksService(RackToMarkConverter converter, Writer writer) {
        this.converter = converter;
        this.writer = writer;
    }

    public void generateMarks(Set<Rack> racks) {
        List<Mark> marks = converter.convert(racks.stream().findFirst().get());
        writer.writeMarksToFile(marks);
        printMarks(marks);
        marks.clear();
    }

    private void printMarks(List<Mark> marks) {
        marks.forEach(System.out::println);
    }
}
