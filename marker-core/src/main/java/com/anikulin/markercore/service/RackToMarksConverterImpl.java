package com.anikulin.markercore.service;

import com.anikulin.markercore.domain.Consumer;
import com.anikulin.markercore.domain.Mark;
import com.anikulin.markercore.domain.Rack;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RackToMarksConverterImpl implements RackToMarkConverter {

    private List<Mark> marks = new ArrayList<>();

    private static final int WIDTH = 10;
    private static final int HEIGHT = 5;

    public List<Mark> convert(Rack rack) {
        rack.getConsumerInputs().forEach(input -> {
            input.getConsumers().forEach(consumer -> {
                if (consumer != null) {
                    consumerToMark(consumer, input.getName(), input.getColor(), "");
                }
            });
        });

        return this.marks;
    }

    private void consumerToMark(Consumer consumer, String inputName, String inputColor, String sourceName) {
        if (sourceName.equals("")) {
            Mark mark = Mark.builder()
                    .name(consumer.getName())
                    .width(WIDTH)
                    .height(HEIGHT)
                    .color("Белый")
                    .build();
            marks.add(mark);
        } else {
            switch (consumer.getConnectionType()) {
                case CABEL -> createBaseMark(consumer, inputName, inputColor, sourceName);
                case POWER_SWITCH -> createPSMark(consumer, inputName, inputColor, sourceName);
                case POWER_PLUG -> createPPMark(consumer, inputName, inputColor, sourceName);
                case POWER_PLUG_AND_POWER_SWITCH -> createPSPPMark(consumer, inputName, inputColor, sourceName);
            }
        }

        consumer.getConsumers().forEach(c ->
                consumerToMark(c, inputName, inputColor, consumer.getName()));
    }

    private void createBaseMark(Consumer consumer, String inputName, String inputColor, String sourceName) {
        Mark mark = Mark.builder()
                .name(String.format(
                        "%s от %s",
                        consumer.getName(),
                        formatSourceName(sourceName, inputName)
                ))
                .width(WIDTH)
                .height(HEIGHT)
                .color(inputColor)
                .build();
        marks.add(mark);
    }

    private void createPSMark(Consumer consumer, String inputName, String inputColor, String sourceName) {
        Mark mark = Mark.builder()
                .name(String.format(
                        "%s от %s %s",
                        consumer.getName(),
                        formatSourceName(sourceName, inputName),
                        consumer.getPowerSwitchName()
                ))
                .width(WIDTH)
                .height(HEIGHT)
                .color(inputColor)
                .build();
        marks.add(mark);
    }

    private void createPPMark(Consumer consumer, String inputName, String inputColor, String sourceName) {
        Mark powerPlugIn = Mark.builder()
                .name(String.format(
                        "РШ от %s к %s",
                        formatSourceName(sourceName, inputName),
                        consumer.getName()
                ))
                .width(WIDTH)
                .height(HEIGHT)
                .color(inputColor)
                .build();
        Mark powerPlugOut= Mark.builder()
                .name(String.format(
                        "РШ от %s к %s",
                        consumer.getName(),
                        formatSourceName(sourceName, inputName)
                ))
                .width(WIDTH)
                .height(HEIGHT)
                .color(inputColor)
                .build();
        Mark consumerMark = Mark.builder()
                .name(String.format(
                        "%s от %s",
                        consumer.getName(),
                        formatSourceName(sourceName, inputName)
                ))
                .width(WIDTH)
                .height(HEIGHT)
                .color(inputColor)
                .build();

        marks.add(powerPlugIn);
        marks.add(powerPlugOut);
        marks.add(consumerMark);
    }

    private void createPSPPMark(Consumer consumer, String inputName, String inputColor, String sourceName) {
        Mark powerPlugIn = Mark.builder()
                .name(String.format(
                        "РШ от %s к %s",
                        formatSourceName(sourceName, inputName),
                        consumer.getName(),
                        consumer.getPowerSwitchName()
                ))
                .width(WIDTH)
                .height(HEIGHT)
                .color(inputColor)
                .build();
        Mark powerPlugOut= Mark.builder()
                .name(String.format(
                        "РШ от %s к %s %s",
                        consumer.getName(),
                        formatSourceName(sourceName, inputName),
                        consumer.getPowerSwitchName()
                ))
                .width(WIDTH)
                .height(HEIGHT)
                .color(inputColor)
                .build();
        Mark consumerMark = Mark.builder()
                .name(String.format(
                        "%s от %s %s",
                        consumer.getName(),
                        formatSourceName(sourceName, inputName),
                        consumer.getPowerSwitchName()
                ))
                .width(WIDTH)
                .height(HEIGHT)
                .color(inputColor)
                .build();

        marks.add(powerPlugIn);
        marks.add(powerPlugOut);
        marks.add(consumerMark);
    }

    private String formatSourceName(String sourceName, String inputName) {
        return inputName.length() > 1 ? sourceName + " " + inputName : sourceName + "-" + inputName;
    }

}
