package com.example.application.JdbcTemplateExample.Randevu.View.RandevuListView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.dao.DataIntegrityViolationException;

import com.example.application.JdbcTemplateExample.MainLayout;
import com.example.application.JdbcTemplateExample.GenericViews.DeleteDataConfirmView.DeleteConfirmView;
import com.example.application.JdbcTemplateExample.GenericViews.ErrorDialog.ErrorDialogView;
import com.example.application.JdbcTemplateExample.Hasta.Controller.HastaController;
import com.example.application.JdbcTemplateExample.Hasta.Model.Hasta;
import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelBolumController;
import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelController;
import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelKurumController;
import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelKurumTuruController;
import com.example.application.JdbcTemplateExample.Personel.Model.Personel;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurum;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurumTuru;
import com.example.application.JdbcTemplateExample.Randevu.Controller.RandevuController;
import com.example.application.JdbcTemplateExample.Randevu.Model.Randevu;
import com.example.application.JdbcTemplateExample.ValueConverters.DateToLocalDateUtil;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "randevu-list", layout = MainLayout.class)
public class RandevuListView extends HorizontalLayout {

    private Grid<Randevu> randevuGrid = new Grid<>(Randevu.class, false);
    private TextField randevuHastaTCAra;
    private TextField randevuHastaAra;
    private ComboBox<PersonelBolum> randevuBolumAra;
    private ComboBox<PersonelKurum> randevuKurumAra;
    private MultiSelectComboBox<PersonelKurumTuru> randevuKurumTuruGroup;
    private DatePicker randevuTarihAralik;
    private Button clearFiltersButton;
    private Button nextDayButton;
    private Button previousDayButton;
    private Button nextWeekButton;
    private Button previousWeekButton;

    private PersonelController personelController;
    private RandevuController randevuController;
    private HastaController hastaController;
    private PersonelBolumController personelBolumController;
    private PersonelKurumController personelKurumController;
    private PersonelKurumTuruController personelKurumTuruController;

    private List<Personel> personelList;
    private List<Hasta> hastaList;
    private List<Randevu> randevuList;
    private List<PersonelKurumTuru> personelKurumTuruList;

    private Consumer<String> filterChangeConsumer;

    private Randevu randevu;
    private RandevuListDetailsView randevuListDetailsView;

    private VerticalLayout randevuListViewMainLayout = new VerticalLayout();
    private VerticalLayout gridAndSearchBarLayout = new VerticalLayout();
    private VerticalLayout detailsVerticalLayout = new VerticalLayout();
    private VerticalLayout randevuKurumTuruCheckBoxGroupLayout = new VerticalLayout();
    private VerticalLayout randevuHastaAraLayout = new VerticalLayout();
    private VerticalLayout randevuKurumBolumAraLayout = new VerticalLayout();
    private HorizontalLayout gridDateOptionLayout = new HorizontalLayout();
    private HorizontalLayout leftSideLayout = new HorizontalLayout();
    private HorizontalLayout rightSideLayout = new HorizontalLayout();

    private ListDataProvider<Randevu> dataProvider;
    private DeleteConfirmView deleteConfirmView;
    private ErrorDialogView errorDialogView;
    private List<String> selectedItemsIds;  

    private Div detailsDiv;

    public RandevuListView(PersonelController personelController, RandevuController randevuController,
            HastaController hastaController, PersonelBolumController personelBolumController,
            PersonelKurumController personelKurumController, PersonelKurumTuruController personelKurumTuruController) {
        this.personelController = personelController;
        this.randevuController = randevuController;
        this.hastaController = hastaController;
        this.personelBolumController = personelBolumController;
        this.personelKurumController = personelKurumController;
        this.personelKurumTuruController = personelKurumTuruController;

        personelList = this.personelController.findAllPerson();
        hastaList = this.hastaController.findAllHasta();
        randevuList = this.randevuController.findAllRandevu();
        personelKurumTuruList=this.personelKurumTuruController.getPersonelKurumTuruList();

        randevuGrid.setItems(randevuList);
        dataProvider = (ListDataProvider<Randevu>) randevuGrid.getDataProvider();

        this.setPadding(false);
        this.setSpacing(false);
        this.getStyle().set("padding", "10px");
        buildMainVerticalLayout();
    }

