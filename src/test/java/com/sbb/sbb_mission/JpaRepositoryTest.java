package com.sbb.sbb_mission;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sbb.sbb_mission.answer.Answer;
import com.sbb.sbb_mission.answer.AnswerRepository;
import com.sbb.sbb_mission.question.Question;
import com.sbb.sbb_mission.question.QuestionRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("JPA save Question Test")
    void saveTest() {
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

    @Test
    @DisplayName("JPA findAll Test")
    void findAllTest() {
        List<Question> questionList = questionRepository.findAll();
        assertEquals(1, questionList.size());
    }

    @Test
    @DisplayName("JPA findById Test")
    void findByIdTest() {
        Optional<Question> question = this.questionRepository.findById(1L);
        if(question.isPresent()) {
            Question q = question.get();
            assertEquals("sbb가 무엇인가요?", q.getSubject());
        }
    }

    @Test
    @DisplayName("JPA findBySubject Test")
    void findBySubjectTest() {
        Optional<Question> question = this.questionRepository.findBySubject("sbb가 무엇인가요?");
        if(question.isPresent()) {
            Question q = question.get();
            assertEquals(1L, q.getId());
        }
    }

    @Test
    @DisplayName("JPA findBySubjectAndContent Test")
    void findBySubjectAndContentTest() {
        Optional<Question> question = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        if(question.isPresent()) {
            Question q = question.get();
            assertEquals(1L, q.getId());
        }
    }

    @Test
    @DisplayName("JPA findBySubjectLike Test")
    void findBySubjectLikeTest() {
        List<Question> question = this.questionRepository.findBySubjectLike("sbb%");
        if(!question.isEmpty()) {
            Question q = question.get(0);
            assertEquals(1L, q.getId());
        }
    }

    @Test
    @DisplayName("JPA modifyQuestion Test")
    void modifyQuestionTest() {
        Optional<Question> oq = this.questionRepository.findById(2L);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        q.modifyQuestion("수정된 제목", "수정된 내용");
        this.questionRepository.save(q);
    }

    @Test
    @DisplayName("JPA deleteQuestion Test")
    void deleteQuestionTest() {
        assertEquals(2, this.questionRepository.count());
        Optional<Question> oq = this.questionRepository.findById(1L);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        this.questionRepository.delete(q);
        assertEquals(1, this.questionRepository.count());
    }

    @Test
    @DisplayName("Answer Save Test")
    void saveAnswerTest() {
        Optional<Question> oq = questionRepository.findById(2L);
        assertTrue(oq.isPresent());
        Question question = oq.get();

        Answer answer = Answer.builder()
                .content("자동으로 생성됩니다.")
                .question(question)
                .build();

        answerRepository.save(answer);
    }

    @Test @Transactional // fetchType.EAGER(즉시로딩)을 사용해도 됨.
    @DisplayName("Answer Save Test")
    void saveAnswerInQuestionTest() {
        Optional<Question> question = questionRepository.findById(2L);
        assertTrue(question.isPresent());

        List<Answer> answerList = question.get().getAnswerList();

        assertEquals(1, answerList.size());
        assertEquals("자동으로 생성됩니다.", answerList.get(0).getContent());
    }

}
