package com.anikulin.markercore.service;

import com.anikulin.markercore.domain.Mark;
import com.anikulin.markercore.domain.Rack;

import java.util.List;

public interface RackToMarkConverter {

    List<Mark> convert(Rack rack);
}
