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
import com.example.application.JdbcTemplateExample.ValueConverters.DateToLocalDateUtil;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "randevu-list", layout = MainLayout.class)
public class RandevuListView extends VerticalLayout {
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

    public RandevuListView(PersonelController personelController, RandevuController randevuController,
            HastaController hastaController, PersonelBolumController personelBolumController,
            PersonelKurumController personelKurumController,PersonelKurumTuruController personelKurumTuruController) {
        this.personelController = personelController;
        this.randevuController = randevuController;
        this.hastaController = hastaController;
        this.personelBolumController = personelBolumController;
        this.personelKurumController = personelKurumController;
        this.personelKurumTuruController=personelKurumTuruController;

        personelList=personelController.findAllPerson();
        hastaList=hastaController.findAllHasta();
        randevuList=randevuController.findAllRandevu();

        buildRandevuListMainLayout();
    }
    private void buildRandevuListMainLayout(){
        VerticalLayout mainLayout=new VerticalLayout();
        mainLayout.add(buildGridSearchBarLayout(),buildRandevuGrid());

        add(mainLayout);
    }

    private HorizontalLayout buildGridSearchBarLayout(){
        HorizontalLayout horizontalLayout=new HorizontalLayout();
        randevuHastaAra=new TextField();
        randevuHastaAra.setPlaceholder("Hasta Adı/Soyadı Griniz");

        randevuHastaTCAra=new TextField();
        randevuHastaTCAra.setMaxLength(11);
        randevuHastaTCAra.setPlaceholder("Hasta TC giriniz");
        
        randevuBolumAra=new ComboBox<>();
        randevuBolumAra.setPlaceholder("Bölüme Göre Filtrele");
        randevuBolumAra.setItemLabelGenerator(PersonelBolum::getPersonelBolumAdi);
        randevuBolumAra.setItems(personelBolumController.getPersonelBolumList());

        randevuKurumAra=new ComboBox<>();
        randevuKurumAra.setPlaceholder("Kuruma Göre Filtrele");
        randevuKurumAra.setItemLabelGenerator(PersonelKurum::getKurumAdi);
        randevuKurumAra.setItems(personelKurumController.getPersonelKurumList());

        randevuKurumTuruAra=new ComboBox<>();
        randevuKurumTuruAra.setPlaceholder("Kurum Türüne Göre Filtrele");
        randevuKurumTuruAra.setItemLabelGenerator(PersonelKurumTuru::getKurumTuruAd);
        randevuKurumTuruAra.setItems(personelKurumTuruController.getPersonelKurumTuruList());

        horizontalLayout.add(randevuHastaTCAra,randevuHastaAra,randevuKurumAra,randevuBolumAra,randevuKurumTuruAra);
        return horizontalLayout;
    }

    private Grid<Randevu> buildRandevuGrid(){
        randevuGrid=new Grid<>();
        randevuGrid.setItems(randevuList);

        randevuGrid.addColumn(Randevu::getRandevuAlanHastaTC).setHeader("Hasta TC");
        randevuGrid.addColumn(r->DateToLocalDateUtil.convertDateToLocalDate(r.getRandevuBaslangicTarih())).setHeader("Randevu Başlangıç Tarihi");
        randevuGrid.addColumn(r->DateToLocalDateUtil.convertDateToLocalDate(r.getRandevuBitisTarih())).setHeader("Randevu Bitiş Tarihi");
        randevuGrid.addColumn(r->r.getRandevuVerenDoktor().getPersonelAdi() +" "+ r.getRandevuVerenDoktor().getPersonelSoyadi()).setHeader("Doktor");
        randevuGrid.addColumn(r->r.getRandevuVerenDoktor().getPersonelBolum().getPersonelBolumAdi()).setHeader("Randevu Bölümü");
        randevuGrid.addColumn(r->r.getRandevuStatu()).setHeader("Randevu Statü");

        return randevuGrid;
    }
}