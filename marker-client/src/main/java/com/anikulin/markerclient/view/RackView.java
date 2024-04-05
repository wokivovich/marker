package com.anikulin.markerclient.view;

import com.anikulin.markerclient.domain.ConnectionType;
import com.anikulin.markerclient.domain.Consumer;
import com.anikulin.markerclient.domain.ConsumerInput;
import com.anikulin.markerclient.domain.Rack;
import com.anikulin.markerclient.service.RequestSender;
import com.anikulin.markerclient.view.components.ConsumerInputView;
import com.anikulin.markerclient.view.components.ConsumerView;
import com.anikulin.markerclient.view.components.RackFormView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Route(value = "")
public class RackView extends VerticalLayout {

    private final RequestSender requestSender;

    private Set<Rack> racks = new HashSet<>();
    private HorizontalLayout rackForms = new HorizontalLayout();

    public RackView(RequestSender requestSender) {
        this.requestSender = requestSender;

        Button newRack = new Button("Создать стойку");
        Button generateMarks = new Button("Сгенерировать маркировку");
        Anchor downloadLink = new Anchor();
        downloadLink.setVisible(false);

        newRack.addClickListener(event -> {
            RackFormView rackFormView = new RackFormView();
            rackForms.add(rackFormView);
        });

        generateMarks.addClickListener(event -> {
            generateMarks(requestSender);
            racks.clear();
            downloadFile(downloadLink);
        });

        add(
                new H1("Создание маркировки"),
                newRack,
                generateMarks,
                downloadLink,
                rackForms
        );
    }

    private void generateMarks(RequestSender requestSender) {
        rackForms.getChildren().forEach(form -> {
            if (form instanceof RackFormView) {
                Rack rack = Rack.builder().build();

                RackFormView rackFormView = (RackFormView) form;
                rack.setName(rackFormView.getRackName().getValue());
                rack.setPowerSource(rackFormView.getPowerSource().getValue());
                Set<ConsumerInputView> consumerInputViews = rackFormView.getConsumerInputs();
                Set<ConsumerInput> consumerInputs = new HashSet<>();

                consumerInputViews.stream().forEach(input -> {
                    ConsumerInput consumerInput = ConsumerInput.builder()
                            .name(input.getInputName().getValue())
                            .color(input.getColor().getValue())
                            .consumers(getConsumers(input.getConsumers()))
                            .build();

                    consumerInputs.add(consumerInput);
                });

                rack.setConsumerInputs(consumerInputs);

                racks.add(rack);
            }
        });
        requestSender.createMarks(racks);
        racks.forEach(System.out::println);
    }

    private Set<Consumer> getConsumers(Set<ConsumerView> consumerViews) {
        Set<Consumer> consumers = new HashSet<>();
        consumerViews.forEach(view -> {
            Consumer consumer = Consumer.builder()
                    .name(view.getName().getValue())
                    .connectionType(getConnectionType(view.getConnectionType().getValue()))
                    .powerSwitchName(view.getPowerSwitchName().getValue())
                    .consumers(getConsumers(view.getConsumers()))
                    .build();

            consumers.add(consumer);
        });

        return consumers;
    }

    private ConnectionType getConnectionType(String rawConnectionType) {
        switch (rawConnectionType) {
            case "Кабель":
                return ConnectionType.CABEL;
            case "РШ":
                return ConnectionType.POWER_PLUG;
            case "АВ":
                return ConnectionType.POWER_SWITCH;
            case"АВ+РШ":
                return ConnectionType.POWER_PLUG_AND_POWER_SWITCH;
        }
        return ConnectionType.CABEL;
    }

    private void downloadFile(Anchor downloadLink) {
        try {
            ResponseEntity<Resource> response = requestSender.downloadMarks();
            Resource resource = response.getBody();
            if (resource != null) {
                StreamResource streamResource = new StreamResource("res.xlsx", () -> {
                    try {
                        return resource.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                });
                downloadLink.setHref(streamResource);
                downloadLink.setText("Маркировка");
                downloadLink.getElement().setAttribute("download", true);
                downloadLink.setVisible(true);

            } else {
                Notification.show("File not found!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Notification.show("Error downloading file: " + ex.getMessage());
        }
    }
}
