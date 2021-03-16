package ru.anton.views.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouterLink;
import ru.anton.data.service.AuthService;

@CssImport("./views/login/login-view.css")
@Route(value = "login")
@RouteAlias(value = "")
@PageTitle("Login")
public class LoginView extends VerticalLayout{

    public LoginView(AuthService authService) {

        addClassName("login-view");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password" +
                "");
        add(
                new H1("Welcome"),
                username,
                password,
                new Button("Login", event -> {
                    try {
                        authService.authenticate(username.getValue(), password.getValue());

                        UI.getCurrent()
                                .navigate("questions");
                    } catch (AuthService.AuthException e) {
                        Notification.show("Wrong credentials.");
                    }
                }),
                new RouterLink("Register", RegisterView.class)
        );
    }

}
