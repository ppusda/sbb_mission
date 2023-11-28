package com.sbb.sbb_mission.question.service;

import com.sbb.sbb_mission.member.entity.Member;
import com.sbb.sbb_mission.question.entity.Question;
import com.sbb.sbb_mission.question.repository.QuestionRepository;
import com.sbb.sbb_mission.question.request.QuestionRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Page<Question> getQuestionList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return questionRepository.findAll(pageable);
    }

    public Question getQuestion(Long qid) {
        Optional<Question> question = questionRepository.findById(qid);
        if (question.isEmpty()) {
            throw new NoSuchElementException();
        }
        return question.get();
    }

    public void saveQuestion(QuestionRequest questionRequest, Member member) {
        Question question = Question.builder()
                .subject(questionRequest.subject())
                .content(questionRequest.content())
                .author(member)
                .build();

        this.questionRepository.save(question);
    }

    public void addQuestion() {
        for(int i = 1; i <= 100; i++) {
            Question question = Question.builder()
                    .subject("sbb가 무엇인가요?" + i + "트")
                    .content("sbb에 대해서 알고 싶습니다." + i + "트")
                    .build();

            this.questionRepository.save(question);
        }
    }

}
