package ru.anton.views.logout;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import ru.anton.data.repository.AnswerDetailsRepository;
import ru.anton.views.question.QuestionView;

@Route("logout")
@PageTitle("Logout")
public class LogoutView extends Composite<VerticalLayout> {

    private final AnswerDetailsRepository answerDetailsRepository;


    public LogoutView(AnswerDetailsRepository answerDetailsRepository) {
        this.answerDetailsRepository = answerDetailsRepository;

        UI.getCurrent().getPage().setLocation("login");

        this.answerDetailsRepository.deleteAllInBatch();
        VaadinSession.getCurrent().getSession().invalidate();
        QuestionView.setID(1L);

        VaadinSession.getCurrent().close();
    }
}
