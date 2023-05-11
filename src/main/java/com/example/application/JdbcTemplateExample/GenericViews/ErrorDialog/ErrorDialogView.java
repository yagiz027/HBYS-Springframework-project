package com.example.application.JdbcTemplateExample.GenericViews.ErrorDialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ErrorDialogView extends Dialog {
    
    private String errorMessage;
    private Label errorLabel;
    private Button closeButton;

    public ErrorDialogView(Exception errorType, String errorMessage) {
        this.errorMessage=errorMessage;

        buildErrorDialog();
    }

    private void buildErrorDialog(){
        VerticalLayout errorLayout=new VerticalLayout();
        errorLabel=new Label();
        errorLabel.setText(errorMessage);
        errorLabel.setHeight("center");
        errorLabel.getStyle().set("color", "red");
        errorLabel.getStyle().set("font-weight", "bold");
        errorLabel.getStyle().set("font-size", "16px");

        closeButton=new Button("Tamam");
        closeButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
        closeButton.addClickListener(e->{
            this.close();
        });

        errorLayout.setAlignItems(Alignment.CENTER);
        errorLayout.setSpacing(true);
        errorLayout.setPadding(true);
        errorLayout.add(errorLabel,closeButton);

        add(errorLayout);
    }
}
