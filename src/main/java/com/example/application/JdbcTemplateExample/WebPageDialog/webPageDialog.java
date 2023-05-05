package com.example.application.JdbcTemplateExample.WebPageDialog;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.Page;

@Component
public class webPageDialog extends Dialog {

    private Div outerHtmlDiv;
    private Document doc;
    private UI currentPage;
    

    public webPageDialog(){
        buildIFrame();
    }
    private void buildIFrame(){
        outerHtmlDiv = new Div();
        outerHtmlDiv.addClassName("outerHtmlDiv");
        // doc=Jsoup.parse();
    }   
    public String getParentWindow(Page component){
        return component.getHistory().getUI().getElement().getOuterHTML();
    }
}
