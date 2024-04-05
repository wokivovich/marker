package com.anikulin.markerclient.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class Rack {

    private String name;
    private String powerSource;
    private Set<ConsumerInput> consumerInputs;
}
