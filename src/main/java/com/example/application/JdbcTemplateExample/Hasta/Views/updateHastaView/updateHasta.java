package com.example.application.JdbcTemplateExample.Hasta.Views.updateHastaView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.function.Consumer;

import com.example.application.JdbcTemplateExample.Hasta.Controller.HastaKanGrupController;
import com.example.application.JdbcTemplateExample.Hasta.Model.Hasta;
import com.example.application.JdbcTemplateExample.Hasta.Model.HastaKanGrup;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.data.value.ValueChangeMode;

public class updateHasta extends Dialog {

    private TextField hastaIdField;
    private TextField hastaFirstNameTextField;
    private TextField hastaLastNameTextField;
    private TextField hastaEmail;
    private TextField hastaTelefon;
    private TextArea hastaAdres;
    private ComboBox<String> educationStatus;
    private DatePicker hastaDogumTarihi;
    private RadioButtonGroup<String> hastaGender;
    private ComboBox<HastaKanGrup> hastaKanGrup;
    private int hastaAge;

    private Button updateHastaButton;
    private Button cancelUpdateButton;

    private Consumer<Hasta> updateHastaConsumer;
    private Binder<Hasta> hastaUpdateBinder;
    private Hasta hasta = new Hasta();
    private HastaKanGrup kanGrup;

    private HastaKanGrupController kanGrupController;

    public updateHasta(HastaKanGrupController kanGrupController, HastaKanGrup kanGrup) {
        this.kanGrupController = kanGrupController;
        this.kanGrup = kanGrup;

        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
        setDraggable(false);

        dialogMain();
        initUpdateHastaBinder();
    }

    public void updateHastaConsumer(Consumer<Hasta> updateHastaConsumer) {
        this.updateHastaConsumer = updateHastaConsumer;
    }

    public Div updateGrid1() {
        hastaIdField = new TextField();
        hastaIdField.setPlaceholder("Hasta Kimlik NO");

        hastaFirstNameTextField = new TextField();
        hastaFirstNameTextField.setPlaceholder("Hasta Adı");

        hastaLastNameTextField = new TextField();
        hastaLastNameTextField.setPlaceholder("Hasta Soyadı");

        educationStatus = new ComboBox<String>();
        educationStatus.setItems("İlk Okul", "Orta Okul", "Lise", "Üniversite");
        educationStatus.setPlaceholder("Eğitim Durumu");
        educationStatus.setValue(hasta.getEducationStatus());

        hastaGender = new RadioButtonGroup<String>();
        hastaGender.setItems("Erkek", "Kadın", "Diğer");

        hastaKanGrup = new ComboBox<HastaKanGrup>();
        hastaKanGrup.setItems(kanGrupController.getKanGrupList());
        hastaKanGrup.setItemLabelGenerator(HastaKanGrup::getKanGrup);
        hastaKanGrup.setPlaceholder("Kan Grubu");

        VerticalLayout updateLayout1 = new VerticalLayout();
        updateLayout1.add(hastaIdField, hastaFirstNameTextField, hastaLastNameTextField, educationStatus, hastaGender,
                hastaKanGrup);
        Div updateGridDiv1 = new Div(updateLayout1);

        return updateGridDiv1;
    }

    public Footer buttonFooter() {
        HorizontalLayout buttonLayout = new HorizontalLayout(updateHastaButton, cancelUpdateButton);
        buttonLayout.setSpacing(true);
        buttonLayout.setPadding(false);
        return new Footer(buttonLayout);
    }

    public void dialogMain() {
        HorizontalLayout gridMainLayout = new HorizontalLayout(updateGrid1(), updateGrid2());

        H1 gridTitle = new H1("Hasta Güncelle");
        gridTitle.setClassName("updateDialogTitle");

        updateHastaButton = new Button("Güncelle");
        updateHastaButton.setClassName("updateButton");
        updateHastaButton.addClickListener(e -> validateAndUpdateHasta());

        cancelUpdateButton = new Button("İptal");
        cancelUpdateButton.setClassName("cancelButton");
        cancelUpdateButton.addClickListener(e -> this.close());

        add(gridTitle, gridMainLayout, updateHastaButton, cancelUpdateButton);
    }

