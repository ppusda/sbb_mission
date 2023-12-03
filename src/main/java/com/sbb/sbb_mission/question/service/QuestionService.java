package com.sbb.sbb_mission.question.service;

import static jakarta.persistence.criteria.JoinType.LEFT;

import com.sbb.sbb_mission.answer.entity.Answer;
import com.sbb.sbb_mission.member.entity.Member;
import com.sbb.sbb_mission.question.entity.Question;
import com.sbb.sbb_mission.question.repository.QuestionRepository;
import com.sbb.sbb_mission.question.repository.QuestionRepositoryImpl;
import com.sbb.sbb_mission.question.request.QuestionRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

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

    @Transactional
    public void saveQuestion(QuestionRequest questionRequest, Member member) {
        Question question = Question.builder()
                .subject(questionRequest.subject())
                .content(questionRequest.content())
                .author(member)
                .build();

        this.questionRepository.save(question);
    }

    @Transactional
    public void modifyQuestion(QuestionRequest questionRequest, Long id, String username) {
        Question question = getQuestion(id);

        if(!checkPrincipal(question, username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        question.modifyQuestion(questionRequest.subject(), questionRequest.content());
    }

    @Transactional
    public void removeQuestion(Long id, String username) {
        Question question = getQuestion(id);

        if(!checkPrincipal(question, username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }

        questionRepository.deleteById(id);
    }

    public boolean checkPrincipal(Question question, String username) {
        return question.getAuthor().getUsername().equals(username);
    }

    @Transactional
    public void vote(Question question, Member member) {
        if (question.getVoter().contains(member)) {
            question.getVoter().remove(member);
            questionRepository.save(question);
            return;
        }

        question.getVoter().add(member);
        questionRepository.save(question);
    }

    public void addQuestion() {
        for(int i = 1; i <= 100; i++) {
            Question question = Question.builder()
                    .subject("sbb가 무엇인가요?" + i + "트")
                    .content("sbb에 대해서 알고 싶습니다." + i + "트")
                    .author(Member.builder()
                            .username("user" + i)
                            .password("user" + i)
                            .email("user" + i + "@naver.com")
                            .build()).build();

            this.questionRepository.save(question);
        }
    }

    public Page<Question> searchQuestionList(int page, String kw) {
        Pageable pageable = PageRequest.of(page, 10);
        return questionRepository.searchQuestions(kw, pageable);
    }

}
