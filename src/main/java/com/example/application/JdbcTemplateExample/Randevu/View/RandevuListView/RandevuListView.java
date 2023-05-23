package com.example.application.JdbcTemplateExample.Randevu.View.RandevuListView;

import java.util.List;
import java.util.function.Consumer;

import com.example.application.JdbcTemplateExample.MainLayout;
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
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "randevu-list", layout = MainLayout.class)
public class RandevuListView extends VerticalLayout {

    private Grid<Randevu> randevuGrid = new Grid<>(Randevu.class, false);
    private TextField randevuHastaTCAra;
    private TextField randevuHastaAra;
    private ComboBox<PersonelBolum> randevuBolumAra;
    private ComboBox<PersonelKurum> randevuKurumAra;
    private RadioButtonGroup<PersonelKurumTuru> randevuKurumTuruGroup;
    private Button clearFiltersButton;

    private PersonelController personelController;
    private RandevuController randevuController;
    private HastaController hastaController;
    private PersonelBolumController personelBolumController;
    private PersonelKurumController personelKurumController;
    private PersonelKurumTuruController personelKurumTuruController;

    private List<Personel> personelList;
    private List<Hasta> hastaList;
    private List<Randevu> randevuList;

    private Consumer<String> filterChangeConsumer;

    private Randevu randevu;
    private RandevuListDetailsView randevuListDetailsView;

    private HorizontalLayout randevuListViewMainLayout = new HorizontalLayout();
    private VerticalLayout detailsLayout = new VerticalLayout();
    private VerticalLayout gridAndSearchBarLayout = new VerticalLayout();
    private VerticalLayout randevuKurumTuruCheckBoxGroupLayout = new VerticalLayout();
    private VerticalLayout randevuHastaAraLayout = new VerticalLayout();
    private VerticalLayout randevuKurumBolumAraLayout = new VerticalLayout();

    private ListDataProvider<Randevu> dataProvider;

    public RandevuListView(PersonelController personelController, RandevuController randevuController,
            HastaController hastaController, PersonelBolumController personelBolumController,
            PersonelKurumController personelKurumController, PersonelKurumTuruController personelKurumTuruController) {
        this.personelController = personelController;
        this.randevuController = randevuController;
        this.hastaController = hastaController;
        this.personelBolumController = personelBolumController;
        this.personelKurumController = personelKurumController;
        this.personelKurumTuruController = personelKurumTuruController;

        personelList = personelController.findAllPerson();
        hastaList = hastaController.findAllHasta();
        randevuList = randevuController.findAllRandevu();

        randevuGrid.setItems(randevuList);
        dataProvider=(ListDataProvider<Randevu>) randevuGrid.getDataProvider();

        this.setPadding(false);
        this.setSpacing(false);
        buildMainVerticalLayout();
    }

    private void buildMainVerticalLayout() {
        randevuListViewMainLayout.add(buildRandevuGridAndsSearchBar(), detailsLayout);
        randevuListViewMainLayout.setPadding(false);
        randevuListViewMainLayout.setSpacing(false);
        randevuListViewMainLayout.setHeightFull();
        randevuListViewMainLayout.setWidthFull();

        add(randevuListViewMainLayout);
    }

