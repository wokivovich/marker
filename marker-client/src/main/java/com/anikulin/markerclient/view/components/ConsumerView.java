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
public class ConsumerView extends VerticalLayout {

    private TextField name = new TextField("Название потребителя");
    private ComboBox<String> connectionType = new ComboBox<>("Тип подключения");
    private TextField powerSwitchName = new TextField("Название автоматического выключателя");
    private TextField cabelName = new TextField("Марка и сечение кабеля");
    private Set<ConsumerView> consumers = new HashSet<>();

    public ConsumerView() {
        connectionType.setItems("Кабель", "РШ", "АВ", "АВ+РШ");

        HorizontalLayout consumersForms = new HorizontalLayout();
        Button addCustomer = new Button("Добавить потребителя");

        addCustomer.addClickListener(event -> {
            ConsumerView consumer = new ConsumerView();
            consumers.add(consumer);
            consumersForms.add(consumer);
        });

        add(
                name,
                connectionType,
                powerSwitchName,
                cabelName,
                addCustomer,
                consumersForms
        );
    }
}
