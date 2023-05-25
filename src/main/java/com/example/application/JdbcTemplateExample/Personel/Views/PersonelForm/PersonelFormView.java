package com.example.application.JdbcTemplateExample.Personel.Views.PersonelForm;

import java.time.ZoneId;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.example.application.JdbcTemplateExample.MainLayout;
import com.example.application.JdbcTemplateExample.GenericViews.DeleteDataConfirmView.DeleteConfirmView;
import com.example.application.JdbcTemplateExample.GenericViews.ErrorDialog.ErrorDialogView;
import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelBolumController;
import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelController;
import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelKurumController;
import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelKurumTuruController;
import com.example.application.JdbcTemplateExample.Personel.Model.Personel;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurum;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurumTuru;
import com.example.application.JdbcTemplateExample.Personel.Views.updatePersonelView.updatePersonelView;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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

@Component
@PageTitle("Personel Kontrol Ekranı")
@Route(value = "", layout = MainLayout.class)
@Scope("prototype")
@Uses(Icon.class)
public class PersonelFormView extends HorizontalLayout {
    private PersonelController personelController;
    private PersonelBolumController personelBolumController;
    private PersonelKurumController personelKurumController;
    private PersonelKurumTuruController personelKurumTuruController;

    private updatePersonelView updateView;

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
    private Button clearFilterButton;

    private Grid<Personel> personelGrid = new Grid<>(Personel.class, false);

    private Binder<Personel> binder;

    private List<Personel> personelList;
    private GridListDataView<Personel> dataView;

    private HorizontalLayout gridOperationsLayout = new HorizontalLayout();

    private DeleteConfirmView deleteConfirmView;
    private ErrorDialogView errorDialogView;

    public PersonelFormView(PersonelController personelController, PersonelBolumController personelBolumController,
            PersonelKurumController personelKurumController, PersonelKurumTuruController personelKurumTuruController) {
        this.personelController = personelController;
        this.personelBolumController = personelBolumController;
        this.personelKurumController = personelKurumController;
        this.personelKurumTuruController = personelKurumTuruController;

        
        personelList = personelController.findAllPerson();
        personelGrid.setItems(personelList);
        dataView=personelGrid.getListDataView();
        
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

        personelKurumList = new ComboBox<>("Personel Kurum Seçiniz");
        personelKurumList.setItems(personelKurumController.getPersonelKurumList());
        personelKurumList.setItemLabelGenerator(PersonelKurum::getKurumAdi);

        cancel = new Button("İptal");
        cancel.addClickListener(e->clearFields());
        save = new Button("Personel Kaydet");

        save.addClickListener(e -> addNewPerson());
        save.addClickShortcut(com.vaadin.flow.component.Key.ENTER);

        HorizontalLayout buttonsLayout = new HorizontalLayout(save, cancel);
        buttonsLayout.setDefaultVerticalComponentAlignment(Alignment.END);
        Footer buttonsFooter = new Footer(buttonsLayout);

        VerticalLayout personOptionsLayout2 = new VerticalLayout(personelDogumTarihi, personelPhone, personelBolumList,
                personelKurumList);
        VerticalLayout personOptionsLayout = new VerticalLayout(personelAdi, personelSoyadi, personelEmail,
                buttonsFooter);

        HorizontalLayout personOptionsMainLayout = new HorizontalLayout(personOptionsLayout, personOptionsLayout2);

        return personOptionsMainLayout;
    }

    private void clearFields() {
        personelAdi.clear();
        personelSoyadi.clear();
        personelEmail.clear();
        personelDogumTarihi.clear();
        personelPhone.clear();
        personelBolumList.setValue(null);
        personelKurumList.setValue(null);   
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
        personelGrid.setClassName("personGrid");
        personelGrid.addColumn(Personel::getPersonelAdi).setHeader("Personel Adı").setFrozen(true);
        personelGrid.addColumn(Personel::getPersonelSoyadi).setHeader("Personel Soyadı").setFrozen(true);
        personelGrid.addColumn(Personel::getPersonelEmail).setHeader("Personel Email");
        personelGrid.addColumn(Personel::getPersonelDogumTarihi).setHeader("Personel Doğum Tarihi");
        personelGrid.addColumn(Personel::getPersonelPhone).setHeader("Personel Telefon");
        personelGrid.addColumn(p -> personelBolumController.getPersonelBolumById(p.getPersonelBolum().getBolumId())
                .getPersonelBolumAdi()).setHeader("Personel Bölümü");
        personelGrid.addColumn(
                k -> personelKurumController.getKurumByPersonelKurumId(k.getPersonelKurum().getKurumId()).getKurumAdi())
                .setHeader("Personel Kurumu");

        personelGrid.addColumn(new ComponentRenderer<>(Button::new, (button, person) -> {
            button.addThemeVariants(ButtonVariant.LUMO_ICON,
                    ButtonVariant.LUMO_ERROR,
                    ButtonVariant.LUMO_TERTIARY);
            button.addClickListener(e -> this.deletePerson(person));
            button.setIcon(new Icon(VaadinIcon.TRASH));
        })).setHeader("Manage").setFrozenToEnd(true);

        personelGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        personelGrid.setItems(personelController.findAllPerson());

        personelGrid.asSingleSelect().addValueChangeListener(e -> {
            if (e.getValue() == null) {
                updateView.close();
            } else {
                updateView = new updatePersonelView(personelBolumController, personelKurumController);

                updateView.setPersonel(e.getValue());

                updateView.setVisible(true);

                updateView.open();

                saveUptatedPerson(personel);
            }
        });

        VerticalLayout personGridLayout = new VerticalLayout(buildGridOperationsLayout(), personelGrid);
        return personGridLayout;
    }

