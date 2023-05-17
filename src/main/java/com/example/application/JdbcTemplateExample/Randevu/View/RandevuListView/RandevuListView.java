package com.example.application.JdbcTemplateExample.Randevu.View.RandevuListView;

import java.util.List;

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
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

@Route(value = "randevu-list", layout = MainLayout.class)
public class RandevuListView extends HorizontalLayout {

    private Grid<Randevu> randevuGrid;
    private TextField randevuHastaTCAra;
    private TextField randevuHastaAra;
    private ComboBox<PersonelBolum> randevuBolumAra;
    private ComboBox<PersonelKurum> randevuKurumAra;
    private ComboBox<PersonelKurumTuru> randevuKurumTuruAra;

    private PersonelController personelController;
    private RandevuController randevuController;
    private HastaController hastaController;
    private PersonelBolumController personelBolumController;
    private PersonelKurumController personelKurumController;
    private PersonelKurumTuruController personelKurumTuruController;

    private List<Personel> personelList;
    private List<Hasta> hastaList;
    private List<Randevu> randevuList;

    private Randevu randevu;
    // private RandevuListDetailsView randevuListDetailsView = new RandevuListDetailsView();

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

        buildRandevuListMainLayout();
    }

    private void buildRandevuListMainLayout() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.add(buildRandevuGrid());
        add(mainLayout);
    }

    private HorizontalLayout buildGridSearchBarLayout(List<Randevu> randevuList) {
        ListDataProvider<Randevu> randevuDataProvider = new ListDataProvider<Randevu>(randevuList);
        randevuGrid.setDataProvider(randevuDataProvider);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        randevuHastaAra = new TextField();
        randevuHastaAra.setPlaceholder("Hasta Adı/Soyadı Griniz");
        randevuHastaAra.addValueChangeListener(h -> {
            randevuDataProvider.setFilter(r -> 
            hastaController.findById(Long.valueOf(r.getRandevuAlanHastaTC()))
                    .getHastafirstName().toLowerCase().contains(h.getValue()) 
            ||
            hastaController.findById(Long.valueOf(r.getRandevuAlanHastaTC()))
                    .getHastaLastName().toLowerCase().contains(h.getValue().toLowerCase()));
        });

        randevuHastaTCAra = new TextField();
        randevuHastaTCAra.setMaxLength(11);
        randevuHastaTCAra.setPlaceholder("Hasta TC giriniz");
        randevuHastaAra.addValueChangeListener(h->{
            randevuDataProvider.setFilter(r->
            hastaController.findById(Long.valueOf(r.getRandevuAlanHastaTC()))
                .getHastakimlikno().equals(Long.valueOf(h.getValue())));
        });

        randevuBolumAra = new ComboBox<>();
        randevuBolumAra.setPlaceholder("Bölüme Göre Filtrele");
        randevuBolumAra.setItemLabelGenerator(PersonelBolum::getPersonelBolumAdi);
        randevuBolumAra.setItems(personelBolumController.getPersonelBolumList());
        randevuBolumAra.addValueChangeListener(p->{
            randevuDataProvider.setFilter(r->
                r.getRandevuVerenDoktor().getPersonelBolum().getPersonelBolumAdi()
                .toLowerCase().contains(p.getValue().getPersonelBolumAdi().toLowerCase()));
        });

        randevuKurumAra = new ComboBox<>();
        randevuKurumAra.setPlaceholder("Kuruma Göre Filtrele");
        randevuKurumAra.setItemLabelGenerator(PersonelKurum::getKurumAdi);
        randevuKurumAra.setItems(personelKurumController.getPersonelKurumList());
        randevuKurumAra.addValueChangeListener(k->{
            randevuDataProvider.setFilter(r->
                r.getRandevuVerenDoktor().getPersonelKurum().getKurumAdi().toLowerCase()
                .contains(k.getValue().getKurumAdi().toLowerCase()));
        });

        randevuKurumTuruAra = new ComboBox<>();
        randevuKurumTuruAra.setPlaceholder("Kurum Türüne Göre Filtrele");
        randevuKurumTuruAra.setItemLabelGenerator(PersonelKurumTuru::getKurumTuruAd);
        randevuKurumTuruAra.setItems(personelKurumTuruController.getPersonelKurumTuruList());
        randevuKurumTuruAra.addValueChangeListener(kt->{
            randevuDataProvider.setFilter(r->
                r.getRandevuVerenDoktor().getPersonelKurum().getKurumAdi().toLowerCase()
                .contains(kt.getValue().getKurumTuruAd().toLowerCase()));
        }); 

        horizontalLayout.add(randevuHastaTCAra, randevuHastaAra, randevuKurumAra, randevuBolumAra, randevuKurumTuruAra);
        return horizontalLayout;
    }

    private VerticalLayout buildRandevuGrid() {
        randevuGrid = new Grid<>();
        randevuGrid.setItems(randevuList);

        randevuGrid.setSelectionMode(SelectionMode.SINGLE);
        randevuGrid.addColumn(Randevu::getRandevuId).setHeader("Randevu No");
        randevuGrid.addColumn(Randevu::getRandevuAlanHastaTC).setHeader("Hasta TC");
        randevuGrid.addColumn(r -> r.getRandevuBaslangicTarih()+"-"+r.getRandevuBitisTarih().getTime()).setHeader("Randevu Tarihi");
        randevuGrid.addColumn(r -> r.getRandevuVerenDoktor().getPersonelBolum().getPersonelBolumAdi())
                .setHeader("Bölüm");
        // randevuGrid.asSingleSelect().addValueChangeListener(randevu -> {
        //     if (randevu != null) {
        //         randevuListDetailsView.setRandevuBean(randevu.getValue());
        //     }
        // });
        randevuGrid.addColumn(new ComponentRenderer<>(Button::new, (button, randevu) -> {
            button.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
            button.setIcon(new Icon(VaadinIcon.TRASH));
            button.addClickListener(r -> {

            });
        }));
        VerticalLayout gridAndFilterLayout=new VerticalLayout(buildGridSearchBarLayout(randevuList),randevuGrid);
        return gridAndFilterLayout;
    }
}