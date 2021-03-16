package ru.anton.data.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.anton.Application;
import ru.anton.data.entity.CorrectAnswer;
import ru.anton.data.entity.Question;
import ru.anton.data.entity.Role;
import ru.anton.data.entity.User;
import ru.anton.data.repository.AnswerDetailsRepository;
import ru.anton.data.repository.CorrectAnswerRepository;
import ru.anton.data.repository.QuestionRepository;
import ru.anton.data.repository.UserRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

@Service
public class DataLoaderQuestions implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(DataLoaderQuestions.class.getName());

    private final CorrectAnswerRepository answerRepository;

    private final QuestionRepository questionRepository;

    private UserRepository userRepository;

    private final AnswerDetailsRepository answerDetailsRepository;

    public DataLoaderQuestions(CorrectAnswerRepository correctAnswerRepository,
                               QuestionRepository questionRepository, UserRepository userRepository, AnswerDetailsRepository answerDetailsRepository) {
        this.answerRepository = correctAnswerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.answerDetailsRepository = answerDetailsRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Application obj = new Application();


        InputStream stringReaderAllTest = obj.getClass()
                .getClassLoader()
                .getResourceAsStream("static/Thermal_power_plants.txt");


        InputStream strReaderCorrectAnswer = obj.getClass()
                .getClassLoader()
                .getResourceAsStream("static/Answer.txt");


        String[] splitAllTest = getStrings(stringReaderAllTest);

        String[] resultAllTest = Arrays.copyOfRange(splitAllTest, 1, splitAllTest.length);

        log.info("Save in questionRepository Questions with test options:");
        log.info("--------------------------------------------------------");
        for (String s : resultAllTest) {
            String[] strings = s.split("\\n");
            questionRepository.save(new Question(strings[1], Arrays.asList(Arrays.copyOfRange(strings, 2, strings.length))));
        }
        log.info("");


        String[] correctAnswers = getStrings(strReaderCorrectAnswer);

        String[] resultCorrectAnswer = Arrays.copyOfRange(correctAnswers, 1, correctAnswers.length);

        log.info("Save in answerRepository correct answer:");
        log.info("----------------------------------------");
        for (String s : resultCorrectAnswer) {
            String[] split = s.split("\\n");

            answerRepository.save(new CorrectAnswer(split[2]));
        }
        log.info("");

        log.info("Save user's");
        log.info("-----------------------------------------");
        userRepository.save(new User("user", "u", Role.USER));
        userRepository.save(new User("admin", "a", Role.ADMIN));
        log.info("");
        //answerDetailsRepository.save(new AnswerDetails(null, null));
        stringReaderAllTest.close();
        strReaderCorrectAnswer.close();

    }

    private String[] getStrings(InputStream stringReaderAllTest) throws IOException {
        StringBuilder fileContent;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stringReaderAllTest))) {

            fileContent = new StringBuilder();
            String st;
            while ((st = br.readLine()) != null) {
                if (st.contains("Правила по охране труда при эксплуатации тепловых энергоустановок"))
                    continue;
                if (st.contains("Мероприятия по оказани первой помощи (Приказ Минздрава России от 04.05.2012 № 477н)"))
                    continue;
                fileContent.append(st)
                        .append("\n");
            }
        }


        return fileContent.toString()
                .split("Вопрос \\d+");
    }
}