    private void buildMainVerticalLayout() {
        randevuListViewMainLayout.add(buildRandevuGridAndsSearchBar());
        randevuListViewMainLayout.setPadding(false);
        randevuListViewMainLayout.setSpacing(false);
        randevuListViewMainLayout.setWidth("100%");
        randevuListViewMainLayout.setHeight("100%");

        detailsVerticalLayout.setHeight("500px");
        detailsVerticalLayout.getStyle().set("flex", "1 1 30%");
        detailsVerticalLayout.setPadding(false);
        detailsVerticalLayout.setSpacing(false);

        add(randevuListViewMainLayout, detailsVerticalLayout);
    }

    private HorizontalLayout buildGridSearchBarLayout() {
        randevuHastaAra = new TextField();
        randevuHastaAra.setValueChangeMode(ValueChangeMode.EAGER);
        randevuHastaAra.setSizeFull();
        randevuHastaAra.setPlaceholder("Hasta Adı/Soyadı Giriniz");
        randevuHastaAra.addValueChangeListener(h -> {
            String hastaTempName = h.getValue();
            dataProvider.addFilter(r -> {
                String hastaFirstName = hastaController.findById(Long.valueOf(r.getRandevuAlanHastaTc()))
                        .getHastafirstName();
                String hastaLastName = hastaController.findById(Long.valueOf(r.getRandevuAlanHastaTc()))
                        .getHastaLastName();
                return hastaFirstName.toLowerCase().contains(hastaTempName.toLowerCase())
                        || hastaLastName.toLowerCase().contains(hastaTempName.toLowerCase());
            });
            dataProvider.refreshAll();
        });
        randevuHastaTCAra = new TextField();
        randevuHastaTCAra.setValueChangeMode(ValueChangeMode.EAGER);
        randevuHastaTCAra.setMaxLength(11);
        randevuHastaTCAra.setSizeFull();
        randevuHastaTCAra.setPlaceholder("Hasta TC Giriniz");
        randevuHastaTCAra.addValueChangeListener(h -> {
            String hastaTc = h.getValue();
            dataProvider.addFilter(r -> hastaTc == null ? null : r.getRandevuAlanHastaTc().contains(hastaTc));
            dataProvider.refreshAll();
        });

        randevuBolumAra = new ComboBox<>();
        randevuBolumAra.setSizeFull();
        randevuBolumAra.setPlaceholder("Bölüme Göre Filtrele");
        randevuBolumAra.setItemLabelGenerator(PersonelBolum::getPersonelBolumAdi);
        randevuBolumAra.setItems(personelBolumController.getPersonelBolumList());
        randevuBolumAra.addValueChangeListener(p -> {
            PersonelBolum selectedBolum = p.getValue();
            dataProvider.addFilter(r -> selectedBolum == null ? null
                    : r.getRandevuVerenDoktor().getPersonelBolum().getPersonelBolumAdi()
                            .contains(selectedBolum.getPersonelBolumAdi()));
            dataProvider.refreshAll();
        });

        randevuKurumAra = new ComboBox<>();
        randevuKurumAra.setPlaceholder("Kuruma Göre Filtrele");
        randevuKurumAra.setSizeFull();
        randevuKurumAra.setItemLabelGenerator(PersonelKurum::getKurumAdi);
        randevuKurumAra.setItems(personelKurumController.getPersonelKurumList());
        randevuKurumAra.addValueChangeListener(k -> {
            PersonelKurum selectedKurum = k.getValue();
            dataProvider.addFilter(r -> r.getRandevuVerenDoktor().getPersonelKurum().getKurumAdi()
                    .contains(selectedKurum != null ? selectedKurum.getKurumAdi() : null));
            dataProvider.refreshAll();
        });

        randevuKurumTuruGroup = new MultiSelectComboBox<>();
        randevuKurumTuruGroup.setRequired(true);
        randevuKurumTuruGroup.setItemLabelGenerator(PersonelKurumTuru::getKurumTuruAd);
        randevuKurumTuruGroup.getStyle().set("padding", "5px");
        randevuKurumTuruGroup.setWidthFull();
        randevuKurumTuruGroup.setItems(personelKurumTuruList);
        
        randevuKurumTuruGroup.setPlaceholder("Kurum Türüne Göre Filtrele");
        randevuKurumTuruGroup.setClearButtonVisible(true);

        randevuKurumTuruGroup.addSelectionListener(kt -> {
            selectedItemsIds=kt.getAllSelectedItems().stream().map(PersonelKurumTuru::getKurumturuId).toList();

            if(!selectedItemsIds.isEmpty() || selectedItemsIds!=null){
                dataProvider.setFilter(item ->
                    selectedItemsIds.contains(
                        personelKurumTuruController.getPersonelKurumTuruById(
                            item.getRandevuVerenDoktor().getPersonelKurum().getKurumTuruId())
                        .getKurumturuId()
                    )
                );
            }else{
                reloadRandevuList();
            }
        });
        clearFiltersButton = new Button();
        clearFiltersButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        clearFiltersButton.setIcon(new Icon(VaadinIcon.CLOSE));
        clearFiltersButton.addClickListener(e -> {
            clearFields();
        });

        randevuKurumTuruCheckBoxGroupLayout.add(randevuKurumTuruGroup);
        randevuKurumTuruCheckBoxGroupLayout.setSpacing(false);
        randevuKurumTuruCheckBoxGroupLayout.setPadding(false);
        randevuKurumTuruCheckBoxGroupLayout.setWidth("400px");
        randevuKurumTuruCheckBoxGroupLayout.setHeight("auto");
        randevuKurumTuruCheckBoxGroupLayout.getStyle().set("font-size", "small");
        randevuKurumTuruCheckBoxGroupLayout.getStyle().set("height", "1.5%");

        randevuHastaAraLayout.add(randevuHastaTCAra, randevuHastaAra);
        randevuHastaAraLayout.getStyle().set("border", "1px black");
        randevuHastaAraLayout.setWidth("300px");
        randevuHastaAraLayout.setSpacing(false);

        randevuKurumBolumAraLayout.add(randevuKurumAra, randevuBolumAra);
        randevuKurumBolumAraLayout.setWidth("300px");
        randevuKurumBolumAraLayout.getStyle().set("border", "1px black");
        randevuKurumBolumAraLayout.setSpacing(false);

        HorizontalLayout gridAboveLayout = new HorizontalLayout(randevuKurumTuruCheckBoxGroupLayout,
                randevuHastaAraLayout, randevuKurumBolumAraLayout, clearFiltersButton);
        gridAboveLayout.setAlignItems(Alignment.CENTER);
        gridAboveLayout.setHeight("100px");
        gridAboveLayout.setSpacing(false);
        gridAboveLayout.getStyle().set("padding", "10px");
        return gridAboveLayout;
    }