    public Div updateGrid2() {
        hastaDogumTarihi = new DatePicker();
        hastaDogumTarihi.setPlaceholder("Dogum Tarihi");

        hastaEmail = new TextField();
        hastaEmail.setPlaceholder("E-Mail");

        hastaTelefon = new TextField();
        hastaTelefon.setPlaceholder("Telefon");

        int adresMaxLength=140;
        hastaAdres = new TextArea();
        hastaAdres.setMaxLength(adresMaxLength);
        hastaAdres.setPlaceholder("Adres");
        hastaAdres.setValueChangeMode(ValueChangeMode.EAGER);
        hastaAdres.addValueChangeListener(e -> e.getSource()
                .setHelperText(e.getValue().length() + "/" + adresMaxLength));

        VerticalLayout updateGridLayout2 = new VerticalLayout(hastaDogumTarihi, hastaEmail, hastaTelefon, hastaAdres);
        Div updateGridDiv2 = new Div(updateGridLayout2);

        return updateGridDiv2;
    }

    public void initUpdateHastaBinder() {
        hastaUpdateBinder = new BeanValidationBinder<>(Hasta.class);
        hastaUpdateBinder.forField(hastaFirstNameTextField).asRequired("Hasta adı alanı zorunludur.")
                .bind(Hasta::getHastafirstName, Hasta::setHastafirstName);
        hastaUpdateBinder.forField(hastaLastNameTextField).asRequired("Hasta soyadı alanı zorunludur.")
                .bind(Hasta::getHastaLastName, Hasta::setHastaLastName);
        hastaUpdateBinder.forField(hastaEmail).asRequired("Hasta email alanı zorunludur.")
                .bind(Hasta::getHastaEmail, Hasta::setHastaEmail);
        hastaUpdateBinder.forField(hastaTelefon).asRequired("Hasta telefon alanı zorunludur.")
                .bind(Hasta::getHastaTelefon, Hasta::setHastaTelefon);
        hastaUpdateBinder.forField(hastaAdres).asRequired("Hasta adres alanı zorunludur.")
                .bind(Hasta::getHastaAdres, Hasta::setHastaAdres);
        hastaUpdateBinder.forField(hastaGender).asRequired("Hasta cinsiyet alanı zorunludur.")
                .bind(Hasta::getHastaGender,Hasta::setHastaGender);
        hastaUpdateBinder.forField(hastaDogumTarihi).asRequired("Hasta dogum tarihi alanı zorunludur.")
                .withConverter(new LocalDateToDateConverter(ZoneId.systemDefault()))
                .bind(Hasta::getHastaDogumTarihi, Hasta::setHastaDogumTarihi);
        hastaUpdateBinder.forField(educationStatus).asRequired("Hasta durumu alanı zorunludur.")
                .bind(Hasta::getEducationStatus, Hasta::setEducationStatus);
        hastaUpdateBinder.forField(hastaKanGrup).asRequired("Hasta kan grubu alanı zorunludur.")
                .bind(Hasta::getHastaKanGrup, Hasta::setHastaKanGrup);
    }

    public void setHasta(Hasta hasta) {
        this.hasta = hasta;

        hastaIdField.setValue(hasta.getHastakimlikno().toString());
        hastaIdField.setEnabled(false);
        hastaFirstNameTextField.setValue(hasta.getHastafirstName());
        hastaLastNameTextField.setValue(hasta.getHastaLastName());
        hastaEmail.setValue(hasta.getHastaEmail());
        hastaDogumTarihi.setValue(getHastaDogumTarihiAsLocalDate());
        educationStatus.setValue(hasta.getEducationStatus());
        hastaTelefon.setValue(hasta.getHastaTelefon());
        hastaAdres.setValue(hasta.getHastaAdres());
        hastaGender.setValue(hasta.getHastaGender());
        hastaKanGrup.setValue(this.kanGrup);
        hastaAge=hasta.getHastaAge();
    }

    public void validateAndUpdateHasta() {
        try {
            hastaUpdateBinder.writeBean(hasta);
            updateHastaConsumer.accept(hasta);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    private LocalDate getHastaDogumTarihiAsLocalDate() {
        Date date = hasta.getHastaDogumTarihi();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        Instant instant = calendar.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        return localDate;
    }
}
