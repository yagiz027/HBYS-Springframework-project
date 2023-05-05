package com.example.application.JdbcTemplateExample.Hasta.Views.HastaEkleView;

import java.text.NumberFormat;
import java.time.ZoneId;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.application.JdbcTemplateExample.MainLayout;
import com.example.application.JdbcTemplateExample.ErrorDialog.ErrorDialogView;
import com.example.application.JdbcTemplateExample.Hasta.Controller.HastaController;
import com.example.application.JdbcTemplateExample.Hasta.Controller.HastaKanGrupController;
import com.example.application.JdbcTemplateExample.Hasta.Model.Hasta;
import com.example.application.JdbcTemplateExample.Hasta.Model.HastaKanGrup;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ErrorLevel;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.PostConstruct;

@Component
@PageTitle("Hasta Kontrol Ekranı")
@Route(value = "hasta-page", layout = MainLayout.class)
@Scope("prototype")
public class hastaEkleView extends HorizontalLayout {

    @Autowired
    private HastaController hastaController;

    @Autowired
    private HastaKanGrupController kanGrupController;

    private TextField hastaIdField;
    private TextField hastaFirstNameTextField;
    private TextField hastaLastNameTextField;
    private EmailField hastaEmail;
    private TextField hastaTelefon;
    private TextArea hastaAdres;
    private Select<String> educationStatus;
    private DatePicker hastaDogumTarihi;
    private RadioButtonGroup<String> hastaGender;
    private ComboBox<HastaKanGrup> hastaKanGrup;

    private Button saveHastaBtn;

    private Binder<Hasta> hastaBinder;

    private Hasta hasta = new Hasta();

    private ErrorDialogView errorDialogView;

    @PostConstruct
    public void init() {
        hastaVerticalLayout1();
        hastaVerticalLayout2();
        initHastaBinder();
    }