    private HorizontalLayout buildGridSearchBarLayout() {
        randevuHastaAra = new TextField();
        randevuHastaAra.setValueChangeMode(ValueChangeMode.EAGER);
        randevuHastaAra.setSizeFull();
        randevuHastaAra.setPlaceholder("Hasta Adı/Soyadı Giriniz");
        randevuHastaAra.addValueChangeListener(h -> {
            String hastaTempName = h.getValue();
            dataProvider.addFilter(r -> {
                String hastaFirstName = hastaController.findById(Long.valueOf(r.getRandevuAlanHastaTC())).getHastafirstName();
                String hastaLastName = hastaController.findById(Long.valueOf(r.getRandevuAlanHastaTC())).getHastaLastName();
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
            dataProvider.addFilter(r -> hastaTc==null ? null : r.getRandevuAlanHastaTC().contains(hastaTc));
            dataProvider.refreshAll();
        });

        randevuBolumAra = new ComboBox<>();
        randevuBolumAra.setSizeFull();
        randevuBolumAra.setPlaceholder("Bölüme Göre Filtrele");
        randevuBolumAra.setItemLabelGenerator(PersonelBolum::getPersonelBolumAdi);
        randevuBolumAra.setItems(personelBolumController.getPersonelBolumList());
        randevuBolumAra.addValueChangeListener(p -> {
            PersonelBolum selectedBolum = p.getValue();
            dataProvider.addFilter(r -> selectedBolum==null ? null : r.getRandevuVerenDoktor().getPersonelBolum().getPersonelBolumAdi()
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
            dataProvider.addFilter(r ->r.getRandevuVerenDoktor().getPersonelKurum().getKurumAdi()
                    .contains(selectedKurum != null ? selectedKurum.getKurumAdi() : null));
            dataProvider.refreshAll();
        });

        randevuKurumTuruGroup = new RadioButtonGroup<>();
        randevuKurumTuruGroup.setRequired(true);
        randevuKurumTuruGroup.setItemLabelGenerator(PersonelKurumTuru::getKurumTuruAd);
        randevuKurumTuruGroup.setItems(personelKurumTuruController.getPersonelKurumTuruList());
        randevuKurumTuruGroup.addValueChangeListener(kt -> {
            dataProvider.clearFilters();
            PersonelKurumTuru selectedKurumTuru = kt.getValue();
            if (kt != null) {
                dataProvider.addFilter(r -> personelKurumTuruController
                        .getPersonelKurumTuruById(r.getRandevuVerenDoktor().getPersonelKurum().getKurumTuruId())
                        .getKurumTuruAd().contains(selectedKurumTuru.getKurumTuruAd()));
                dataProvider.refreshAll();
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
        randevuKurumTuruCheckBoxGroupLayout.getStyle().set("border", "1px black");
        randevuKurumTuruCheckBoxGroupLayout.addClassName("bordered-layout");
        randevuKurumTuruCheckBoxGroupLayout.setWidth("420px");
        randevuKurumTuruCheckBoxGroupLayout.setHeight("100px");
        randevuKurumTuruCheckBoxGroupLayout.getStyle().set("font-size", "small");

        randevuHastaAraLayout.add(randevuHastaTCAra, randevuHastaAra);
        randevuHastaAraLayout.getStyle().set("border", "1px black");
        randevuHastaAraLayout.setWidth("300px");

        randevuKurumBolumAraLayout.add(randevuKurumAra, randevuBolumAra);
        randevuKurumBolumAraLayout.setWidth("300px");
        randevuKurumBolumAraLayout.getStyle().set("border", "1px black");

        HorizontalLayout gridAboveLayout = new HorizontalLayout(randevuKurumTuruCheckBoxGroupLayout,
                randevuHastaAraLayout, randevuKurumBolumAraLayout, clearFiltersButton);
        gridAboveLayout.setAlignItems(Alignment.CENTER);
        gridAboveLayout.setHeight("100px");
        gridAboveLayout.setSpacing(false);
        gridAboveLayout.setPadding(false);
        return gridAboveLayout;
    }

    private VerticalLayout buildRandevuGridAndsSearchBar() {
        randevuGrid.setSelectionMode(SelectionMode.SINGLE);
        randevuGrid.addColumn(Randevu::getRandevuId).setHeader("No")
                .setWidth("2em");
        randevuGrid.addColumn(Randevu::getRandevuAlanHastaTC).setHeader("Hasta TC");
        randevuGrid.addColumn(r -> r.getRandevuBaslangicTarih() + "-" + r.getRandevuBitisTarih().getTime())
                .setHeader("Randevu Tarihi")
                .setWidth("6em");
        randevuGrid.addColumn(r -> r.getRandevuVerenDoktor().getPersonelBolum().getPersonelBolumAdi())
                .setHeader("Bölüm");
        randevuGrid.asSingleSelect().addValueChangeListener(randevu -> {
            if (randevu != null) {
                detailsLayout.removeAll();
                randevuListDetailsView = new RandevuListDetailsView(randevu.getValue(), hastaController);
                detailsLayout.setSpacing(false);
                detailsLayout.add(randevuListDetailsView);
            }
        });
        randevuGrid.addColumn(new ComponentRenderer<>(Button::new, (button, randevu) -> {
            button.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
            button.setIcon(new Icon(VaadinIcon.TRASH));
            button.addClickListener(r -> {
            });
        }));
        randevuGrid.setWidth("100%");
        randevuGrid.setRowsDraggable(true);
        gridAndSearchBarLayout.add(buildGridSearchBarLayout(), randevuGrid);
        gridAndSearchBarLayout.setSpacing(false);
        gridAndSearchBarLayout.setPadding(false);

        return gridAndSearchBarLayout;
    }

    private void clearFields() {
        dataProvider.clearFilters();
        
        randevuBolumAra.clear();
        randevuKurumAra.clear();
        randevuHastaAra.clear();
        randevuHastaTCAra.clear();
        randevuKurumTuruGroup.clear();

        randevuGrid.setItems(randevuList);
    }
}