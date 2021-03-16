package ru.anton.views.question;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import ru.anton.data.entity.AnswerDetails;
import ru.anton.data.entity.Question;
import ru.anton.data.repository.AnswerDetailsRepository;
import ru.anton.data.repository.CorrectAnswerRepository;
import ru.anton.data.repository.QuestionRepository;

import java.util.List;

@PageTitle("Question")
public class QuestionView extends Div {


    private final QuestionRepository questionRepository;

    private final CorrectAnswerRepository correctAnswerRepository;

    private final AnswerDetailsRepository answerDetails;

    private final List<Question> allQuestion;

    Button btnAbort;
    Button btnAnswer;
    Button btnNextQuestion;
    H3 nameQuestion;

    RadioButtonGroup<String> radioTestOptions;

    private static long ID = 1;

    @Autowired
    public QuestionView(QuestionRepository questionRepository, CorrectAnswerRepository correctAnswerRepository, AnswerDetailsRepository answerDetails1) {

        this.questionRepository = questionRepository;
        this.correctAnswerRepository = correctAnswerRepository;
        this.answerDetails = answerDetails1;


        btnAnswer = new Button("Ответить");
        btnNextQuestion = new Button("Следующий вопрос");



        allQuestion = questionRepository.findAll();

        btnAbort = new Button("Прервать");

        btnAnswer.addClickListener(e -> getCorrectAnswer());

        btnAbort.addClickListener(e -> {

            ID = 1;

            radioTestOptions.setValue(null);
            nameQuestion.setText(questionRepository.findById(ID)
                    .getId() + ". " + questionRepository.findById(ID)
                    .getQuestion());
            radioTestOptions.setItems(questionRepository.findById(ID)
                    .getTestOptions());
            UI.getCurrent()
                    .navigate("questions");
        });

        add(createQuestionLayout(ID), createButtonLayout());


        btnNextQuestion.addClickListener(e -> next());

    }

    public static void setID(long ID) {
        QuestionView.ID = ID;
    }

    private void getCorrectAnswer() {
        if (correctAnswerRepository.findById(ID)
                .getCorrectAnswer()
                .equals(radioTestOptions.getValue())) {
            Notification correctNotice = new Notification();
            Span corrContent = new Span("Верный ответ");
            Button btnCorrect = new Button("Закрыть", e -> correctNotice.close());
            correctNotice.setDuration(3000);
            correctNotice.setPosition(Notification.Position.MIDDLE);
            correctNotice.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            correctNotice.add(corrContent, btnCorrect);

            corrContent.getStyle()
                    .set("margin-right", "0.5rem");
            btnCorrect.getStyle()
                    .set("margin-right", "0.5rem");
            if(!answerDetails.existsById(ID)) {
                answerDetails.save(new AnswerDetails(true, questionRepository.findById(ID).getQuestion()));
            }
            correctNotice.open();
        } else {
            Notification errNotice = new Notification();
            Span errContent = new Span("Неверный ответ");
            Button btnErrCorrect = new Button("Закрыть", e -> errNotice.close());
            errNotice.setDuration(3000);
            errNotice.setPosition(Notification.Position.MIDDLE);
            errNotice.addThemeVariants(NotificationVariant.LUMO_ERROR);
            errNotice.add(errContent, btnErrCorrect);

            errContent.getStyle()
                    .set("margin-right", "0.5rem");
            btnErrCorrect.getStyle()
                    .set("margin-right", "0.5rem");
            if(!answerDetails.existsById(ID)) {
                answerDetails.save(new AnswerDetails(false, questionRepository.findById(ID).getQuestion()));
            }
            errNotice.open();
        }
    }

    private void next() {
        ID = ID + 1;
        if (ID > 190)
            ID = 1;
        radioTestOptions.setValue(null);
        nameQuestion.setText(questionRepository.findById(ID)
                .getId() + ". " + questionRepository.findById(ID)
                .getQuestion());
        radioTestOptions.setItems(questionRepository.findById(ID)
                .getTestOptions());
    }


    private VerticalLayout createQuestionLayout(long ID) {
        radioTestOptions = new RadioButtonGroup<>();
        radioTestOptions.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        nameQuestion = new H3(questionRepository.findById(ID)
                .getId() + ". " + questionRepository.findById(ID)
                .getQuestion());
        radioTestOptions.setItems(questionRepository.findById(ID)
                .getTestOptions());

        radioTestOptions.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                btnNextQuestion.setEnabled(true);
                btnAnswer.setEnabled(true);
            } else {
                btnNextQuestion.setEnabled(false);
                btnAnswer.setEnabled(false);
            }
        });

        return new VerticalLayout(nameQuestion, radioTestOptions);
    }


    private HorizontalLayout createButtonLayout() {
        btnNextQuestion.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnAnswer.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnAbort.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        if (radioTestOptions.getValue() == null) {
            btnNextQuestion.setEnabled(false);
            btnAnswer.setEnabled(false);
        }

        return new HorizontalLayout(btnAnswer, btnNextQuestion, btnAbort);
    }


}
