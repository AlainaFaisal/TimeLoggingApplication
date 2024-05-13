package com.example.application.views.list;


import com.example.application.data.Contact;
import com.example.application.data.Employee;
import com.example.application.data.Project;
import com.example.application.data.TimeEntry;
import com.example.application.services.CrmService;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;


import java.time.Duration;
import java.util.List;


public class TimeForm extends FormLayout {
    private TimeEntry timeEntry;
    Binder<TimeEntry> binder= new BeanValidationBinder<>(TimeEntry.class);

    ComboBox<Employee> employee = new ComboBox<>("Employee");
    ComboBox<Project> project = new ComboBox<>("Project");
    TextField description = new TextField("Description");
    TextField hours = new TextField("Hours");
    DatePicker date2 = new DatePicker("Date");
    TimePicker arrivalTime= new TimePicker("Arrival");
    TimePicker departureTime= new TimePicker("Departure");
    ComboBox<String> timeCategory = new ComboBox<>("Time Category");
    ComboBox<Duration> breakDuration = new ComboBox<>("Break Duration");

    Button close = new Button("Close");
    Button save = new Button("Save");
    Button delete = new Button("Delete");


    public TimeForm(List<Project> projects, List<Employee> employees, List<String> timeCategories, List<TimeEntry> timeEntries) {
        addClassName("time-form");
        binder.bindInstanceFields(this);
        binder.forField(date2)
                .bind(TimeEntry::getDate, TimeEntry::setDate);

        binder.forField(description)
                .bind(timeEntry -> timeEntry.getProject() != null ? timeEntry.getProject().getDescription() : "",
                        (timeEntry, desc) -> {
                            if (timeEntry.getProject() != null) {
                                timeEntry.getProject().setDescription(desc);
                            }
                        });

        employee.setItems(employees);
        employee.setItemLabelGenerator(Employee::getName);
        project.setItems(projects);
        project.setItemLabelGenerator(Project::getName);
        employee.addValueChangeListener(this::handleEmployeeSelectionChange);
        timeCategory.setItems(timeCategories);
        breakDuration.setItems(Duration.ofMinutes(15), Duration.ofMinutes(30), Duration.ofMinutes(45),  Duration.ofMinutes(60));
        breakDuration.setItemLabelGenerator(duration -> String.format("%d minutes", duration.toMinutes()));

        add(createBoxlayout2(), createBoxlayout(), createButtonsLayout());
    }

    public void setTimeEntry(TimeEntry timeEntry){
        binder.setBean(timeEntry);
    }

    private void handleEmployeeSelectionChange(HasValue.ValueChangeEvent<Employee> event) {
        Employee selectedEmployee = event.getValue();
        timeEntry.setEmployee(selectedEmployee);
        binder.readBean(timeEntry);
        fireEvent(new CustomValueChangeEvent(this, selectedEmployee != null ? selectedEmployee.getName() : null));
    }

    public static class CustomValueChangeEvent extends ComponentEvent<TimeForm> {
        private final String employeeName;

        public CustomValueChangeEvent(TimeForm source, String employeeName) {
            super(source, false);
            this.employeeName = employeeName;
        }

        public String getEmployeeName() {
            return employeeName;
        }
    }

    public Registration addCustomValueChangeListener(ComponentEventListener<CustomValueChangeEvent> listener) {
        return addListener(CustomValueChangeEvent.class, listener);
    }


    private HorizontalLayout createBoxlayout2() {
        return new HorizontalLayout( employee, date2, arrivalTime, breakDuration, departureTime);
    }
    private HorizontalLayout createBoxlayout() {
        return new HorizontalLayout(project, description, hours, timeCategory);
    }


    private HorizontalLayout createButtonsLayout() {
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
//            TimeEntry timeEntry = binder.getBean();
//            String employeeName = timeEntry.getEmployee().getName();
//            Notification.show("Time Entry saved successfully");
//            fireEvent(new SaveEvent(this, timeEntry, employeeName));
    }


    // Events
    public static class TimeFormEvent extends ComponentEvent<TimeForm> {
        private TimeEntry timeEntry;

        public TimeFormEvent(TimeForm source, TimeEntry timeEntry) {
            super(source, false);
            this.timeEntry = timeEntry;
        }

        public TimeEntry getTimeEntry() {
            return timeEntry;
        }
    }

    public static class SaveEvent extends TimeFormEvent {
        SaveEvent(TimeForm source, TimeEntry timeEntry) {
            super(source, timeEntry);
        }
    }

    public static class DeleteEvent extends TimeFormEvent {
        DeleteEvent(TimeForm source, TimeEntry timeEntry) {
            super(source, timeEntry);
        }
    }

    public static class CloseEvent extends TimeFormEvent {
        CloseEvent(TimeForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }
}
