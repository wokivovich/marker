package com.anikulin.markerclient.view.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class RackFormView extends VerticalLayout {
    private TextField rackName = new TextField("Название стойки");
    private TextField powerSource = new TextField("Источник питания");
    private Set<ConsumerInputView> consumerInputs = new HashSet<>();

    public RackFormView() {
        HorizontalLayout inputs = new HorizontalLayout();
        Button addInput = new Button("Добавить ввод");

        addInput.addClickListener(event -> {
           ConsumerInputView consumerInputView = new ConsumerInputView();
           consumerInputs.add(consumerInputView);
           inputs.add(consumerInputView);
        });

        add(
                rackName,
                powerSource,
                addInput,
                inputs
        );
    }
}
