package com.example.application.views.list;

import ch.qos.logback.core.Layout;
import com.example.application.data.Contact;
import com.example.application.data.Employee;
import com.example.application.services.CrmService;
import com.example.application.data.TimeEntry;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;

@Route(value = "")
@PageTitle("Time Logging Application")
public class ListView extends VerticalLayout {
    Grid<TimeEntry> grid = new Grid<>(TimeEntry.class);
    TextField filterText = new TextField();
    TimeForm form;
   CrmService service;


    public ListView(CrmService service) {
        this.service=service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(heading(), getContent());
       // updateList();
    }
    private Component getContent() {
        VerticalLayout content = new VerticalLayout(form, grid);
        content.setFlexGrow(1, form);
        content.setFlexGrow(2, grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }
    private void configureForm() {
        form = new TimeForm(service.findAllProjects(), service.findAllEmployees());
        form.setWidth("25em");

        form.addCustomValueChangeListener(event -> {
            String employeeName = event.getEmployeeName();
            if (employeeName != null && !employeeName.isEmpty()) {
                updateList(employeeName);
            } else {
                grid.setItems();  // This might need to be grid.setItems(Collections.emptyList()) depending on your grid's API
            }
        });

    }

    private void updateList(String employeeName) {
        grid.setItems(service.findAllProjectsforanEmployee(employeeName));
    }


    private Component heading() {

        var topbar = new HorizontalLayout(new H1("Vaadin"));
        topbar.addClassName("topbar");
        return topbar;
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("date");
        grid.addColumn(timeentry -> String.format("%.2f hours", timeentry.getHoursWorked())).setHeader("Hours Worked");
        grid.addColumn(timeentry -> timeentry.getProject().getDescription()).setHeader("Description");
        grid.addColumn(timeentry -> timeentry.getProject().getName()).setHeader("Project");
        grid.addColumn(TimeEntry::getTimeCategory).setHeader("Time Category");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {

        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button updateButton = new Button("Search");
        Button addButton = new Button("Add");
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        var toolbar = new HorizontalLayout(filterText, updateButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
}