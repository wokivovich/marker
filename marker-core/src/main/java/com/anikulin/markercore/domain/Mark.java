package com.anikulin.markercore.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Mark {

    private String name;
    private int width;
    private int height;
    private String color;
}