    public void hastaVerticalLayout1() {
        hastaIdField = new TextField("Hasta TC Kimlik NO");
        hastaIdField.setClassName("hastaID");
        hastaIdField.setMaxLength(11);
        hastaIdField.setHelperText("Kimlik NO en fazla 11 karakter olmalı.");

        hastaFirstNameTextField = new TextField("Hasta Adı");
        hastaFirstNameTextField.setClassName("hastaName");

        hastaLastNameTextField = new TextField("Hasta Soyadı");
        hastaLastNameTextField.setClassName("hastLastName");

        educationStatus = new Select<>();
        educationStatus.setClassName("hastaEducation");
        educationStatus.setLabel("Eğitim durumuzu seçiniz");
        educationStatus.setItems("İlk Okul", "Orta Okul", "Lise", "Üniversite");

        hastaGender = new RadioButtonGroup<>();
        hastaGender.setClassName("hastaGender");
        hastaGender.setLabel("Hasta Cinsiyet");
        hastaGender.setItems("Erkek", "Kadın", "Diğer");

        saveHastaBtn = new Button("Hasta Kaydet");
        saveHastaBtn.setClassName("saveHasta");

        saveHastaBtn.addClickListener(e -> {
            try {
                ValidateAndSaveHasta();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        VerticalLayout hastaOperationsLayout = new VerticalLayout(hastaIdField, hastaFirstNameTextField,
                hastaLastNameTextField,
                educationStatus, hastaGender, saveHastaBtn);
        Div contentHastaView = new Div(hastaOperationsLayout);

        add(contentHastaView);
    }

    public void hastaVerticalLayout2() {
        hastaDogumTarihi = new DatePicker("Doğum Tarihinizi Seçiniz");
        hastaDogumTarihi.setClassName("hastaDogum");

        hastaTelefon = new TextField("Hasta Telefon NO");
        hastaTelefon.setClassName("hastaTelefon");
        hastaTelefon.setMaxLength(11);

        hastaEmail = new EmailField("Email Adresinizi Giriniz");
        hastaEmail.setClassName("hastaEmail");
        hastaEmail.setHelperText("email@example.com");

        hastaKanGrup = new ComboBox<HastaKanGrup>("Kan Grubunuzu Seçiniz");
        hastaKanGrup.setClassName("hastaKan");
        hastaKanGrup.setItems(kanGrupController.getKanGrupList());
        hastaKanGrup.setItemLabelGenerator(HastaKanGrup::getKanGrup);

        int adresAreaCharLimit = 140;
        hastaAdres = new TextArea("İkametgah Adresinizi Giriniz");
        hastaAdres.setClassName("hastaAdres");
        hastaAdres.setMaxLength(adresAreaCharLimit);
        hastaAdres.setValueChangeMode(ValueChangeMode.EAGER);
        hastaAdres.addValueChangeListener(e -> e.getSource()
                .setHelperText(e.getValue().length() + "/" + adresAreaCharLimit));

        VerticalLayout hastaOperationsLayout2 = new VerticalLayout(hastaDogumTarihi, hastaTelefon, hastaEmail,
                hastaKanGrup, hastaAdres);
        Div contentHastView2 = new Div(hastaOperationsLayout2);

        add(contentHastView2);
    }

    private StringToLongConverter CustomConverter() {
        StringToLongConverter idConverter = new StringToLongConverter("Lütfen sayısal bir değer giriniz.") {
            protected java.text.NumberFormat getFormat(Locale hastaID) {
                NumberFormat format = super.getFormat(hastaID);
                format.setGroupingUsed(false);
                return format;
            }
        };
        return idConverter;
    }

    public void initHastaBinder() {
        hastaBinder = new BeanValidationBinder<>(Hasta.class);

        hastaBinder.forField(hastaIdField)
                .withValidator(hastaIdField -> isValid() == true, "Lütfen geçerli bir TC Kimlik numarası giriniz.",ErrorLevel.CRITICAL)
                .withConverter(CustomConverter()).asRequired("Tc alanı boş olamaz")
                .bind(Hasta::getHastakimlikno, Hasta::setHastakimlikno);
        hastaBinder.forField(hastaFirstNameTextField).asRequired("Hasta Adı alanı boş olamaz")
                .bind(Hasta::getHastafirstName, Hasta::setHastafirstName);
        hastaBinder.forField(hastaLastNameTextField).asRequired("Hasta Soyadı alanı boş olamaz")
                .bind(Hasta::getHastaLastName, Hasta::setHastaLastName);
        hastaBinder.forField(hastaEmail).asRequired("Email alanı boş olamaz")
                .bind(Hasta::getHastaEmail, Hasta::setHastaEmail);
        hastaBinder.forField(hastaTelefon).asRequired("Hasta Telefon numarası boş olamaz")
                .bind(Hasta::getHastaTelefon, Hasta::setHastaTelefon);
        hastaBinder.forField(hastaAdres).asRequired("İkametgah alanı boş olamaz")
                .bind(Hasta::getHastaAdres, Hasta::setHastaAdres);
        hastaBinder.forField(educationStatus).asRequired("Eğitim durumunu seçiniz")
                .bind(Hasta::getEducationStatus, Hasta::setEducationStatus);
        hastaBinder.forField(hastaDogumTarihi).asRequired("Doğum Tarihi alanı boş olamaz")
                .withConverter(new LocalDateToDateConverter(ZoneId.systemDefault()))
                .bind(Hasta::getHastaDogumTarihi, Hasta::setHastaDogumTarihi);
        hastaBinder.forField(hastaGender).asRequired("Lütfen seçeneklerden birini seçiniz.")
                .bind(Hasta::getHastaGender, Hasta::setHastaGender);
        hastaBinder.forField(hastaKanGrup).asRequired("Lütfen kan grubunu seçiniz.")
        .bind(Hasta::getHastaKanGrup, Hasta::setHastaKanGrup);
    }

    private boolean isValid() {
        String strNumber = hastaIdField.getValue();
        if (strNumber.length() != 11 || strNumber.charAt(0) == '0') {
            return false;
        }
        int oddSum = 0, evenSum = 0, controlDigit = 0;
        for (int i = 0; i <= 8; i++) {
            if (i % 2 == 0) {
                oddSum += Character.getNumericValue(strNumber.charAt(i));

            } else {
                evenSum += Character.getNumericValue(strNumber.charAt(i));
            }
        }
        controlDigit = (oddSum * 7 - evenSum) % 10;
        if (Character.getNumericValue(strNumber.charAt(9)) != controlDigit) {
            return false;
        }
        if (Character.getNumericValue(strNumber.charAt(10)) != (controlDigit + evenSum + oddSum) % 10) {
            return false;
        }
        return true;
    }

    public void ValidateAndSaveHasta() throws ValidationException {
        try {
            hastaBinder.writeBean(hasta);
            hastaController.addHasta(hasta);
        } catch (Exception exception) {
            errorDialogView=new ErrorDialogView(exception,"Lütfen Alanları eksiksiz olarak doldurduğunuzdan emin olunuz.");
            errorDialogView.open();
            exception.printStackTrace();
        } 
    }
}
