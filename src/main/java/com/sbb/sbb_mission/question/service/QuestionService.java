package com.sbb.sbb_mission.question.service;

import com.sbb.sbb_mission.question.entity.Question;
import com.sbb.sbb_mission.question.repository.QuestionRepository;
import com.sbb.sbb_mission.question.request.QuestionRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getQuestionList() {
        return questionRepository.findAll();
    }

    public Question getQuestion(Long qid) {
        Optional<Question> question = questionRepository.findById(qid);
        if (question.isEmpty()) {
            throw new NoSuchElementException();
        }
        return question.get();
    }

    public void saveQuestion(QuestionRequest questionRequest) {
        Question question = Question.builder()
                .subject(questionRequest.subject())
                .content(questionRequest.content())
                .build();

        this.questionRepository.save(question);
    }

    public void addQuestion() {
        Question question1 = Question.builder()
                .subject("sbb가 무엇인가요?")
                .content("sbb에 대해서 알고 싶습니다.")
                .build();

        this.questionRepository.save(question1);


        Question question2 = Question.builder()
                .subject("스프링부트 모델 질문입니다.")
                .content("id는 자동으로 생성되나요?")
                .build();

        this.questionRepository.save(question2);
    }

}
