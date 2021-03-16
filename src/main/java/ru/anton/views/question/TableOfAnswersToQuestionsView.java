package ru.anton.views.question;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import ru.anton.data.entity.AnswerDetails;
import ru.anton.data.repository.AnswerDetailsRepository;
import ru.anton.data.repository.CorrectAnswerRepository;
import ru.anton.data.repository.QuestionRepository;

import java.util.Comparator;

@PageTitle("Answers")
public class TableOfAnswersToQuestionsView extends Div {

   private final AnswerDetailsRepository answerDetailsRepository;

    private final QuestionRepository questionRepository;

    private final CorrectAnswerRepository correctAnswerRepository;

    RadioButtonGroup<String> radioButtonGroup;

    Grid<AnswerDetails> grid;

    @Autowired
    public TableOfAnswersToQuestionsView(AnswerDetailsRepository answerDetailsRepository,
                                         QuestionRepository questionRepository,
                                         CorrectAnswerRepository correctAnswerRepository) {
        this.answerDetailsRepository = answerDetailsRepository;
        this.questionRepository = questionRepository;
        this.correctAnswerRepository = correctAnswerRepository;

        grid = new Grid<>(AnswerDetails.class);
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        grid.addItemDoubleClickListener((ComponentEventListener<ItemDoubleClickEvent<AnswerDetails>>) e -> {
            Dialog dialogAnswer = new Dialog();
            dialogAnswer.add(layoutForDialog(e.getItem().getId(), e, dialogAnswer),
                    new Button("Ответить", event -> {
                       if(correctAnswerRepository
                               .findById(e.getItem()
                                       .getId())
                               .get().getCorrectAnswer()
                               .equals(radioButtonGroup.getValue())){
                           Notification.show("Ответ верный", 3000, Notification.Position.BOTTOM_CENTER)
                                   .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                       }else{
                           Notification.show("Ответ неверный", 3000, Notification.Position.BOTTOM_CENTER).
                           addThemeVariants(NotificationVariant.LUMO_ERROR);

                       }
                    }),
                    new Button("Закрыть", event -> dialogAnswer.close()));
            dialogAnswer.setModal(false);
            dialogAnswer.setDraggable(true);
            dialogAnswer.setResizable(true);
            dialogAnswer.open();
        });
        add(grid);
        grid.setColumns("textQuestion");
        grid.addComponentColumn(item ->{
            Icon icon = null;
            if(item.getStatus() == true){
                icon = VaadinIcon.CHECK.create();
                icon.setColor("green");
            }else if(item.getStatus() == false){
                icon = VaadinIcon.EXCLAMATION.create();
                icon.setColor("red");
            }
            return icon;
        }).setKey("icon").setHeader("Status").setComparator(Comparator.comparing(AnswerDetails::getStatus));

        listAnswerDetails();

    }

    private VerticalLayout layoutForDialog(long id, ItemDoubleClickEvent<AnswerDetails> event, Dialog dialog){
        Span  answer = new Span (event.getItem().getTextQuestion());

        radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioButtonGroup.setItems(questionRepository.findById(id).getTestOptions());

        return new VerticalLayout(answer, radioButtonGroup);

    }

    private void listAnswerDetails() {
        grid.setItems(answerDetailsRepository.findAll());
    }
}
