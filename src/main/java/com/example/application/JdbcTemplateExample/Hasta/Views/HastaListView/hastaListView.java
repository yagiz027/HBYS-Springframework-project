package com.example.application.JdbcTemplateExample.Hasta.Views.HastaListView;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.example.application.JdbcTemplateExample.MainLayout;
import com.example.application.JdbcTemplateExample.GenericViews.DeleteDataConfirmView.DeleteConfirmView;
import com.example.application.JdbcTemplateExample.GenericViews.ErrorDialog.ErrorDialogView;
import com.example.application.JdbcTemplateExample.Hasta.Controller.HastaController;
import com.example.application.JdbcTemplateExample.Hasta.Controller.HastaKanGrupController;
import com.example.application.JdbcTemplateExample.Hasta.Model.Hasta;
import com.example.application.JdbcTemplateExample.Hasta.Model.HastaKanGrup;
import com.example.application.JdbcTemplateExample.Hasta.Views.updateHastaView.updateHasta;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.RouteScopeOwner;

@Component
@PageTitle("Hasta Listesi")
@Route(value = "hasta-list", layout = MainLayout.class)
@Scope("prototype")
@RouteScopeOwner(updateHasta.class) // Bu view'ın içerisinde belirtilen view'ın parent'ı olduğunu belirtir.
public class hastaListView extends Div {

    private HastaController hastaController;

    private HastaKanGrupController kanGrupController;

    private static Hasta hasta = new Hasta();
    private TextField hastaFindByName;
    private RadioButtonGroup<String> hastaFindByAge;
    private ComboBox<String> hastaFindByEduStatusBox;
    private RadioButtonGroup<String> hastafindByGender;
    private ComboBox<HastaKanGrup> hastaFindByKanGrupBox;

    private Grid<Hasta> hastaGrid = new Grid<>(Hasta.class, false);
    private List<Hasta> hastaList;
    private updateHasta updateHastaDialog;
    private HastaKanGrup defaultKanGrup;

    private Button clearFilter;    
    private GridListDataView<Hasta> dataView;
    private DeleteConfirmView deleteConfirmView;
    private ErrorDialogView errorDialogView;

    public hastaListView(HastaController hastaController, HastaKanGrupController kanGrupController) {
        this.hastaController = hastaController;
        this.kanGrupController = kanGrupController;

        hastaList=hastaController.findAllHasta();
        hastaGrid.setItems(hastaList);
        dataView=hastaGrid.getListDataView();

        add(configureHastaGridLayout());
    }
    private HorizontalLayout gridFilterOperationsLayout(){
        HorizontalLayout gridOperationsLayout=new HorizontalLayout();

        hastaFindByName=new TextField("Hasta Adı/Soyadına göre arama");
        hastaFindByName.setPlaceholder("Hasta Adı/Soyadı giriniz");
        hastaFindByName.setClassName("findHastaByName");
        hastaFindByName.setWidth(16, Unit.EM);
        hastaFindByName.setValueChangeMode(ValueChangeMode.EAGER);
        hastaFindByName.addValueChangeListener(e->{
            if(!e.getValue().isEmpty() || e.getValue()!=null){
                dataView.setFilter(item->item.getHastafirstName().toLowerCase().contains(e.getValue().toLowerCase()) || 
                    item.getHastaLastName().toLowerCase().contains(e.getValue().toLowerCase()));
            }
        });


        hastaFindByEduStatusBox=new ComboBox<>("Hasta eğitim durumuna göre arama");
        hastaFindByEduStatusBox.setClassName("findHastaByEdu");
        hastaFindByEduStatusBox.setPlaceholder("Hasta eğitim durumu seçiniz");
        hastaFindByEduStatusBox.setItems("İlk Okul", "Orta Okul", "Lise", "Üniversite");
        hastaFindByEduStatusBox.setWidth(16, Unit.EM);
        hastaFindByEduStatusBox.addValueChangeListener(e->{
            if(!e.getValue().isEmpty() || e.getValue()!=null){
                dataView.setFilter(item->item.getEducationStatus().toLowerCase().contains(e.getValue().toLowerCase()));
                dataView.refreshAll();
            }
        });

        hastaFindByKanGrupBox=new ComboBox<>("Hasta kan grubuna göre arama");
        hastaFindByKanGrupBox.setClassName("findHastaByKanGrup");
        hastaFindByKanGrupBox.setItems(kanGrupController.getKanGrupList());
        hastaFindByKanGrupBox.setItemLabelGenerator(HastaKanGrup::getKanGrup);
        hastaFindByKanGrupBox.setPlaceholder("Hasta kan grubu seçiniz");
        hastaFindByKanGrupBox.setWidth(16, Unit.EM);
        hastaFindByKanGrupBox.addValueChangeListener(e->{
            if(e.getValue()!=null){
                dataView.setFilter(item->item.getHastaKanGrup().getKanGrup().contains(e.getValue().getKanGrup()));
                dataView.refreshAll();
            }
        });

        hastaFindByAge=new RadioButtonGroup<>();
        hastaFindByAge.setClassName("findHastaByAge");
        hastaFindByAge.setItems("18>","18<");
        hastaFindByAge.addValueChangeListener(e->{
            if(!e.getValue().isEmpty() || e.getValue()!=null){
                if(e.getValue().equals("18>")){
                    dataView.setFilter(item->item.getHastaAge()>18 || item.getHastaAge()==18);
                    dataView.refreshAll();
                }else if(e.getValue().equals("18<")){
                    dataView.setFilter(item->item.getHastaAge()<18);
                    dataView.refreshAll();
                }
            }
        }); 

        hastafindByGender=new RadioButtonGroup<>();
        hastafindByGender.setClassName("findHastaByGender");
        hastafindByGender.setItems("Erkek","Kadın","Diğer");
        hastafindByGender.addValueChangeListener(e->{
            if(!e.getValue().isEmpty() || e.getValue()!=null){
                dataView.setFilter(item->item.getHastaGender().contains(e.getValue()));
                dataView.refreshAll();
            }
        });

        clearFilter=new Button();
        clearFilter.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ICON);
        clearFilter.setIcon(new Icon(VaadinIcon.CLOSE));
        clearFilter.addClickListener(e->clearFilters());
        
