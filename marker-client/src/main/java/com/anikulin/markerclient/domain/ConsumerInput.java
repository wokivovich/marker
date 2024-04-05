package com.anikulin.markerclient.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class ConsumerInput {

    private String name;
    private String color;
    private Set<Consumer> consumers;
}
