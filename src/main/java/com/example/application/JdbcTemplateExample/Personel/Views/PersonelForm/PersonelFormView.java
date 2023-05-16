package com.example.application.JdbcTemplateExample.Personel.Views.PersonelForm;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.application.SpringUtils;
import com.example.application.JdbcTemplateExample.MainLayout;
import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelBolumController;
import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelController;
import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelKurumController;
import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelKurumTuruController;
import com.example.application.JdbcTemplateExample.Personel.Model.Personel;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurum;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurumTuru;
import com.example.application.JdbcTemplateExample.Personel.Views.updatePersonelView.updatePersonelView;
import com.example.application.JdbcTemplateExample.WebPageDialog.webPageDialog;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.PostConstruct;

@Component
@PageTitle("Personel Kontrol Ekranı")
@Route(value = "", layout = MainLayout.class)
@Scope("prototype")
@Uses(Icon.class)
public class PersonelFormView extends Div {
    @Autowired
    private SpringUtils springUtils;

    @Autowired
    private PersonelController personelController;

    @Autowired
    private PersonelBolumController personelBolumController;

    @Autowired
    private PersonelKurumController personelKurumController;

    @Autowired
    private PersonelKurumTuruController personelKurumTuruController;

    private updatePersonelView updateView;
    private webPageDialog pageDialog;

    private Personel personel = new Personel();

    private TextField personelAdi;
    private TextField personelSoyadi;
    private EmailField personelEmail;
    private DatePicker personelDogumTarihi;
    private TextField personelPhone;
    private ComboBox<PersonelBolum> personelBolumList;
    private ComboBox<PersonelKurum> personelKurumList;

    private TextField findPerson;
    private ComboBox<PersonelKurumTuru> findPersonelByKurumTuruList;
    private ComboBox<PersonelBolum> findPersonelByBolumList;

    private Button cancel;
    private Button save;
    private Button goToPage;
    private Button clearFilterButton;

    private Grid<Personel> personelGrid = new Grid<>(Personel.class, false);

    private Binder<Personel> binder;

    @PostConstruct
    public void init() {
        add(buildUI(), GridUI());
        initBinder();
        reloadPersonList();
    }

    public HorizontalLayout buildUI() {
        personelAdi = new TextField("Personel Adı");
        personelSoyadi = new TextField("Personel Soyadı");
        personelEmail = new EmailField("Personel Email");
        personelDogumTarihi = new DatePicker("Personel Doğum Tarihi");

        personelPhone = new TextField("Personel Telefon");
        personelBolumList = new ComboBox<>("Personel Bölüm Listesi");
        personelBolumList.setItems(personelBolumController.getPersonelBolumList());
        personelBolumList.setItemLabelGenerator(PersonelBolum::getPersonelBolumAdi);

        personelKurumList=new ComboBox<>("Personel Kurum Seçiniz");
        personelKurumList.setItems(personelKurumController.getPersonelKurumList());
        personelKurumList.setItemLabelGenerator(PersonelKurum::getKurumAdi);

        cancel = new Button("İptal");
        save = new Button("Personel Kaydet");
        goToPage = new Button("Got To WebPage");

        goToPage.addClickListener(e -> {
            addWebPage();
        });

        save.addClickListener(e -> addNewPerson());
        save.addClickShortcut(com.vaadin.flow.component.Key.ENTER);

        HorizontalLayout buttonsLayout=new HorizontalLayout(save, cancel, goToPage);
        buttonsLayout.setDefaultVerticalComponentAlignment(Alignment.END);
        Footer buttonsFooter = new Footer(buttonsLayout);

        VerticalLayout personOptionsLayout2 = new VerticalLayout(personelDogumTarihi, personelPhone, personelBolumList,personelKurumList);
        VerticalLayout personOptionsLayout = new VerticalLayout(personelAdi, personelSoyadi, personelEmail, buttonsFooter);

        HorizontalLayout personOptionsMainLayout = new HorizontalLayout(personOptionsLayout, personOptionsLayout2);

        return personOptionsMainLayout;
    }

    private void addNewPerson() {
        try {
            binder.writeBean(personel);
            personelController.add(personel);
            reloadPersonList();
        } catch (ValidationException e1) {
            System.out.println("Error Message:" + e1.getMessage());
        }
    }

