package com.sbb.sbb_mission.answer.service;

import com.sbb.sbb_mission.answer.entity.Answer;
import com.sbb.sbb_mission.answer.repository.AnswerRepository;
import com.sbb.sbb_mission.answer.request.AnswerRequest;
import com.sbb.sbb_mission.member.entity.Member;
import com.sbb.sbb_mission.question.entity.Question;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Answer getAnswer(Long aid) {
        Optional<Answer> answer = answerRepository.findById(aid);
        if (answer.isEmpty()) {
            throw new NoSuchElementException();
        }
        return answer.get();
    }

    @Transactional
    public void writeAnswer(Question question, AnswerRequest answerRequest, Member member) {
        Answer answer = Answer.builder()
                .content(answerRequest.content())
                .question(question)
                .author(member)
                .build();

        answerRepository.save(answer);
    }

    @Transactional
    public void modifyAnswer(AnswerRequest answerRequest, Long id, String username) {
        Answer answer = getAnswer(id);
        
        if(!checkPrincipal(answer, username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        answer.modifyAnswer(answerRequest.content());
    }

    @Transactional
    public void removeAnswer(Long id, String username) {
        Answer answer = getAnswer(id);

        if(!checkPrincipal(answer, username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }

        answerRepository.deleteById(id);
    }

    public boolean checkPrincipal(Answer answer, String username) {
        return answer.getAuthor().getUsername().equals(username);
    }

    @Transactional
    public void vote(Answer answer, Member member) {
        if (answer.getVoter().contains(member)) {
            answer.getVoter().remove(member);
            answerRepository.save(answer);
            return;
        }

        answer.getVoter().add(member);
        answerRepository.save(answer);
    }


}
