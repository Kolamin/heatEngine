package ru.anton.data.service;

import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;
import ru.anton.data.entity.Role;
import ru.anton.data.entity.User;
import ru.anton.data.repository.UserRepository;
import ru.anton.views.admin.AdminView;
import ru.anton.views.contact.ContactView;
import ru.anton.views.logout.LogoutView;
import ru.anton.views.main.MainView;
import ru.anton.views.question.QuestionView;
import ru.anton.views.question.TableOfAnswersToQuestionsView;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {



    public class AuthException extends Exception {

    }

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void authenticate(String username, String password) throws AuthException {

        User user = userRepository.getByUsername(username);
        if (user != null && user.checkPassword(password)) {
            VaadinSession.getCurrent().setAttribute(User.class, user);
            createRoutes(user.getRole());
        } else {
            throw new AuthException();
        }
    }

    private void createRoutes(Role role) {
        getAuthorizedRoutes(role).stream()
                .forEach(route ->
                        RouteConfiguration.forSessionScope()
                                .setRoute(route.getRoute(),
                                        route.getView(), MainView.class)
                );
    }

    public List<AuthorizedRoute> getAuthorizedRoutes(Role role) {

        List<AuthorizedRoute> routes = new ArrayList<>();


        if (role.equals(Role.USER)) {

            routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
            routes.add(new AuthorizedRoute("questions", "Г.2.1", QuestionView.class));
            routes.add(new AuthorizedRoute("answers", "Ваши ответы", TableOfAnswersToQuestionsView.class));
            routes.add(new AuthorizedRoute("contacts", "Контакты", ContactView.class));

        } else if (role.equals(Role.ADMIN)) {

            routes.add(new AuthorizedRoute("admin", "Admin", AdminView.class));
            routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
            routes.add(new AuthorizedRoute("questions", "Г.2.1", QuestionView.class));
            routes.add(new AuthorizedRoute("answers", "Ваши ответы", TableOfAnswersToQuestionsView.class));
            routes.add(new AuthorizedRoute("contacts", "Контакты", ContactView.class));

        }

        return routes;
    }

    public void register(String username, String password) {
        userRepository.save(new User(username, password, Role.USER));
    }
}
