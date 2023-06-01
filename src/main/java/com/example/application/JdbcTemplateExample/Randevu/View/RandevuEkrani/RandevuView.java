package com.example.application.JdbcTemplateExample.Randevu.View.RandevuEkrani;

import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.context.annotation.Scope;

import com.example.application.JdbcTemplateExample.MainLayout;
import com.example.application.JdbcTemplateExample.GenericViews.ErrorDialog.ErrorDialogView;
import com.example.application.JdbcTemplateExample.Hasta.Controller.HastaController;
import com.example.application.JdbcTemplateExample.Hasta.Controller.HastaKanGrupController;
import com.example.application.JdbcTemplateExample.Hasta.Model.Hasta;
import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelBolumController;
import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelController;
import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelKurumController;
import com.example.application.JdbcTemplateExample.Personel.Model.Personel;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurum;
import com.example.application.JdbcTemplateExample.Randevu.Controller.RandevuController;
import com.example.application.JdbcTemplateExample.Randevu.Model.Randevu;
import com.example.application.JdbcTemplateExample.Randevu.View.RandevuTakvimEkrani.RandevuDateSelectionView;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "hasta-randevu", layout = MainLayout.class)
@PageTitle("Randevu Ekleme Ekranı")
@Scope("prototype")
public class RandevuView extends HorizontalLayout {

    private DateTimePicker randevuBaslangicTarihDatePicker;
    private DateTimePicker randevuBitisTarihiDatePicker;

    private TextField randevuAlanHastaTcTextField;
    private ComboBox<Personel> randevuVerenDoktorComboBox;
    private ComboBox<PersonelBolum> randevuBolumComboBox;
    private ComboBox<PersonelKurum> randevuKurumComboBox;

    private Button randevuKaydetButton;
    private Button calendarButton;

    private TextField findHastaByName;
    private TextField findHastaByTC;
    private Grid<Hasta> randevuAlanHastaGrid;

    private static Hasta hasta = new Hasta();
    private static Personel personel = new Personel();
    private static Randevu randevu = new Randevu();

    private List<PersonelKurum> personelKurumList;
    private List<PersonelBolum> personelBolumList;
    private List<Personel> personelList;
    private List<Hasta> hastaList;

    private Binder<Randevu> randevuBinder = new Binder<>(Randevu.class, true);

    private RandevuController randevuController;

    private HastaController hastaController;

    private PersonelController personelController;

    private PersonelBolumController personelBolumController;

    private PersonelKurumController personelKurumController;

    private static HastaKanGrupController hastaKanGrupController;

    private ErrorDialogView errorDialogView;

    private RandevuDateSelectionView randevuDateSelectionView;

    public RandevuView(RandevuController randevuController,
            HastaController hastaController, PersonelController personelController,
            PersonelBolumController personelBolumController, PersonelKurumController personelKurumController,
            HastaKanGrupController hastaKanGrupController) {
        this.randevuController = randevuController;
        this.hastaController = hastaController;
        this.personelController = personelController;
        this.personelBolumController = personelBolumController;
        this.personelKurumController = personelKurumController;
        RandevuView.hastaKanGrupController = hastaKanGrupController;

        add(randevuMainVerticalLayout());
        setRandevuOperationFieldsStatus(false);
    }

    private VerticalLayout randevuMainVerticalLayout() {
        VerticalLayout aboveGridLayout = new VerticalLayout();

        HorizontalLayout filterLayout = new HorizontalLayout();

        findHastaByTC = new TextField();
        findHastaByTC.setPlaceholder("Hasta Tc");
        findHastaByTC.setValueChangeMode(ValueChangeMode.EAGER);

        findHastaByName = new TextField();
        findHastaByName.setValueChangeMode(ValueChangeMode.EAGER);
        findHastaByName.setPlaceholder("Hasta Adı/Soyadı");

        filterLayout.add(findHastaByTC, findHastaByName);

        aboveGridLayout.add(filterLayout, randevuMainHorizontalLayout());

        return aboveGridLayout;
    }

    private HorizontalLayout randevuMainHorizontalLayout() {
        HorizontalLayout randevuMainLayout = new HorizontalLayout();

        randevuMainLayout.add(buildHastaGrid(), buildRandevuAlFormLayout());
        randevuMainLayout.setSizeFull();

        return randevuMainLayout;
    }

