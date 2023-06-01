package com.example.application.JdbcTemplateExample.Randevu.View.RandevuListView;

import com.example.application.JdbcTemplateExample.Hasta.Controller.HastaController;
import com.example.application.JdbcTemplateExample.Hasta.Model.Hasta;
import com.example.application.JdbcTemplateExample.Personel.Model.Personel;
import com.example.application.JdbcTemplateExample.Randevu.Model.Randevu;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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

    private Div detailsContainer=new Div();
    private HorizontalLayout detailsHeaderLayout=new HorizontalLayout();
    private HorizontalLayout detailsFooterLayout=new HorizontalLayout();
    
    private VerticalLayout detailsMainLayout=new VerticalLayout();
    
    private Header header = new Header();
    private H2 detailsLayoutTitle = new H2();
    private Section detailsHeader= new Section();
    private Section detailsBodySection = new Section();
    private Section detailsFooterSection = new Section();
    private Footer detailsFooter=new Footer();
    
    private Button randevuKabulButton;

    public RandevuListDetailsView(Randevu randevu, HastaController hastaController) {
        this.hastaController=hastaController;
        this.randevu=randevu;
        
        this.getStyle().set("padding","10px");
        this.getStyle().set("spacing", "10px");
        this.setWidth(400, Unit.PIXELS);
        this.setHeight(400, Unit.PIXELS);
        this.setHeightFull();
        this.setWidthFull();
        
       add(buildDetailsMainLayout());
    }

    private VerticalLayout buildDetailsMainLayout(){
        VerticalLayout detailsMainLayout=new VerticalLayout();
        detailsMainLayout.setSpacing(false);
        detailsMainLayout.setPadding(false);
        detailsMainLayout.setWidthFull();
        detailsMainLayout.setHeightFull();
        detailsMainLayout.getStyle().set("background-color", "white");
        detailsMainLayout.getStyle().set("border-radius", "10px");
        detailsMainLayout.getStyle().set("box-shadow", "0px 0px 10px 0px rgba(0, 0, 0, 0.2)");
        detailsMainLayout.getStyle().set("box-sizing", "border-box");
        detailsMainLayout.getStyle().set("padding", "10px");
        detailsMainLayout.getStyle().set("margin", "10px");
        detailsMainLayout.getStyle().set("padding", "10px").set("spacing", "10px");

        detailsMainLayout.add(buildDetailsHeader(),addWithScroller(buildDetailsBody()),buildDetailsFooter());
        return detailsMainLayout;
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
        hastaDetails.addThemeVariants(DetailsVariant.REVERSE);
        hastaDetails.setOpened(false);

        return hastaDetails;
    }

    private Details buildPersonelDetails(){
        VerticalLayout personelDetailsVerticalLayout=new VerticalLayout();
        personelDetailsVerticalLayout.setSpacing(false);
        personelDetailsVerticalLayout.setPadding(false);

        //Randevu Alınan Personel:
        Personel personel=new Personel();
        personel=randevu.getRandevuVerenDoktor();

        //Randevu Alınan Personel Bilgileri:
        personelName.setText("Doktor Adı :"+personel.getPersonelAdi()+" "+personel.getPersonelSoyadi());
        personelTel.setText("Doktor Telefon :"+personel.getPersonelPhone());
        personelEmail.setText("Doktor Email :"+personel.getPersonelEmail());
        personelBolum.setText("Bölüm :"+personel.getPersonelBolum().getPersonelBolumAdi());

        personelDetailsVerticalLayout.add(personelName,personelTel,personelEmail,personelBolum);

        VerticalLayout personelDetailsMainLayout=new VerticalLayout();
        personelDetailsMainLayout.setSpacing(false);
        personelDetailsMainLayout.setPadding(false);
        personelDetailsMainLayout.add(personelDetailsVerticalLayout);

        Details personelDetails=new Details("Personel Bilgileri:");
        personelDetails.setContent(personelDetailsMainLayout);
        personelDetails.addThemeVariants(DetailsVariant.REVERSE);

        return personelDetails;
    }
    private Details buildKurumDetails(){
        Personel personel=new Personel();
        personel=randevu.getRandevuVerenDoktor();

        VerticalLayout kurumDetailsLayout=new VerticalLayout();
        kurumDetailsLayout.setSpacing(false);
        kurumDetailsLayout.setPadding(false);

        Details kurumDetails=new Details("Kurum Bilgileri:");
        kurumDetails.setContent(kurumDetailsLayout);
        kurumDetails.addThemeVariants(DetailsVariant.REVERSE);
        kurumDetails.setOpened(false);

        //Personel Kurum Bilgileri:
        KurumAdı.setText("Kurum Adı :"+personel.getPersonelKurum().getKurumAdi());
        KurumTuru.setText("Kurum Türü :"+personel.getPersonelKurum().getKurumTuruId());
        KurumIl.setText("Kurum İl :"+personel.getPersonelKurum().getKurumil());
        KurumIlce.setText("Kurum İlçe :"+personel.getPersonelKurum().getKurumilce());

        //Kurum Bilgileri Details:
        kurumDetailsLayout.add(KurumAdı,KurumTuru,KurumIl,KurumIlce);

        return kurumDetails;
    }

    private Details buildRandevuDuzenleDetails(){
        return null;
    }

    private HorizontalLayout buildDetailsHeader(){
        header.getStyle().set("align-items", "center")
                .set("border-bottom", "1px solid var(--lumo-contrast-20pct)")
                .set("display", "flex").set("padding", "var(--lumo-space-m)");

        detailsLayoutTitle.setText("Başvuru Bilgileri");
        detailsLayoutTitle.getStyle().set("margin", "0");

        Icon arrowLeft = VaadinIcon.ARROW_LEFT.create();
        arrowLeft.setSize("var(--lumo-icon-size-m)");
        arrowLeft.getElement().setAttribute("aria-hidden", "true");
        arrowLeft.getStyle().set("box-sizing", "border-box")
                .set("margin-right", "var(--lumo-space-m)")
                .set("padding", "calc(var(--lumo-space-xs) / 2)");

        Anchor goBack = new Anchor("#", arrowLeft);

        header.add(goBack,detailsLayoutTitle);

        detailsHeaderLayout.add(header);

        return detailsHeaderLayout;
    }

    private Div buildDetailsBody(){
        detailsContainer.add(buildHastaDetails(),buildPersonelDetails(),buildKurumDetails());
        detailsContainer.getStyle().set("spacing", "0px");
        detailsContainer.getStyle().set("padding", "0px");
        detailsContainer.setSizeFull();
        detailsContainer.getStyle().set("overflow", "bottom");
        detailsContainer.getStyle().set("align-items", "stretch");

        return detailsContainer;
    }

    private HorizontalLayout buildDetailsFooter(){
        detailsFooter.getStyle().set("align-items", "center");

        randevuKabulButton=new Button("Randevu Kabul");
        randevuKabulButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        detailsFooter.add(randevuKabulButton);

        detailsFooterLayout.add(detailsFooter);
        
        return detailsFooterLayout;
    }

    private Scroller addWithScroller(Component... childerens){
        Div detailsDiv=new Div(childerens);
        detailsDiv.setSizeFull();
        detailsDiv.getStyle().set("align-items", "stretch").set("padding", "false");
        
        Scroller scroller = new Scroller(detailsDiv);
        scroller.setScrollDirection(ScrollDirection.VERTICAL);
        scroller.getStyle()
            .set("border-bottom", "1px solid var(--lumo-contrast-20pct)");
        scroller.setHeight("280px");
        scroller.setWidth("350px");
        return scroller;
    }

}