    private HorizontalLayout buildGridOperationsLayout() {

        findPersonelByKurumTuruList = new ComboBox<>();
        findPersonelByKurumTuruList.setItems(personelKurumTuruController.getPersonelKurumTuruList());
        findPersonelByKurumTuruList.setItemLabelGenerator(PersonelKurumTuru::getKurumTuruAd);
        
        findPersonelByBolumList = new ComboBox<>();
        findPersonelByBolumList.setItems(personelBolumController.getPersonelBolumList());
        findPersonelByBolumList.setItemLabelGenerator(PersonelBolum::getPersonelBolumAdi);

        findPersonelByKurumTuruList.setLabel("Kurum Türüne Göre Sırala");
        findPersonelByKurumTuruList.setPlaceholder("Kurum Türü Seçiniz");
        findPersonelByKurumTuruList.setWidth(300, Unit.PIXELS);

        findPersonelByBolumList.setLabel("Personel Bölümüne Göre Sırala");
        findPersonelByBolumList.setPlaceholder("Personel Bölümü Seçiniz");
        findPersonelByBolumList.setWidth(300, Unit.PIXELS);

        findPerson = new TextField("Personel Ara");
        findPerson.setPlaceholder("Personel Adı/Soyadı Giriniz");
        findPerson.setWidth(300, Unit.PIXELS);
        findPerson.setClassName("findPersonelTxtField");

        clearFilterButton = new Button();
        clearFilterButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clearFilterButton.setIcon(new Icon("lumo", "cross"));
        clearFilterButton.addClickListener(r -> clearFilters());

        findPerson.setValueChangeMode(ValueChangeMode.EAGER);
        findPerson.addValueChangeListener(e -> {
            if (!e.getValue().isEmpty() || e.getValue() != null) {
                dataView.setFilter(p->p.getPersonelAdi().toLowerCase().contains(e.getValue().toLowerCase()));
                dataView.refreshAll();
            }
        });

        findPersonelByKurumTuruList.addValueChangeListener(e -> {
            PersonelKurumTuru selectedKurumTuru = e.getValue();
            if(selectedKurumTuru!=null){
                dataView.setFilter(p->personelKurumTuruController.getPersonelKurumTuruById(p.getPersonelKurum().getKurumTuruId()).getKurumTuruAd().contains(selectedKurumTuru.getKurumTuruAd()));
                dataView.refreshAll();
            }
        });

        findPersonelByBolumList.addValueChangeListener(e -> {
            PersonelBolum selectedBolum = e.getValue();
            if(selectedBolum!=null){
                dataView.setFilter(p->p.getPersonelBolum().getPersonelBolumAdi().contains(selectedBolum.getPersonelBolumAdi()));
                dataView.refreshAll();
            }
        });

        gridOperationsLayout.add(findPerson, findPersonelByBolumList, findPersonelByKurumTuruList, clearFilterButton);
        gridOperationsLayout.setDefaultVerticalComponentAlignment(Alignment.END);

        return gridOperationsLayout;
    }
    
    private void clearFilters(){
        dataView.removeFilters();
        findPerson.clear();
        findPersonelByBolumList.clear();
        findPersonelByKurumTuruList.clear();
    }

    public void saveUptatedPerson(Personel person) {
        updateView.updatePersonConsumer(pe -> {
            personelController
                    .updatePerson(new Personel(pe.getPersonelId(), pe.getPersonelAdi(), pe.getPersonelSoyadi(),
                            pe.getPersonelEmail(), pe.getPersonelPhone(), pe.getPersonelDogumTarihi(),
                            pe.getPersonelBolum(), pe.getPersonelKurum()));
            reloadPersonList();
            clearFilters();
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
                .bind(Personel::getPersonelBolum, Personel::setPersonelBolum);
        binder.forField(personelKurumList).asRequired("Lütfen personel kurumu seçiniz.")
                .bind(Personel::getPersonelKurum, Personel::setPersonelKurum);
    }

    private void deletePerson(Personel person) {
        deleteConfirmView=new DeleteConfirmView("Bu personeli silmek istediğinize emin misiniz?");
        deleteConfirmView.open();
        deleteConfirmView.getConfirmButton().addClickListener(e->{
            try{
                personelController.delete(person.getPersonelId());
                reloadPersonList();
                clearFilters();
                deleteConfirmView.close();
            }catch(DataIntegrityViolationException exception){
                errorDialogView=new ErrorDialogView(exception,"Bu personele ait bir randevu kaydı bulunmaktadır. Lütfen önce personelin randevu kaydını kaldırınız.");
                errorDialogView.open();
                deleteConfirmView.close();
            }
        });
    }

    private void reloadPersonList() {
        personelGrid.setItems(personelController.findAllPerson());
    }
}