    private Grid<Hasta> buildHastaGrid() {
        randevuAlanHastaGrid = new Grid<>(Hasta.class, false);

        hastaList = hastaController.findAllHasta();

        randevuAlanHastaGrid.setItems(hastaList);
        // Grid Columns Configuration
        randevuAlanHastaGrid.setSelectionMode(SelectionMode.MULTI);
        randevuAlanHastaGrid.addColumn(Hasta::getHastakimlikno).setHeader("Hasta NO");
        randevuAlanHastaGrid.addColumn(Hasta::getHastafirstName).setHeader("Hasta Adı");
        randevuAlanHastaGrid.addColumn(Hasta::getHastaLastName).setHeader("Hasta Soyadı");
        randevuAlanHastaGrid.addColumn(createToggleHastaDetails(randevuAlanHastaGrid));

        randevuAlanHastaGrid.setDetailsVisibleOnClick(false);
        randevuAlanHastaGrid.setItemDetailsRenderer(createHastaDetailsRenderer());

        randevuAlanHastaGrid.addSelectionListener(selection -> {
            Set<Hasta> selectedHastas = selection.getAllSelectedItems();
            try {
                if (selectedHastas.size() > 1) {
                    clearRandevuOperationFields();
                    setRandevuOperationFieldsStatus(false);
                } else {
                    randevuAlanHastaTcTextField
                            .setValue(selection.getFirstSelectedItem().get().getHastakimlikno().toString());
                    randevuBolumComboBox.setEnabled(true);
                    configureRandevuBinder();
                }
            } catch (RuntimeException runtimeException) {
                errorDialogView = new ErrorDialogView(runtimeException,
                        "Lütfen listeden bir hasta seçiniz.");
                clearRandevuOperationFields();
                setRandevuOperationFieldsStatus(false);
                errorDialogView.open();
            }
        });

        // Filter by Hasta FirstName Or LastName:
        ListDataProvider<Hasta> dataProvider = new ListDataProvider<>(hastaList);
        randevuAlanHastaGrid.setDataProvider(dataProvider);
        findHastaByName.addValueChangeListener(e -> {
            dataProvider.setFilter(h -> h.getHastafirstName().toLowerCase().contains(e.getValue().toLowerCase())
                    ||
                    h.getHastaLastName().toLowerCase().contains(e.getValue().toLowerCase()));
        });

        // Filter by TC NO:
        findHastaByTC.addValueChangeListener(e -> {
            dataProvider.setFilter(h -> h.getHastakimlikno().toString().contains(e.getValue()));
        });

        randevuAlanHastaGrid.setWidth(1000, Unit.EM);
        randevuAlanHastaGrid.getElement().getStyle().set("flex-grow", "1");
        randevuAlanHastaGrid.setHeight(40, Unit.EM);

        // Given Auto width to grid columns
        randevuAlanHastaGrid.getColumns().forEach(cols -> cols.setAutoWidth(true));

        return randevuAlanHastaGrid;
    }

