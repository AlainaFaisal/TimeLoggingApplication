package com.example.application.views.list;

import ch.qos.logback.core.Layout;
import com.example.application.data.Contact;
import com.example.application.data.Employee;
import com.example.application.services.CrmService;
import com.example.application.data.TimeEntry;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
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
    ComboBox<Employee> name = new ComboBox<>("Employee");
    Employee selectedEmployee= new Employee();
    private boolean isEditing = false;
    TimeForm form;
    CrmService service;


    public ListView(CrmService service) {
        this.service=service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();
        //form.setWidthFull();
        add(heading(), getContent());
        updateList();
    }

    private void closeEditor() {
        form.setTimeEntry(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void openEditor() {
        form.setVisible(true);
        addClassName("editing");
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
        form = new TimeForm(service.findAllProjects(), service.findAllEmployees(), service.findAllDistinctTimeEntries(), service.findAllTimeEntries());
      // form.setWidth("15em");
        //form.setColspan(1, );
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 5));

        form.getEmployeeComboBox().addValueChangeListener(event -> {
            selectedEmployee = event.getValue();
            if (selectedEmployee != null) {
                updateListWithName(selectedEmployee.getName());
            } else {
                grid.setItems();
            }
        });
        form.addSaveListener(this::saveTimeEntry);
        form.addDeleteListener(this::deleteTimeEntry);
        form.addClearListener(this::clearTimeEntry);
        form.addCreateNewListener(this::createNewEntry);
    }

    private void createNewEntry(TimeForm.CreateNewEvent event) {
        service.saveTimeEntry(event.getTimeEntry());
        Employee selected = form.getEmployeeComboBox().getValue();
        updateListWithName(selected.getName());
    }

    private void clearTimeEntry(TimeForm.ClearEvent clearEvent) {
        form.clearForm();

    }


    private void saveTimeEntry(TimeForm.SaveEvent event) {
        service.saveTimeEntry(event.getTimeEntry());
        Employee selected = form.getEmployeeComboBox().getValue();
        updateListWithName(selected.getName());
        openEditor();
    }

    private void deleteTimeEntry(TimeForm.DeleteEvent event) {
        service.deleteTimeEntry(event.getTimeEntry());
        Employee selected = form.getEmployeeComboBox().getValue();
        updateListWithName(selected.getName());
        openEditor();
    }

    private void updateListWithName(String employeeName) {
        grid.setItems(service.findAllProjectsforanEmployee(employeeName));
    }

    private void updateList() {
        grid.setItems(service.findAllTimeEntries());
    }

    private Component heading() {
        HorizontalLayout topbar = new HorizontalLayout(new H1("vaadin}>"));
        H4 text=new H4("home");
        text.addClassName("text-on-side");
        topbar.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        topbar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        topbar.setPadding(true);
        topbar.setWidthFull();
        topbar.addClassName("topbar");
        //topbar.add(text);
        return topbar;
    }

    private void configureGrid() {
        grid.addClassNames("timeEntry-grid");
        grid.setSizeFull();
        grid.setColumns("date");
        grid.addColumn(timeentry -> String.format("%.2f hours", timeentry.getHours())).setHeader("Hours Worked");
        grid.addColumn(TimeEntry::getDescription).setHeader("Description");
        grid.addColumn(timeentry -> timeentry.getProject().getName()).setHeader("Project");
        grid.addColumn(TimeEntry::getTimeCategory).setHeader("Time Category");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (!isEditing) {
                editEntry(event.getValue());
            }
            isEditing = false;
        });
    }



    private HorizontalLayout getToolbar() {
        name.setItems(service.findAllEmployees());
        name.setItemLabelGenerator(Employee::getName);
        name.setPlaceholder("Select Employee");
        name.setClearButtonVisible(true);

        Button selectButton = new Button("Select");
        selectButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        selectButton.addClickListener(event -> {
//            selectedEmployee = name.getValue();
//            if (selectedEmployee != null) {
//                updateListWithName(selectedEmployee.getName());
//            } else {
//                Notification.show("Please select an employee from the list.", 3000, Notification.Position.MIDDLE);
//            }
//            addEntry();
        });

        var toolbar = new HorizontalLayout(name, selectButton);
        toolbar.addClassName("toolbar");
        toolbar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.END); // Align components vertically
        toolbar.setAlignItems(FlexComponent.Alignment.BASELINE); // Ensure the button and ComboBox align at the baseline

        return toolbar;
    }
    public void editEntry(TimeEntry value) {
            isEditing = true;
            form.setTimeEntry(value);
            form.setVisible(true);
            addClassName("editing");
    }

    private void addEntry() {
        grid.asSingleSelect().clear();
        form.setVisible(true);
        editEntry(new TimeEntry());
    }
}