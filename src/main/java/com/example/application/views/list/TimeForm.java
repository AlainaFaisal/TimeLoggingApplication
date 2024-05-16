package com.example.application.views.list;


import com.example.application.data.Contact;
import com.example.application.data.Employee;
import com.example.application.data.Project;
import com.example.application.data.TimeEntry;
import com.example.application.services.CrmService;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import org.hibernate.event.spi.ClearEvent;


import java.time.Duration;
import java.time.LocalTime;
import java.util.List;


public class TimeForm extends FormLayout {
    CrmService service;
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
    Button save = new Button("Update");
    Button delete = new Button("Delete");
    Button clear = new Button("Clear");
    Button createNew = new Button("Create New");
    private String selectedEmployeeName;


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


        binder.forField(arrivalTime)
                .bind(TimeEntry::getArrivalTime, TimeEntry::setArrivalTime);

        binder.forField(departureTime)
                .bind(TimeEntry::getDepartureTime, TimeEntry::setDepartureTime);

        binder.forField(breakDuration)
                .asRequired("Break duration is required")
                .bind(TimeEntry::getBreakDuration, TimeEntry::setBreakDuration);
        binder.bind(hours, timeEntry -> {
            if (timeEntry.getArrivalTime() != null && timeEntry.getDepartureTime() != null && timeEntry.getBreakDuration() != null) {
                long minutesWorked = Duration.between(timeEntry.getArrivalTime(), timeEntry.getDepartureTime()).minus(timeEntry.getBreakDuration()).toMinutes();
                double hoursWorked = minutesWorked / 60.0;
                hoursWorked = Math.round(hoursWorked * 100.0) / 100.0;
                return String.format("%.2f", hoursWorked);
            }
            return "0.00"; // Default
        }, null);

        employee.setItems(employees);
        employee.setItemLabelGenerator(Employee::getName);
        project.setItems(projects);
        project.setItemLabelGenerator(Project::getName);
        //employee.addValueChangeListener(this::handleEmployeeSelectionChange);
        timeCategory.setItems(timeCategories);
        employee.addClassName("employee-combobox");
        arrivalTime.addClassName("arrival-field"); // Apply the custom CSS class to TextField
        date2.addClassName("date-picker");
        description.addClassName("description-box");
        breakDuration.addClassName("break-field");
        departureTime.addClassName("departure-field");
        breakDuration.setItems(Duration.ofMinutes(15), Duration.ofMinutes(30), Duration.ofMinutes(45),  Duration.ofMinutes(60));
        breakDuration.setItemLabelGenerator(duration -> String.format("%d minutes", duration.toMinutes()));

        add(createBoxlayout2(), createBoxlayout(), createButtonsLayout());
        setTimeEntry(new TimeEntry());
    }

    public void clearForm(){
        project.clear();
        employee.clear();
        description.clear();
        hours.clear();
        date2.clear();
        arrivalTime.clear();
        departureTime.clear();
        timeCategory.clear();
        breakDuration.setValue(Duration.ZERO);
        hours.setValue("0.00");
    }

    private void handleEmployeeSelectionChange(ValueChangeEvent<Employee> event) {
        Employee selectedEmployee = event.getValue();
        if (selectedEmployee != null) {
            selectedEmployeeName = selectedEmployee.getName();
        } else {
            selectedEmployeeName = null;
        }
    }
    public ComboBox<Employee> getEmployeeComboBox() {
        return employee;
    }

    public String getSelectedEmployeeName() {
        return selectedEmployeeName;
    }


    public void setTimeEntry(TimeEntry timeEntry){
        binder.setBean(timeEntry);
    }


    private Component createBoxlayout2() {
        H4 addHours = new H4("Add Hours");
        addHours.addClassName("h4-no-margin");

        HorizontalLayout first = new HorizontalLayout();
        first.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        first.setAlignItems(FlexComponent.Alignment.STRETCH);
        first.add(employee, date2, arrivalTime, breakDuration, departureTime);
        first.setWidthFull();
        first.setSpacing(true);

        return first;
    }
    private HorizontalLayout createBoxlayout() {
        HorizontalLayout second= new HorizontalLayout(project, description, hours, timeCategory);
        second.setFlexGrow(1, project);
        second.setFlexGrow(0.5, hours);
        second.setFlexGrow(9, timeCategory);
        second.setWidthFull();

        return second;
    }


    private HorizontalLayout createButtonsLayout() {
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        clear.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        createNew.addThemeVariants(ButtonVariant.LUMO_PRIMARY); // Styling the new button


        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));
        clear.addClickListener(event -> fireEvent(new ClearEvent(this,binder.getBean())));
        createNew.addClickListener(event -> newTimeEntry());

        save.addClickShortcut(Key.ENTER);
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(createNew, save, clear, close);
    }

    private void newTimeEntry() {
        if (binder.isValid()) {
//            TimeEntry newEntry = new TimeEntry();
//            binder.writeBeanIfValid(newEntry);
            fireEvent(new CreateNewEvent(this, binder.getBean()));
            setTimeEntry(new TimeEntry());
        }
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
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

    public static class ClearEvent extends TimeFormEvent {
        ClearEvent(TimeForm source, TimeEntry timeEntry) {
            super(source, timeEntry);
        }
    }

    public static class CloseEvent extends TimeFormEvent {
        CloseEvent(TimeForm source) {
            super(source, null);
        }
    }
    public static class CreateNewEvent extends TimeFormEvent {
        CreateNewEvent(TimeForm source, TimeEntry timeEntry) {
            super(source, timeEntry);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addClearListener(ComponentEventListener<ClearEvent> listener) {
        return addListener(ClearEvent.class, listener);
    }

    public Registration addCreateNewListener(ComponentEventListener<CreateNewEvent> listener) {
        return addListener(CreateNewEvent.class, listener);
    }
}