    private FormLayout buildRandevuAlFormLayout() {
        FormLayout randevuAlOperationFormLayout = new FormLayout();
        randevuAlanHastaTcTextField = new TextField("Randevu Alacak Hasta TC");
        randevuAlanHastaTcTextField.setPlaceholder("Hasta Kimlik NO");

        // Load data into the lists from database
        personelKurumList = personelKurumController.getPersonelKurumList();
        personelBolumList = personelBolumController.getPersonelBolumList();

        randevuKurumComboBox = new ComboBox<>("Randevu Alınacak Kurum");
        randevuKurumComboBox.setPlaceholder("Kurum Seçiniz");

        randevuBolumComboBox = new ComboBox<>("Randevu Alınacak Bölüm");
        randevuBolumComboBox.setPlaceholder("Bölüm Seçiniz");

        randevuVerenDoktorComboBox = new ComboBox<>("Randevu Alınacak Doktor");
        randevuVerenDoktorComboBox.setPlaceholder("Doktor Seçiniz");

        if (randevuAlanHastaTcTextField.isEmpty() != true || randevuAlanHastaTcTextField.getValue() != null) {
            randevuBolumComboBox.setEnabled(true);
            randevuBolumComboBox.setItems(personelBolumList);
            randevuBolumComboBox.setItemLabelGenerator(p -> p.getPersonelBolumAdi());
        } else {
            randevuBolumComboBox.setHelperText(
                    "Bölüm listesini görebilmek için lütfen\nlisteden randevu işlemi yapılacak bir hasta seçiniz");
        }
        randevuBolumComboBox.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                randevuKurumComboBox.setEnabled(true);
                randevuKurumComboBox.setItems(personelKurumList);
                randevuKurumComboBox.setItemLabelGenerator(PersonelKurum::getKurumAdi);
            }
        });

        randevuKurumComboBox.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                randevuVerenDoktorComboBox.setEnabled(true);
                personelList = personelController.findAllPerson();
                // Filter personel by choosen randevuBolum and randevuKurum
                List<Personel> filteredPersonelList = personelList.stream()
                        .filter(p -> p.getPersonelBolum().getBolumId() == randevuBolumComboBox.getValue().getBolumId()
                                &&
                                p.getPersonelKurum().getKurumId() == randevuKurumComboBox.getValue().getKurumId())
                        .toList();
                randevuVerenDoktorComboBox.setItems(filteredPersonelList);
                randevuVerenDoktorComboBox.setItemLabelGenerator(p -> p.getPersonelAdi() + " " + p.getPersonelSoyadi());

                randevuBaslangicTarihDatePicker.setEnabled(true);
            }
        });
        randevuAlOperationFormLayout.setWidthFull();
        randevuAlOperationFormLayout.setHeight("auto");

        randevuAlOperationFormLayout.add(
                randevuAlanHastaTcTextField,
                randevuBolumComboBox,
                randevuKurumComboBox,
                randevuVerenDoktorComboBox,
                buildBaslangicDateTimePicker(),
                // Time selection list
                buildRandevuKaydetButtonlayout());

        return randevuAlOperationFormLayout;
    }

    private HorizontalLayout buildRandevuKaydetButtonlayout() {
        Footer buttonFooter = new Footer();
        randevuKaydetButton = new Button("Randevu Kaydet");

        // Add a new Randevu:
        randevuKaydetButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        randevuKaydetButton.addClickListener(e -> {
            Notification.show("Randevunuz başarılı bir şekilde kaydedildi.");
            saveNewRandevu();
        });
        buttonFooter.add(randevuKaydetButton);

        HorizontalLayout footerLayout = new HorizontalLayout(buttonFooter);
        footerLayout.setAlignItems(Alignment.CENTER);

        return footerLayout;
    }

    private VerticalLayout buildBaslangicDateTimePicker() {
        HorizontalLayout randevuBaslangicTarihLayout = new HorizontalLayout();

        DatePickerI18n dateFormat = new DatePickerI18n();
        dateFormat.setDateFormat("dd/MM/yyyy");

        randevuBaslangicTarihDatePicker = new DateTimePicker();
        randevuBaslangicTarihDatePicker.setLabel("Randevu Başlangıç Tarihi");
        randevuBaslangicTarihDatePicker.setDatePickerI18n(dateFormat);
        randevuBaslangicTarihDatePicker.setDatePlaceholder("Tarih");
        randevuBaslangicTarihDatePicker.setTimePlaceholder("Saat");
        randevuBaslangicTarihDatePicker.setEnabled(false);

        HorizontalLayout randevuBitisTarihLayout = new HorizontalLayout();
        randevuBitisTarihiDatePicker = new DateTimePicker();
        randevuBitisTarihiDatePicker.setLabel("Randevu Bitiş Tarihi");
        randevuBitisTarihiDatePicker.setDatePickerI18n(dateFormat);
        randevuBitisTarihiDatePicker.setDatePlaceholder("Tarih");
        randevuBitisTarihiDatePicker.setTimePlaceholder("Saat");
        randevuBitisTarihiDatePicker.setEnabled(false);

        calendarButton = new Button();
        calendarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        calendarButton.setIcon(new Icon(VaadinIcon.CALENDAR));
        calendarButton.setEnabled(false);
        randevuVerenDoktorComboBox.addValueChangeListener(selected -> {
            if (selected != null) {
                calendarButton.setEnabled(true);
                calendarButton.addClickListener(e -> {
                    randevuDateSelectionView = new RandevuDateSelectionView(randevuController, personelController,
                            selected.getValue(),
                            randevuAlanHastaTcTextField.getValue());
                    randevuDateSelectionView.open();
                    randevuBitisTarihLayout.setEnabled(true);

                    randevuDateSelectionView.dateTimeConsumer(
                            start -> randevuBaslangicTarihDatePicker.setValue(start),
                            end -> randevuBitisTarihiDatePicker.setValue(end));
                });
            }
        });

        randevuBaslangicTarihLayout.setAlignItems(Alignment.BASELINE);
        randevuBaslangicTarihLayout.add(randevuBaslangicTarihDatePicker, calendarButton);

        randevuBitisTarihLayout.add(randevuBitisTarihiDatePicker);

        VerticalLayout mainTarihOperationsLayout = new VerticalLayout();
        mainTarihOperationsLayout.add(randevuBaslangicTarihLayout, randevuBitisTarihLayout);

        return mainTarihOperationsLayout;
    }

    private void setRandevuOperationFieldsStatus(Boolean isEnabled) {
        randevuAlanHastaTcTextField.setEnabled(isEnabled);
        randevuKurumComboBox.setEnabled(isEnabled);
        randevuBolumComboBox.setEnabled(isEnabled);
        randevuVerenDoktorComboBox.setEnabled(isEnabled);
        randevuBaslangicTarihDatePicker.setEnabled(isEnabled);
    }

    private void clearRandevuOperationFields() {
        randevuAlanHastaTcTextField.clear();
        randevuKurumComboBox.clear();
        randevuBolumComboBox.clear();
        randevuVerenDoktorComboBox.clear();
        randevuBaslangicTarihDatePicker.clear();
    }

    private static Renderer<Hasta> createToggleHastaDetails(Grid<Hasta> randevuHastaGrid) {
        return LitRenderer
                .<Hasta>of(
                        "<vaadin-button theme=\"tertiary\" @click=\"${handleClick}\">Hasta Detayları</vaadin-button>")
                .withFunction("handleClick",
                        hasta -> randevuHastaGrid.setDetailsVisible(hasta,
                                !randevuHastaGrid.isDetailsVisible(hasta)));
    }

    private static ComponentRenderer<HastaDetailsForm, Hasta> createHastaDetailsRenderer() {
        return new ComponentRenderer<>(HastaDetailsForm::new, HastaDetailsForm::setHasta);
    }

    private static class HastaDetailsForm extends FormLayout {
        private final TextField hastaTc = new TextField();
        private final TextField hastaTelefon = new TextField();
        private final TextField hastaMail = new TextField();
        private final TextArea hastaAdres = new TextArea();
        private final TextField hastaEgitim = new TextField();
        private final TextField hastaDogumTarih = new TextField();
        private final TextField hastaCinsiyet = new TextField();
        private final TextField hastaYas = new TextField();
        private final TextField hastaKanGrubu = new TextField();

        public HastaDetailsForm() {
            Stream.of(
                    hastaTc,
                    hastaTelefon,
                    hastaMail,
                    hastaEgitim,
                    hastaDogumTarih,
                    hastaCinsiyet,
                    hastaYas,
                    hastaAdres,
                    hastaKanGrubu).forEach(field -> {
                        field.setReadOnly(true);
                        add(field);
                    });
        }

        private void setHasta(Hasta hasta) {
            hastaTc.setValue("Hasta TC:"+hasta.getHastakimlikno());
            hastaTelefon.setValue("Telefon: " + hasta.getHastaTelefon());
            hastaMail.setValue("E-mail: " + hasta.getHastaEmail());
            hastaAdres.setValue("Adres: " + hasta.getHastaAdres());
            hastaEgitim.setValue("Eğitim Düzeyi: " + hasta.getEducationStatus());
            hastaDogumTarih.setValue("Doğum Tarihi: " + hasta.getHastaDogumTarihi().toString());
            hastaCinsiyet.setValue("Cinsiyet: " + hasta.getHastaGender());
            hastaYas.setValue("Yaş: " + hasta.getHastaAge());
            hastaKanGrubu
                    .setValue("Kan Grubu: " + hastaKanGrupController.getKanGrup(hasta.getHastaKanGrup()).getKanGrup());
        }
    }

    private void configureRandevuBinder() {
        randevuBinder = new Binder<>(Randevu.class);
        
        randevuBinder.forField(randevuAlanHastaTcTextField).asRequired("Lütfen listeden bir hasta seçiniz")
                .bind(Randevu::getRandevuAlanHastaTc,Randevu::setRandevuAlanHastaTc);
        randevuBinder.forField(randevuBaslangicTarihDatePicker).asRequired("Lütfen randevu tarihini seçiniz")
                .withConverter(new LocalDateTimeToDateConverter(ZoneId.systemDefault()))
                .bind(Randevu::getRandevuBaslangicTarih, Randevu::setRandevuBaslangicTarih);
        randevuBinder.forField(randevuBitisTarihiDatePicker)
                .withConverter(new LocalDateTimeToDateConverter(ZoneId.systemDefault()))
                .bind(Randevu::getRandevuBitisTarih, Randevu::setRandevuBitisTarih);
        randevuBinder.forField(randevuVerenDoktorComboBox).asRequired("Lütfen alınacak doktoru seçiniz.")
                .bind(Randevu::getRandevuVerenDoktor, Randevu::setRandevuVerenDoktor);

    }

    private void saveNewRandevu() {
        try {
            randevuBinder.writeBean(randevu);
            randevuController.addRandevu(randevu);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}