        dataView.refreshAll();
        gridOperationsLayout.add(hastaFindByName,hastaFindByEduStatusBox,hastaFindByKanGrupBox,hastaFindByAge,hastafindByGender,clearFilter);
        gridOperationsLayout.setAlignItems(Alignment.END);

        return gridOperationsLayout;
    }
    private void clearFilters(){
        dataView.removeFilters();
        hastaFindByName.clear();
        hastaFindByEduStatusBox.clear();
        hastaFindByKanGrupBox.clear();
        hastaFindByAge.clear();
        hastafindByGender.clear();
    }

    private VerticalLayout configureHastaGridLayout() {
        hastaGrid.addColumn(Hasta::getHastakimlikno).setHeader("Hasta Kimlik NO").setFrozen(true);
        hastaGrid.addColumn(Hasta::getHastafirstName).setHeader("Hasta Adı").setFrozen(true);
        hastaGrid.addColumn(Hasta::getHastaLastName).setHeader("Hasta Soyadı").setFrozen(true);
        hastaGrid.addColumn(Hasta::getHastaEmail).setHeader("Hasta Email");
        hastaGrid.addColumn(Hasta::getHastaTelefon).setHeader("Hasta Telefon");
        hastaGrid.addColumn(Hasta::getHastaAdres).setHeader("Hasta Adres");
        hastaGrid.addColumn(Hasta::getEducationStatus).setHeader("Hasta Eğitim Durumu");
        hastaGrid.addColumn(Hasta::getHastaGender).setHeader("Hasta Cinsiyet");
        hastaGrid.addColumn(Hasta::getHastaDogumTarihi).setHeader("Hasta Doğum Tarihi");
        hastaGrid.addColumn(e -> kanGrupController.getKanGrup(e.getHastaKanGrup()).getKanGrup())
                .setHeader("Hasta Kan Grup");
        hastaGrid.addColumn(Hasta::getHastaAge).setHeader("Hasta Yaşı"); //Non-Column Value: this value getting from data base where given the hastaDogumTarihi value calcuting         
        hastaGrid.addColumn(new ComponentRenderer<>(Button::new, (button, hasta) -> {
            button.addThemeVariants(ButtonVariant.LUMO_ICON,
                    ButtonVariant.LUMO_ERROR,
                    ButtonVariant.LUMO_TERTIARY);
            button.addClickListener(e -> this.deleteHasta(hasta));
            button.setIcon(new Icon(VaadinIcon.TRASH));
        })).setHeader("Manage").setFrozenToEnd(true);   

        hastaGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        hastaGrid.getColumns().forEach(column -> column.setAutoWidth(true));

        hastaGrid.asSingleSelect().addValueChangeListener(h -> {
            if (h.getValue() == null) {
                updateHastaDialog.close();
            } else {
                defaultKanGrup = kanGrupController.getKanGrup(h.getValue().getHastaKanGrup());

                updateHastaDialog = new updateHasta(kanGrupController, defaultKanGrup);

                updateHastaDialog.setHasta(h.getValue());

                updateHastaDialog.setVisible(true);

                updateHastaDialog.open();

                saveUpdatedHasta(hasta);
            }
        });

        VerticalLayout hastaGridLayout=new VerticalLayout(gridFilterOperationsLayout(),hastaGrid);
        
        return hastaGridLayout;
    }
    private void deleteHasta(Hasta hasta){
        deleteConfirmView=new DeleteConfirmView("Bu hastayı silmek istediğinize emin misiniz?");
        deleteConfirmView.open();
        deleteConfirmView.getConfirmButton().addClickListener(e->{
            try{
                hastaController.deleteHasta(hasta.getHastakimlikno());
                reloadHastaList();
                clearFilters();
                deleteConfirmView.close();
            }catch(DataIntegrityViolationException exception){
                errorDialogView=new ErrorDialogView(exception,"Bu hastaya ait aktif bir randevu bulunuyor lütfen önce randevu işlemini sonlandırınız.");
                errorDialogView.open();
                deleteConfirmView.close();
            }
        });
    }

    public void reloadHastaList() {
        hastaGrid.setItems(hastaController.findAllHasta());
    }

    public void saveUpdatedHasta(Hasta UpdatedHasta) {
        updateHastaDialog.updateHastaConsumer(he -> {
            hastaController.updateHasta(new Hasta(
                    he.getHastakimlikno(),
                    he.getHastafirstName(),
                    he.getHastaLastName(),
                    he.getHastaEmail(),
                    he.getHastaTelefon(),
                    he.getHastaAdres(),
                    he.getEducationStatus(),
                    he.getHastaDogumTarihi(),
                    he.getHastaGender(),
                    he.getHastaKanGrup(),
                    he.getHastaAge()));
            reloadHastaList();
            clearFilters();
            updateHastaDialog.close();
        });
    }
}