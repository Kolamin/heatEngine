package ru.anton.views.login;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import ru.anton.data.service.AuthService;

@Route("register")
public class RegisterView extends Composite {

    public final AuthService authService;

    private TextField username;
    private PasswordField password1;
    private PasswordField password2;

    public RegisterView(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected Component initContent() {
        username = new TextField("Username");
        password1 = new PasswordField("Password");
        password2 = new PasswordField("Confirm password");
        return new VerticalLayout(
                new H2("Register"),
                username,
                password1,
                password2,
                new Button("Send", event ->register(
                        username.getValue(),
                        password1.getValue(),
                        password2.getValue()
                ))
        );
    }

    private void register(String username, String password1, String password2) {
        if(username.trim().isEmpty()){
            Notification.show("Enter a username");
        }else if(password1.isEmpty()){
            Notification.show("Enter a password");
        }else if(!password1.equals(password2)){
            Notification.show("Password don't match");
        }else{
            authService.register(username, password1);
            Notification.show("Registration succeeded");
        }
        UI.getCurrent().navigate("login");
    }
}
