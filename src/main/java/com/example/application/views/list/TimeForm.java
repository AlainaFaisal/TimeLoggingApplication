package com.example.application.views.list;


import com.example.application.data.Employee;
import com.example.application.data.Project;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;


import java.util.List;


public class TimeForm extends FormLayout {
    ComboBox<Employee> employee = new ComboBox<>("Employee");
    ComboBox<Project> project = new ComboBox<>("Project");
    TextField Description = new TextField("Description");
    TextField hours = new TextField("Hours");
   // ComboBox<String> TimeCategory = new ComboBox<>("Time Category");
   private String selectedEmployeeName;

    Button update = new Button("Update");
    Button add = new Button("Add");


    public TimeForm(List<Project> projects, List<Employee> employees) {
        addClassName("time-form");


        employee.setItems(employees);
        employee.setItemLabelGenerator(Employee::getName);
        project.setItems(projects);
        project.setItemLabelGenerator(Project::getName);
        employee.addValueChangeListener(this::handleEmployeeSelectionChange);

        //TimeCategory.setItems()

        add(employee, createBoxlayout(), createButtonsLayout());
    }
    public void addEmployeeValueChangeListener() {
        employee.addValueChangeListener(e->{String value= String.valueOf(e.getValue());});

    }

    private void handleEmployeeSelectionChange(HasValue.ValueChangeEvent<Employee> event) {
        Employee selectedEmployee = event.getValue();
        if (selectedEmployee != null) {
            fireEvent(new CustomValueChangeEvent(this, selectedEmployee.getName()));
        } else {
            fireEvent(new CustomValueChangeEvent(this, null));
        }
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



    private HorizontalLayout createBoxlayout() {
        return new HorizontalLayout(project, Description,hours);
    }


    private HorizontalLayout createButtonsLayout() {
        update.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        add.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        update.addClickShortcut(Key.ENTER);

        return new HorizontalLayout(update, add);
    }
}
