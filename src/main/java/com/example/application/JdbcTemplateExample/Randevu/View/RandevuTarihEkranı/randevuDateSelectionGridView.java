package com.example.application.JdbcTemplateExample.Randevu.View.RandevuTarihEkranı;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelController;
import com.example.application.JdbcTemplateExample.Randevu.Controller.RandevuController;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;

public class randevuDateSelectionGridView extends Dialog{

    private RandevuController randevuController;
    private PersonelController personelController;

    private LocalDate currentDate;
    private Grid<LocalDateTime> calendarGrid;
    private Calendar calendar;
    
    private List<String> daysOfWeek;
    
    public randevuDateSelectionGridView(RandevuController randevuController, PersonelController personelController){
        this.randevuController=randevuController;
        this.personelController=personelController;

        daysOfWeek=new ArrayList<String>();
            daysOfWeek.add("Pazartesi");
            daysOfWeek.add("Salı");
            daysOfWeek.add("Çarşamba");
            daysOfWeek.add("Perşembe");
            daysOfWeek.add("Cuma");
            daysOfWeek.add("Cumartesi");
            daysOfWeek.add("Pazar");
    }

    private void buildDateTimeGrid(){
        calendarGrid=new Grid<LocalDateTime>();        
        
    }
}