    private VerticalLayout buildRandevuGridAndsSearchBar() {
        randevuGrid.setSelectionMode(SelectionMode.SINGLE);
        randevuGrid.addColumn(Randevu::getRandevuId).setHeader("Randevu No")
                .setWidth("2em");
        randevuGrid.addColumn(Randevu::getRandevuAlanHastaTc).setHeader("Hasta TC");
        randevuGrid
                .addColumn(r -> hastaController.findById(Long.valueOf(r.getRandevuAlanHastaTc())).getHastafirstName()
                        + " " +
                        hastaController.findById(Long.valueOf(r.getRandevuAlanHastaTc())).getHastaLastName())
                .setHeader("Hasta");
        randevuGrid.addColumn(
                r -> r.getRandevuVerenDoktor().getPersonelAdi() + " " + r.getRandevuVerenDoktor().getPersonelSoyadi())
                .setHeader("Personel")
                .setWidth("10em");
        randevuGrid.addColumn(r -> r.getRandevuVerenDoktor().getPersonelBolum().getPersonelBolumAdi())
                .setHeader("Bölüm");
        randevuGrid.asSingleSelect().addValueChangeListener(r -> {
            if (r.getValue() != null) {
                randevuListDetailsView = new RandevuListDetailsView(r.getValue(), hastaController);
                detailsVerticalLayout.removeAll();
                detailsVerticalLayout.add(randevuListDetailsView);
            } else {
                detailsVerticalLayout.removeAll();
            }
        });
        randevuGrid.addColumn(new ComponentRenderer<>(Button::new, (button, randevu) -> {
            button.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
            button.setIcon(new Icon(VaadinIcon.TRASH));
            button.addClickListener(r -> {
                deleteRandevu(randevu);
                reloadRandevuList();
            });
        }));
        randevuGrid.setRowsDraggable(true);
        gridAndSearchBarLayout.add(buildGridSearchBarLayout(), buildRandevuTarihAralikLayout(), randevuGrid);
        gridAndSearchBarLayout.getStyle().set("flex", "1 1 70%");
        gridAndSearchBarLayout.getStyle().set("box-shadow", "0px 0px 10px 0px rgba(0, 0, 0, 0.2)");
        gridAndSearchBarLayout.getStyle().set("border-radius", "10px");
        gridAndSearchBarLayout.getStyle().set("spacing", "10px");
        gridAndSearchBarLayout.setWidth(1075, Unit.PIXELS);
        gridAndSearchBarLayout.setHeightFull();
        gridAndSearchBarLayout.setSpacing(false);
        gridAndSearchBarLayout.setPadding(false);

        return gridAndSearchBarLayout;
    }

