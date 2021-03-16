package ru.anton.views.contact;


import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Contacts")
public class ContactView extends VerticalLayout {
    Anchor anchor;
    H3 textCont;
    public ContactView() {

        textCont = new H3("Telegram canal");
        anchor = new Anchor("https://t.me/antCode", "t.me/antCode");

        add(textCont, anchor);

    }
}
