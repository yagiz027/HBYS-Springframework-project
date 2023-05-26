package com.example.application.JdbcTemplateExample.Personel.Views.updatePersonelView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.function.Consumer;

import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelBolumController;
import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelKurumController;
import com.example.application.JdbcTemplateExample.Personel.Model.Personel;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurum;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;

public class updatePersonelView extends Dialog {

    private TextField personelAd;
    private TextField personelSoyadi;
    private EmailField personelEmail;
    private DatePicker personelDogumTarihi;
    private TextField personelPhone;
    private ComboBox<PersonelBolum> personelBolumList;
    private ComboBox<PersonelKurum> personelKurumList;

    private Button update;
    private Button cancel;

    private Consumer<Personel> updatePersonConsumer;
    private Binder<Personel> personUpdateBinder;

    private Personel personel = new Personel();
    private PersonelBolumController personelBolumController;
    private PersonelKurumController personelKurumController;

    public updatePersonelView(PersonelBolumController personelBolumController,
            PersonelKurumController personelKurumController) {
        this.personelBolumController = personelBolumController;
        this.personelKurumController = personelKurumController;

        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
        setDraggable(false);

        buildDialogUI();
        initDialogBinder();
    }

    public void updatePersonConsumer(Consumer<Personel> uConsumer) {
        this.updatePersonConsumer = uConsumer;
    }

    public void buildDialogUI() {

        H1 header=new H1();
        header.setClassName("personelUpdateDialogHeader");
        header.setText("Personel Güncelle");

        update = new Button("Update");
        update.setClassName("updateBtn");
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        update.addClickListener(e -> {
            validateAndUpdatePerson();
        });

        cancel = new Button("Cancel");
        cancel.setClassName("updateCancelBtn");
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);
        cancel.addClickListener(e -> this.close());

        HorizontalLayout buttonsFooter = new HorizontalLayout(update, cancel);

        HorizontalLayout mainDialogLayout = new HorizontalLayout(dialogDiv1(), dialogDiv2());

        add(header,mainDialogLayout,buttonsFooter);
    }

    private Div dialogDiv1() {
        personelAd = new TextField();
        personelAd.setClassName("firstName");

        personelSoyadi = new TextField();
        personelSoyadi.setClassName("lastName");

        personelEmail = new EmailField();
        personelSoyadi.setClassName("email");

        personelDogumTarihi = new DatePicker();
        personelDogumTarihi.setClassName("dateOfBirth");

        VerticalLayout dialogVerticalLayout1 = new VerticalLayout(personelAd, personelSoyadi, personelEmail,
                personelDogumTarihi);
        Div dialogDiv2 = new Div(dialogVerticalLayout1);
        return dialogDiv2;
    }

    private Div dialogDiv2() {
        personelPhone = new TextField();
        personelPhone.setClassName("phone");

        personelBolumList = new ComboBox<>();
        personelBolumList.setItems(personelBolumController.getPersonelBolumList());
        personelBolumList.setItemLabelGenerator(PersonelBolum::getPersonelBolumAdi);
        personelBolumList.setClassName("occupation");

        personelKurumList = new ComboBox<>();
        personelKurumList.setItems(personelKurumController.getPersonelKurumList());
        personelKurumList.setItemLabelGenerator(PersonelKurum::getKurumAdi);

        VerticalLayout dialogVerticalLayout2 = new VerticalLayout(personelPhone, personelBolumList, personelKurumList);
        Div dialogDiv2 = new Div(dialogVerticalLayout2);
        return dialogDiv2;
    }

    private void initDialogBinder() {
        personUpdateBinder = new Binder<>(Personel.class);

        personUpdateBinder.forField(personelAd).asRequired("Lütfen Personel Ad bilgisini giriniz.")
                .bind(Personel::getPersonelAdi, Personel::setPersonelAdi);
        personUpdateBinder.forField(personelSoyadi).asRequired("Lütfen Personel Soyad bilgisini giriniz.")
                .bind(Personel::getPersonelSoyadi, Personel::setPersonelSoyadi);
        personUpdateBinder.forField(personelEmail).asRequired("Lütfen Personel Email bilgisini giriniz.")
                .bind(Personel::getPersonelEmail, Personel::setPersonelEmail);
        personUpdateBinder.forField(personelDogumTarihi).asRequired("Lütfen Personel Doğum Tarihi bilgisini giriniz.")
                .withConverter(new LocalDateToDateConverter(ZoneId.systemDefault()))
                .bind(Personel::getPersonelDogumTarihi, Personel::setPersonelDogumTarihi);
        personUpdateBinder.forField(personelPhone).asRequired("Lütfen Personel Telefon bilgisini giriniz.")
                .bind(Personel::getPersonelPhone, Personel::setPersonelPhone);
        personUpdateBinder.forField(personelBolumList).asRequired("Lütfen Personel Bölüm bilgisini giriniz.")
                .bind(Personel::getPersonelBolum, Personel::setPersonelBolum);
        personUpdateBinder.forField(personelKurumList).asRequired("Lütfen Personel Kurumunu seçiniz.")
                .bind(Personel::getPersonelKurum,Personel::setPersonelKurum);
    }

    private void validateAndUpdatePerson() {
        try {
            personUpdateBinder.writeBean(personel);
            updatePersonConsumer.accept(personel);
        } catch (Exception e) {
            System.out.println("Error Message:" + e.getMessage());
            System.out.println("Error Cause:" + e.getCause());
        }
    }

    public void setPersonel(Personel personel) {
        this.personel = personel;
        personelAd.setValue(personel.getPersonelAdi());
        personelSoyadi.setValue(personel.getPersonelSoyadi());
        personelEmail.setValue(personel.getPersonelEmail());
        personelDogumTarihi.setValue(getPersonelDogumTarihiAsLocalDate());
        personelPhone.setValue(personel.getPersonelPhone());
        personelBolumList.setValue(personelBolumController.getPersonelBolumById(personel.getPersonelBolum().getBolumId()));
        personelKurumList.setValue(personelKurumController.getKurumByPersonelKurumId(personel.getPersonelKurum().getKurumId()));
    }

    private LocalDate getPersonelDogumTarihiAsLocalDate() {
        Date date = personel.getPersonelDogumTarihi();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        Instant instant = calendar.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        return localDate;
    }
}
