package com.example.application.JdbcTemplateExample.Randevu.View.RandevuListView;

import com.example.application.JdbcTemplateExample.Hasta.Controller.HastaController;
import com.example.application.JdbcTemplateExample.Hasta.Model.Hasta;
import com.example.application.JdbcTemplateExample.Personel.Model.Personel;
import com.example.application.JdbcTemplateExample.Randevu.Model.Randevu;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class RandevuListDetailsView extends FormLayout{
    private Randevu randevu;

    private HastaController hastaController;

    private Span hastaTC=new Span();
    private Span hastaName=new Span();
    private Span hastaGender=new Span();
    private Span hastaAge=new Span();
    private Span hastaKan=new Span();
    private Span hastaTel=new Span();
    private Span hastaEmail=new Span();
    private Span hastaAdres=new Span();

    private Span personelName=new Span();
    private Span personelTel=new Span();
    private Span personelEmail=new Span();
    private Span personelBolum=new Span();
    private Span KurumAdı=new Span();
    private Span KurumTuru=new Span();
    private Span KurumIl=new Span();
    private Span KurumIlce=new Span();


    private Binder<Randevu> randevuBinder=new BeanValidationBinder<>(Randevu.class);

    public RandevuListDetailsView() {
        add(buildHastaDetails(),buildPersonelDetails());
    }

    private Details buildHastaDetails(){
        VerticalLayout hastaDetailsVerticalLayout=new VerticalLayout();
        VerticalLayout hastaDetailsVerticalLayout2=new VerticalLayout();

        //Randevu Alan Hasta:
        Hasta hasta=new Hasta();
        hasta=hastaController.findById(Long.valueOf(randevu.getRandevuAlanHastaTC()));
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
        
        hastaDetailsVerticalLayout.add(hastaTC,hastaName,hastaGender,hastaAge,hastaKan);
        hastaDetailsVerticalLayout2.add(hastaTel,hastaEmail,hastaAdres);

        HorizontalLayout hastaDetailsHorizontalLayout=new HorizontalLayout();
        hastaDetailsHorizontalLayout.add(hastaDetailsVerticalLayout,hastaDetailsVerticalLayout2);

        Details hastaDetails=new Details("Hasta Bilgiler :");
        hastaDetails.setContent(hastaDetailsHorizontalLayout);
        hastaDetails.setOpened(false);

        return hastaDetails;
    }

    private Details buildPersonelDetails(){
        VerticalLayout personelDetailsVerticalLayout=new VerticalLayout();
        VerticalLayout personelDetailsVerticalLayout2=new VerticalLayout();

        //Randevu Alınan Personel:
        Personel personel=new Personel();
        personel=randevu.getRandevuVerenDoktor();

        //Randevu Alınan Personel Bilgileri:
        personelName.setText("Doktor Adı :"+personel.getPersonelAdi()+" "+personel.getPersonelSoyadi());
        personelTel.setText("Doktor Telefon :"+personel.getPersonelPhone());
        personelEmail.setText("Doktor Email :"+personel.getPersonelEmail());
        personelBolum.setText("Randevu Alınan Bölüm :"+personel.getPersonelBolum().getPersonelBolumAdi());

        //Personel Kurum Bilgileri:
        KurumAdı.setText("Kurum Adı :"+personel.getPersonelKurum().getKurumAdi());
        KurumTuru.setText("Kurum Türü :"+personel.getPersonelKurum().getKurumAdi());
        KurumIl.setText("Kurum İl :"+personel.getPersonelKurum().getKurumil());
        KurumIlce.setText("Kurum İlçe :"+personel.getPersonelKurum().getKurumilce());

        //Kurum Bilgileri Details:
        personelDetailsVerticalLayout2.add(KurumAdı,KurumTuru,KurumIl,KurumIlce);
        Details kurumDetails=new Details("Randevu Alınan Kurum :");
        kurumDetails.setContent(personelDetailsVerticalLayout2);

        personelDetailsVerticalLayout.add(personelName,personelTel,personelEmail,personelBolum,kurumDetails);

        HorizontalLayout personelDetailsHorizontalLayout=new HorizontalLayout();
        personelDetailsHorizontalLayout.add(personelDetailsVerticalLayout,personelDetailsVerticalLayout2);

        Details personelDetails=new Details("Randevu Alınan Personel:");
        personelDetails.setContent(personelDetailsHorizontalLayout);

        return personelDetails;
    }

    public void setRandevuBean(Randevu randevu){
        this.randevu=randevu;
        randevuBinder.setBean(randevu);
    }
}
