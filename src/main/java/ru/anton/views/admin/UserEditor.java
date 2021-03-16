package ru.anton.views.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import ru.anton.data.entity.Role;
import ru.anton.data.entity.User;
import ru.anton.data.repository.UserRepository;

@SpringComponent
@UIScope
public class UserEditor extends VerticalLayout {
    private UserRepository userRepository;

    private User user;

    TextField username = new TextField("Username");
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    Button cancel = new Button("Cancel");

    HorizontalLayout actions = new HorizontalLayout(save, delete, cancel);

    Binder<User> binder = new Binder<>(User.class);

    public interface ChangeHandler {
        void onChange();
    }

    private ChangeHandler changeHandler;

    @Autowired
    public UserEditor(UserRepository userRepository) {
        this.userRepository = userRepository;

        add(username,actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editUser(user));
        setVisible(false);
    }

    public final void editUser(User u) {
        if(u == null){
            setVisible(false);
            return;
        }

        final boolean persisted  = u.getId() != null;
        if(persisted){
            user = userRepository.findById(u.getId()).get();
        }else{
            user = u;
        }
        cancel.setVisible(persisted);
        binder.setBean(user);
        setVisible(true);
        username.focus();
    }

    private void delete() {
       if(user.getRole().equals(Role.ADMIN)){
           Notification.show("The administrator cannot be deleted");
       }else {
           userRepository.delete(user);
       }
    }

    void save(){
        userRepository.save(user);
        changeHandler.onChange();
    }

    public void setChangeHandler(ChangeHandler h){
        changeHandler = h;
    }


}
