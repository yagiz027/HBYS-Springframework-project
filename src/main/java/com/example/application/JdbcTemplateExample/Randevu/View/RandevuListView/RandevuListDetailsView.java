package com.example.application.JdbcTemplateExample.Randevu.View.RandevuListView;

import com.example.application.JdbcTemplateExample.Hasta.Controller.HastaController;
import com.example.application.JdbcTemplateExample.Hasta.Model.Hasta;
import com.example.application.JdbcTemplateExample.Personel.Model.Personel;
import com.example.application.JdbcTemplateExample.Randevu.Model.Randevu;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class RandevuListDetailsView extends VerticalLayout {
    private Randevu randevu;

    private HastaController hastaController;

    private final Span hastaTC=new Span();
    private final Span hastaName=new Span();
    private final Span hastaGender=new Span();
    private final Span hastaAge=new Span();
    private final Span hastaKan=new Span();
    private final Span hastaTel=new Span();
    private final Span hastaEmail=new Span();
    private final Span hastaAdres=new Span();

    private final Span personelName=new Span();
    private final Span personelTel=new Span();
    private final Span personelEmail=new Span();
    private final Span personelBolum=new Span();
    private final Span KurumAdı=new Span();
    private final Span KurumTuru=new Span();
    private final Span KurumIl=new Span();
    private final Span KurumIlce=new Span();

    public RandevuListDetailsView(Randevu randevu, HastaController hastaController) {
        this.hastaController=hastaController;
        this.randevu=randevu;
        
        this.getStyle().set("padding","20px");
        this.getStyle().set("spacing", "10px");
        this.setHeightFull();
        this.setWidthFull();
        
        buildDetailsMainLayout();
    }

    private void buildDetailsMainLayout(){
        VerticalLayout mainLayout=new VerticalLayout();
        mainLayout.add(buildHastaDetails(),buildPersonelDetails());
        mainLayout.setPadding(false);
        mainLayout.setSpacing(false);
        Scroller scroller=new Scroller(mainLayout);        
        scroller.setScrollDirection(ScrollDirection.VERTICAL);
        
        add(mainLayout,scroller);
    }

    private Details buildHastaDetails(){
        VerticalLayout hastaDetailsVerticalLayout=new VerticalLayout();
        hastaDetailsVerticalLayout.setSpacing(false);
        hastaDetailsVerticalLayout.setPadding(false);
        VerticalLayout hastaDetailsVerticalLayout2=new VerticalLayout();
        hastaDetailsVerticalLayout2.setSpacing(false);
        hastaDetailsVerticalLayout2.setPadding(false);

        //Randevu Alan Hasta:
        Hasta hasta=new Hasta();
        hasta=hastaController.findById(Long.valueOf(randevu.getRandevuAlanHastaTc()));
        //Randevu Alan Hasta Bilgileri:
        hastaTC.setText("Hasta TC :"+hasta.getHastakimlikno().toString());
        hastaName.setText("Hasta Adı Soyadı :"+hasta.getHastafirstName()+" "+hasta.getHastaLastName());
        hastaGender.setText("Hasta Cinsiyeti :"+hasta.getHastaGender());
        hastaAge.setText("Hasta Yaş :"+hasta.getHastaAge());
        hastaKan.setText("Hasta Kan Grubu :"+hasta.getHastaKanGrup().getKanGrup());

        //Randevu Alan Hasta iletişim bilgileri:
        hastaTel.setText("Hasta Telefon :"+hasta.getHastaTelefon());
        hastaEmail.setText("Hasta Email :"+hasta.getHastaEmail());
        hastaAdres.setText("Hasta Adres :"+hasta.getHastaAdres());
        
        hastaDetailsVerticalLayout.add(hastaTC,hastaName,hastaGender,hastaAge,hastaKan,hastaTel,hastaEmail,hastaAdres);

        HorizontalLayout hastaDetailsHorizontalLayout=new HorizontalLayout();
        hastaDetailsHorizontalLayout.add(hastaDetailsVerticalLayout);

        Details hastaDetails=new Details("Hasta Bilgiler :");
        hastaDetails.setContent(hastaDetailsHorizontalLayout);
        hastaDetails.setOpened(false);

        return hastaDetails;
    }

    private Details buildPersonelDetails(){
        VerticalLayout personelDetailsVerticalLayout=new VerticalLayout();
        personelDetailsVerticalLayout.setSpacing(false);
        personelDetailsVerticalLayout.setPadding(false);
        VerticalLayout personelDetailsVerticalLayout2=new VerticalLayout();
        personelDetailsVerticalLayout2.setSpacing(false);
        personelDetailsVerticalLayout2.setPadding(false);

        //Randevu Alınan Personel:
        Personel personel=new Personel();
        personel=randevu.getRandevuVerenDoktor();

        //Randevu Alınan Personel Bilgileri:
        personelName.setText("Doktor Adı :"+personel.getPersonelAdi()+" "+personel.getPersonelSoyadi());
        personelTel.setText("Doktor Telefon :"+personel.getPersonelPhone());
        personelEmail.setText("Doktor Email :"+personel.getPersonelEmail());
        personelBolum.setText("Bölüm :"+personel.getPersonelBolum().getPersonelBolumAdi());

        //Personel Kurum Bilgileri:
        KurumAdı.setText("Kurum Adı :"+personel.getPersonelKurum().getKurumAdi());
        KurumTuru.setText("Kurum Türü :"+personel.getPersonelKurum().getKurumTuruId());
        KurumIl.setText("Kurum İl :"+personel.getPersonelKurum().getKurumil());
        KurumIlce.setText("Kurum İlçe :"+personel.getPersonelKurum().getKurumilce());

        //Kurum Bilgileri Details:
        personelDetailsVerticalLayout2.add(KurumAdı,KurumTuru,KurumIl,KurumIlce);
        Details kurumDetails=new Details("Kurum Bilgileri:");
        kurumDetails.setContent(personelDetailsVerticalLayout2);
        kurumDetails.setOpened(false);

        personelDetailsVerticalLayout.add(personelName,personelTel,personelEmail,personelBolum);

        VerticalLayout personelDetailsMainLayout=new VerticalLayout();
        personelDetailsMainLayout.setSpacing(false);
        personelDetailsMainLayout.setPadding(false);
        personelDetailsMainLayout.add(personelDetailsVerticalLayout,kurumDetails);

        Details personelDetails=new Details("Personel Bilgileri:");
        personelDetails.setContent(personelDetailsMainLayout);

        return personelDetails;
    }
}
