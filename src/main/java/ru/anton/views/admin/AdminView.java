package ru.anton.views.admin;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import ru.anton.data.entity.User;
import ru.anton.data.repository.UserRepository;

@CssImport("./views/admin/admin-view.css")
@PageTitle("Admin")
public class AdminView extends Div {

    private final UserRepository userRepository;

    private final UserEditor editor;

    final Grid<User> grid;


    public AdminView(UserRepository userRepository, UserEditor editor) {
        this.userRepository = userRepository;
        this.editor = editor;

        this.grid = new Grid<>(User.class);
        add(grid, editor);
        grid.setColumns("id", "username", "role");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        grid.asSingleSelect().addValueChangeListener(e->{
            editor.editUser(e.getValue());
        });
        editor.setChangeHandler(() ->{
            editor.setVisible(false);

        });
        listUsers();
    }

    private void listUsers() {
        grid.setItems(userRepository.findAll());
    }

    public UserEditor getEditor() {
        return editor;
    }
}