    private VerticalLayout GridUI() {  
        findPersonelByKurumTuruList=new ComboBox<>();
        findPersonelByKurumTuruList.setItems(personelKurumTuruController.getPersonelKurumTuruList());
        findPersonelByKurumTuruList.setItemLabelGenerator(PersonelKurumTuru::getKurumTuruAd);

        findPersonelByBolumList=new ComboBox<>();
        findPersonelByBolumList.setItems(personelBolumController.getPersonelBolumList());
        findPersonelByBolumList.setItemLabelGenerator(PersonelBolum::getPersonelBolumAdi);

        personelGrid.setClassName("personGrid");
        personelGrid.addColumn(Personel::getPersonelAdi).setHeader("Personel Adı");
        personelGrid.addColumn(Personel::getPersonelSoyadi).setHeader("Personel Soyadı");
        personelGrid.addColumn(Personel::getPersonelEmail).setHeader("Personel Email");
        personelGrid.addColumn(Personel::getPersonelDogumTarihi).setHeader("Personel Doğum Tarihi");
        personelGrid.addColumn(Personel::getPersonelPhone).setHeader("Personel Telefon");
        personelGrid.addColumn(p->personelBolumController.getPersonelBolumById(p.getPersonelBolum().getBolumId()).getPersonelBolumAdi()).setHeader("Personel Bölümü");
        personelGrid.addColumn(k->personelKurumController.getKurumByPersonelKurumId(k.getPersonelKurum().getKurumId()).getKurumAdi()).setHeader("Personel Kurumu");
        
        findPerson=new TextField("Personel Ara");
        findPerson.setPlaceholder("Personel Adı/Soyadı Giriniz");
        findPerson.setWidth(300, Unit.PIXELS);
        findPerson.setClassName("findPersonelTxtField");

        List<Personel> personelList=personelController.findAllPerson();
        personelGrid.setItems(personelList);

        findPerson.setValueChangeMode(ValueChangeMode.EAGER);
        findPerson.addValueChangeListener(e -> {
            if (e.getValue() == null) {
                reloadPersonList();
            } else {
                personelGrid.setItems(personelList.stream()
                .filter(p-> p.getPersonelAdi().contains(e.getValue()))
                .collect(Collectors.toList()));
            }
        });
        
        findPersonelByKurumTuruList.setLabel("Kurum Türüne Göre Sırala");
        findPersonelByKurumTuruList.setPlaceholder("Kurum Türü Seçiniz");
        findPersonelByKurumTuruList.setWidth(300, Unit.PIXELS);

        findPersonelByBolumList.setLabel("Personel Bölümüne Göre Sırala");
        findPersonelByBolumList.setPlaceholder("Personel Bölümü Seçiniz");
        findPersonelByBolumList.setWidth(300, Unit.PIXELS);

        findPersonelByKurumTuruList.addValueChangeListener(e->{
            personelGrid.setItems(personelList.stream()
                            .filter(p->p.getPersonelKurum().getKurumTuruId()
                                .contains(e.getValue().getKurumturuId())).toList());
        });

        findPersonelByBolumList.addValueChangeListener(e->{
            personelGrid.setItems(personelList.stream()
                    .filter(p->p.getPersonelBolum().getPersonelBolumAdi()
                        .contains(e.getValue().getPersonelBolumAdi())).toList());
        });

        clearFilterButton=new Button();
        clearFilterButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clearFilterButton.setIcon(new Icon("lumo","cross"));

        personelGrid.addColumn(new ComponentRenderer<>(Button::new, (button, person) -> {
            button.addThemeVariants(ButtonVariant.LUMO_ICON,
                    ButtonVariant.LUMO_ERROR,
                    ButtonVariant.LUMO_TERTIARY);
            button.addClickListener(e -> this.deletePerson(person));
            button.setIcon(new Icon(VaadinIcon.TRASH));
        })).setHeader("Manage");

        personelGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        personelGrid.setItems(personelController.findAllPerson());

        personelGrid.asSingleSelect().addValueChangeListener(e -> {
            if (e.getValue() == null) {
                updateView.close();
            } else {
                updateView=new updatePersonelView(personelBolumController,personelKurumController);

                updateView.setPersonel(e.getValue());

                updateView.setVisible(true);

                updateView.open();

                saveUptatedPerson(personel);
            }
        });
        HorizontalLayout gridOperations=new HorizontalLayout(findPerson,findPersonelByBolumList,findPersonelByKurumTuruList,clearFilterButton);
        gridOperations.setDefaultVerticalComponentAlignment(Alignment.END);
        VerticalLayout personGridLayout = new VerticalLayout(gridOperations,personelGrid);
        return personGridLayout;
    }

    public void saveUptatedPerson(Personel person) {
        updateView.updatePersonConsumer(pe -> {
            personelController.updatePerson(new Personel(pe.getPersonelId(), pe.getPersonelAdi(), pe.getPersonelSoyadi(),
                    pe.getPersonelEmail(), pe.getPersonelPhone(), pe.getPersonelDogumTarihi(), pe.getPersonelBolum(),pe.getPersonelKurum()));
            reloadPersonList();
            updateView.close();
        });
    }

    private void initBinder() {
        binder = new BeanValidationBinder<>(Personel.class);

        binder.forField(personelAdi).asRequired("Bu alan boş olamaz")
                .bind(Personel::getPersonelAdi, Personel::setPersonelAdi);
        binder.forField(personelSoyadi).asRequired("Bu alan boş olamaz")
                .bind(Personel::getPersonelSoyadi, Personel::setPersonelSoyadi);
        binder.forField(personelEmail).asRequired("Bu alan boş olamaz")
                .bind(Personel::getPersonelEmail, Personel::setPersonelEmail);
        binder.forField(personelDogumTarihi).asRequired("Bu alan boş olamaz")
                .withConverter(new LocalDateToDateConverter(ZoneId.systemDefault()))
                .bind(Personel::getPersonelDogumTarihi, Personel::setPersonelDogumTarihi);
        binder.forField(personelPhone).asRequired("Bu alan boş olamaz")
                .bind(Personel::getPersonelPhone, Personel::setPersonelPhone);
        binder.forField(personelBolumList).asRequired("Bu alan boş olanaz")
                .bind(Personel::getPersonelBolum,Personel::setPersonelBolum);
        binder.forField(personelKurumList).asRequired("Lütfen personel kurumu seçiniz.")
                .bind(Personel::getPersonelKurum,Personel::setPersonelKurum);
    }

    private void deletePerson(Personel person) {
        personelController.delete(person.getPersonelId());
        reloadPersonList();
    }

    private void reloadPersonList() {
        personelGrid.setItems(personelController.findAllPerson());
    }

    private void addWebPage() {
        pageDialog = springUtils.getBean(webPageDialog.class);
        pageDialog.open();
    }
}
