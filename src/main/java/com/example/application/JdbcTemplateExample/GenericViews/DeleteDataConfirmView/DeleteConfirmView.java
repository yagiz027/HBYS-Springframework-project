package com.example.application.JdbcTemplateExample.GenericViews.DeleteDataConfirmView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class DeleteConfirmView extends Dialog{

    private Label deleteLabel;
    private HorizontalLayout buttonsLayout=new HorizontalLayout();
    private Button confirmButton;
    private Button cancelButton;

    public DeleteConfirmView(String deleteText) {
        this.deleteLabel=new Label(deleteText);

        confirmButton=new Button(new Icon("lumo","checkmark"));
        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR,ButtonVariant.LUMO_ICON);
        cancelButton=new Button(new Icon(VaadinIcon.CLOSE),e->close());

        buttonsLayout.add(confirmButton,cancelButton);
        
        add(deleteLabel,buttonsLayout);
    }

    public Button getConfirmButton(){
        return confirmButton;
    }
}