    private void deleteRandevu(Randevu randevu) {
        deleteConfirmView = new DeleteConfirmView("Bu başvuruyu silmek istediğinize emin misiniz?");
        deleteConfirmView.open();
        deleteConfirmView.getConfirmButton().addClickListener(e -> {
            try {
                randevuController.deleteRandevu(randevu);
                clearFields();
                deleteConfirmView.close();
            } catch (DataIntegrityViolationException exception) {
                errorDialogView = new ErrorDialogView(exception, exception.getMessage());
                errorDialogView.open();
                deleteConfirmView.close();
            }
        });
    }

    private HorizontalLayout buildRandevuTarihAralikLayout() {
        leftSideLayout.getStyle().set("width", "25%");
        rightSideLayout.getStyle().set("width", "25%");
        leftSideLayout.setHeightFull();
        rightSideLayout.setHeightFull();
        rightSideLayout.setPadding(false);
        leftSideLayout.setPadding(false);

        randevuTarihAralik = new DatePicker();
        randevuTarihAralik.setPlaceholder("Tarihe Göre Filtrele");
        randevuTarihAralik.setClearButtonVisible(true);
        randevuTarihAralik.getElement().setAttribute("text-align", "center");
        randevuTarihAralik.setWidthFull();
        randevuTarihAralik.setHeightFull();
        randevuTarihAralik.setValue(LocalDate.now());

        randevuTarihAralik.addValueChangeListener(date -> {
            dataProvider.setFilter(
                    r -> DateToLocalDateUtil.convertDateToLocalDate(r.getRandevuBaslangicTarih()) == date.getValue());
            dataProvider.refreshAll();
        });

        HorizontalLayout dateOptionsLayout = new HorizontalLayout();
        dateOptionsLayout.getStyle().set("width", "50%");
        dateOptionsLayout.setPadding(false);
        dateOptionsLayout.setHeightFull();

        nextDayButton = new Button();
        nextDayButton.setIcon(new Icon(VaadinIcon.ARROW_RIGHT));
        nextDayButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        nextDayButton.addClickListener(e -> {
            randevuTarihAralik.setValue(randevuTarihAralik.getValue().plusDays(1));
        });

        previousDayButton = new Button();
        previousDayButton.setIcon(new Icon(VaadinIcon.ARROW_LEFT));
        previousDayButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        previousDayButton.addClickListener(e -> {
            randevuTarihAralik.setValue(randevuTarihAralik.getValue().minusDays(1));
        });

        nextWeekButton = new Button();
        nextWeekButton.setIcon(new Icon(VaadinIcon.FORWARD));
        nextWeekButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        nextWeekButton.addClickListener(e -> {
            randevuTarihAralik.setValue(randevuTarihAralik.getValue().plusWeeks(1));
        });

        previousWeekButton = new Button();
        previousWeekButton.setIcon(new Icon(VaadinIcon.BACKWARDS));
        previousWeekButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        previousWeekButton.addClickListener(e -> {
            randevuTarihAralik.setValue(randevuTarihAralik.getValue().minusWeeks(1));
        });

        dateOptionsLayout.add(previousWeekButton, previousDayButton, randevuTarihAralik, nextDayButton,
                nextWeekButton);

        gridDateOptionLayout.add(leftSideLayout, dateOptionsLayout, rightSideLayout);
        gridDateOptionLayout.setWidthFull();

        return gridDateOptionLayout;
    }

    private void reloadRandevuList() {
        randevuGrid.setItems(randevuController.findAllRandevu());
    }

    private void clearFields() {
        dataProvider.clearFilters();

        randevuBolumAra.clear();
        randevuKurumAra.clear();
        randevuHastaAra.clear();
        randevuHastaTCAra.clear();

        randevuGrid.setItems(randevuList);
    }
}