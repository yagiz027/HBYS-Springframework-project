package com.example.application.JdbcTemplateExample.Randevu.View.RandevuTakvimEkrani;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.example.application.JdbcTemplateExample.GenericViews.ErrorDialog.ErrorDialogView;
import com.example.application.JdbcTemplateExample.GenericViews.TakvimView.TakvimView;
import com.example.application.JdbcTemplateExample.Hasta.Model.Hasta;
import com.example.application.JdbcTemplateExample.Personel.Controller.PersonelController;
import com.example.application.JdbcTemplateExample.Personel.Model.Personel;
import com.example.application.JdbcTemplateExample.Randevu.Controller.RandevuController;
import com.example.application.JdbcTemplateExample.Randevu.Model.Randevu;
import com.example.application.JdbcTemplateExample.Randevu.View.RandevuEkrani.RandevuView;
import com.example.application.JdbcTemplateExample.ValueConverters.DateToLocalDateUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class RandevuDateSelectionView extends Dialog {

    private RandevuController randevuController;
    private PersonelController personelController;

    private Hasta hasta;
    private Randevu randevu;
    private Personel personel;

    private String selectedHastaTc;
    private List<Randevu> randevuList = new ArrayList<>();

    private Button randevuTarihKaydetBtn;
    private TakvimView randevuTakvimView;
    private ErrorDialogView errorDialogView;
    private RandevuView randevuView;
    private VerticalLayout takvimLayout = new VerticalLayout();

    private Consumer<LocalDateTime> localStartDateTimeConsumer;
    private Consumer<LocalDateTime> localEndDateTimeConsumer;

    public RandevuDateSelectionView(RandevuController randevuController, PersonelController personelController,
            Personel selectedPersonel, String selectedHastaTc) {
        this.selectedHastaTc = selectedHastaTc;
        this.randevuController = randevuController;
        this.personelController = personelController;
        this.personel = selectedPersonel;

        this.randevuList = randevuController.findAllRandevu();
        add(takvimLayout);

        buildRandevuTakvimView();
    }

    public void dateTimeConsumer(Consumer<LocalDateTime> startDateTimeConsumer,
            Consumer<LocalDateTime> endDateTimeConsumer) {
        this.localStartDateTimeConsumer = startDateTimeConsumer;
        this.localEndDateTimeConsumer = endDateTimeConsumer;
    }

    private VerticalLayout buildRandevuTakvimView() {
        randevuTakvimView = new TakvimView(personel, randevuList);

        randevuTarihKaydetBtn = new Button("Randevu Tarihi Kaydet");
        randevuTarihKaydetBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        takvimLayout.add(randevuTarihKaydetBtn, randevuTakvimView);

        randevuTarihKaydetBtn.addClickListener(e -> {
            LocalDateTime clickedStartDateTime = randevuTakvimView.getClickedStartDateTime();
            LocalDateTime clickedEndDateTime = randevuTakvimView.getClickedEndDateTime();

            if (isSameDaySamePaitient(clickedStartDateTime.toLocalDate(), selectedHastaTc)==true) {
                errorDialogView = new ErrorDialogView(new Exception(),
                        "Aynı gün içerisinde yalnızca bir randevu alabilirsiniz.");
            } else {
                if (clickedStartDateTime == null || clickedEndDateTime == null) {
                    errorDialogView = new ErrorDialogView(new Exception(),
                            "Lütfen geçerli bir randevu tarihi seçiniz.");
                }
                localStartDateTimeConsumer.accept(clickedStartDateTime);
                localEndDateTimeConsumer.accept(clickedEndDateTime);
            }
            close();
        });
        return takvimLayout;
    }

    private boolean isSameDaySamePaitient(LocalDate rowDate, String hastaTc) {
        boolean result = randevuList.stream()
                .filter(r -> r.getRandevuAlanHastaTc() == hastaTc)
                .filter(r -> DateToLocalDateUtil.convertDateToLocalDate(r.getRandevuBaslangicTarih()) == rowDate)
                .findAny().isPresent(); 
        return result;
    }
}
