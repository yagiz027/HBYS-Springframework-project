package com.example.application.JdbcTemplateExample.GenericViews.TakvimView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.application.JdbcTemplateExample.Randevu.Model.Randevu;
import com.example.application.JdbcTemplateExample.ValueConverters.DateToLocalDateUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TakvimView extends VerticalLayout{
    private Button prevButton;
    private Button nextButton;
    private DateTimePicker dialogDateTimePicker;

    private LocalDateTime cellDateTime;
    private LocalDateTime currentDateTime;
    private List<String> dayNames;
    private LocalDate startOfWeekDay= LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue()-1);
    private List<Integer> startTimeList = new ArrayList<>();
    private List<Integer> endTimeList = new ArrayList<>();

    private Div lastClickedCell=null;
    
    private final int ROWS=12;
    private final int COLUMNS=7;
    
    private LocalDateTime clickedStartDateTime;
    private LocalDateTime clickedEndDateTime;

    private HorizontalLayout topLayout=new HorizontalLayout();
    private VerticalLayout bottomLayout=new VerticalLayout();
    private List<Randevu> randevuList;

    public TakvimView(List<Randevu> randevuList) {
        this.randevuList=randevuList;

        add(topLayout,bottomLayout);
        bottomLayout.setSpacing(false);
        this.setSpacing(false);
        
        buildTakvimLayoutBody();
        buildAboveCalendarDialogLayout();
    }

    public void buildTakvimLayoutBody(){
        dayNames = List.of("Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar");
        
        int startHour = 6;
        int endHour = 18;
        int timeSlotLength = 1; 
    
        for (int i = startHour; i <= endHour; i += timeSlotLength) {
            startTimeList.add(i);
            endTimeList.add(i + timeSlotLength);
        }
        HorizontalLayout[] rows = new HorizontalLayout[ROWS];
        for (int row = 0; row < ROWS; row++) {

            rows[row] = new HorizontalLayout();
            rows[row].addClassName("row");
            rows[row].setSpacing(false);

            for (int col = 0; col < COLUMNS;col++) {
                final int clickedCol = col;
                Div cell = new Div();
                cell.addClassName("timeCell");
                cell.getStyle().set("border", "1px solid black").set("width", "90px");

                //First row is a banner of this calendar
                if (row == 0) {
                    cell.setText(startOfWeekDay.plusDays(col).format(DateTimeFormatter.ofPattern("dd.MMMM"))+"\n"+ dayNames.get(col));
                    cell.getStyle().set("background", "Aquamarine");
                } 
                else {
                    int startTime = startTimeList.get(row);
                    int endTime = endTimeList.get(row);
                    if (endTime <= endHour && startTime >= startHour) {
                        
                        //Fill the cells with times 
                        String cellText=LocalTime.of(startTime,0) + "-" + LocalTime.of(endTime,0);
                        cell.setText(cellText);

                        //When the clicked a div cell event 
                        LocalTime clickedStartTime=LocalTime.of(startTime,0);
                        LocalTime clickedEndTime=LocalTime.of(endTime,0);

                        if(isCellBeforeCurrentDateTime(LocalDateTime.of(startOfWeekDay.plusDays(clickedCol),clickedStartTime))){
                            pastDateHtmlEvents(cell);
                        }else if(isRandevuExsistInDb(LocalDateTime.of(startOfWeekDay.plusDays(clickedCol),clickedStartTime))==true){
                            ifCellSelectedBeforeHtmlEvents(cell);
                        }else{
                            cellMouseEvents(cell);
                        }

                        cell.addClickListener(e->{
                            if (e.getSource() instanceof Div) {
                                clickedStartDateTime=LocalDateTime.of(startOfWeekDay.plusDays(clickedCol),clickedStartTime);
                                clickedEndDateTime=LocalDateTime.of(startOfWeekDay.plusDays(clickedCol),clickedEndTime);                          
                                dialogDateTimePicker.setValue(clickedEndDateTime);
                            }
                        });
                    } else {
                        cell.setText("");
                    }
                }
                rows[row].add(cell);
            }
        }
        bottomLayout.add(rows);
    }
    
    private void cellMouseEvents(Div cell){
        //Mouse over event 'mouseover' HTML Event
        cell.getElement().addEventListener("mouseover", event ->{
            cell.getStyle().set("background","Aquamarine");
            cell.getStyle().set("cursor","pointer");
        });
        cell.getElement().addEventListener("mouseout", event2->{
            if(lastClickedCell == null || lastClickedCell != cell) {
                cell.getStyle().set("background-color","");
            }
        });
        
        // Click event
        cell.addClickListener(event->{
            if (lastClickedCell != null) {
                lastClickedCell.getStyle().set("background-color","");
            }
            cell.getStyle().set("background-color", "green");
            lastClickedCell = cell;
        });
    }
    private void pastDateHtmlEvents(Div cell){
        cell.getStyle().set("enabled", "false");
        cell.getStyle().set("background-color", "gray");
        cell.getStyle().set("text-color", "red");
        cell.getStyle().set("cursor", "");
        cell.getElement().getStyle().set("pointer-events", "none");
        prevButton.setEnabled(false);
    }

    private void ifCellSelectedBeforeHtmlEvents(Div cell){
        cell.getStyle().set("background-color", "red");
        cell.getStyle().set("cursor", "");
        cell.getStyle().set("enabled", "false");
        cell.getElement().getStyle().set("pointer-events", "none"); //disable click event
    }
    
    private boolean isCellBeforeCurrentDateTime(LocalDateTime cellDateTime){
        currentDateTime=LocalDateTime.now();
        return cellDateTime.isBefore(currentDateTime);
    }
    
    private void buildAboveCalendarDialogLayout(){
        HorizontalLayout aboveGridLayout=new HorizontalLayout();
        dialogDateTimePicker=new DateTimePicker();
        dialogDateTimePicker.setEnabled(false);
        dialogDateTimePicker.setDatePlaceholder("Tarih");
        dialogDateTimePicker.setTimePlaceholder("Saat");

        prevButton=new Button();
        prevButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        prevButton.setIcon(new Icon(VaadinIcon.ARROW_LEFT));
        prevButton.setEnabled(false);
        
        prevButton.addClickListener(e->{
            startOfWeekDay=startOfWeekDay.minusWeeks(1);
            if(startOfWeekDay.equals(LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue()-1))){
                prevButton.setEnabled(true);
            }
            updateCalendar();
        });
        

        nextButton=new Button();
        nextButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        nextButton.setIcon(new Icon(VaadinIcon.ARROW_RIGHT));
        nextButton.addClickListener(e->{
            startOfWeekDay=startOfWeekDay.plusWeeks(1);
            prevButton.setEnabled(true);
            updateCalendar();
        });

        aboveGridLayout.add(dialogDateTimePicker,prevButton,nextButton);
        
        topLayout.add(aboveGridLayout);
    }

    private boolean isRandevuExsistInDb(LocalDateTime cellDateTime){
        for(Randevu randevu:randevuList){
            LocalDateTime baslangicLocalDateTime=DateToLocalDateUtil.convertDateToLocalDate(randevu.getRandevuBaslangicTarih());
            if(randevuList.stream().anyMatch(r->baslangicLocalDateTime.isEqual(cellDateTime))){
                return true;
            }
        }
        return false;
    }

    private void updateCalendar(){
        dialogDateTimePicker.setDatePlaceholder(startOfWeekDay.toString());
        bottomLayout.removeAll();
        buildTakvimLayoutBody();
    }

    public LocalDate getStartOfWeekDay() {
        return startOfWeekDay;
    }

    public void setStartOfWeekDay(LocalDate startOfWeekDay) {
        this.startOfWeekDay = startOfWeekDay;
    }

    public LocalDateTime getClickedStartDateTime() {
        return clickedStartDateTime;
    }

    public void setClickedStartDateTime(LocalDateTime clickedStartDateTime) {
        this.clickedStartDateTime = clickedStartDateTime;
    }

    public LocalDateTime getClickedEndDateTime() {
        return clickedEndDateTime;
    }

    public void setClickedEndDateTime(LocalDateTime clickedEndDateTime) {
        this.clickedEndDateTime = clickedEndDateTime;
    }

    public LocalDateTime clickedStartDateTime(){
        return clickedStartDateTime;
    }

    public LocalDateTime clickedEndDateTime(){
        return clickedEndDateTime;
    }

    public LocalDateTime getCellDateTime() {
        return cellDateTime;
    }

    public void setCellDateTime(LocalDateTime cellDateTime) {
        this.cellDateTime = cellDateTime;
    }
}