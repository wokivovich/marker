package com.anikulin.markercore.service;

import com.anikulin.markercore.domain.Mark;

import java.util.List;

public interface Writer {

    void writeMarksToFile(List<Mark> marks);
}
