package com.anikulin.markerclient.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class Consumer {

    private String name;
    private ConnectionType connectionType;
    private String powerSwitchName;
    private String cabelName;
    private Set<Consumer> consumers;
}
