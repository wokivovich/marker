package com.anikulin.markerclient.view.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ConsumerInputView extends VerticalLayout {

    private TextField inputName = new TextField("Название ввода");
    private ComboBox<String> color = new ComboBox<>("Цветовое обозначение ввода");
    private Set<ConsumerView> consumers = new HashSet<>();

    public ConsumerInputView() {
        color.setItems("Белый", "Красный", "Голубой");

        Button addCustomer = new Button("Добавить потребителя");
        HorizontalLayout consumersForms = new HorizontalLayout();

        addCustomer.addClickListener(event -> {
            ConsumerView consumer = new ConsumerView();
            consumers.add(consumer);
            consumersForms.add(consumer);
        });

        add(
                inputName,
                color,
                addCustomer,
                consumersForms
        );
    }
}
