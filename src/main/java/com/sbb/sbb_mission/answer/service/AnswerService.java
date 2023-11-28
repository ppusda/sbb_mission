package com.sbb.sbb_mission.answer.service;

import com.sbb.sbb_mission.answer.entity.Answer;
import com.sbb.sbb_mission.answer.repository.AnswerRepository;
import com.sbb.sbb_mission.answer.request.AnswerRequest;
import com.sbb.sbb_mission.member.entity.Member;
import com.sbb.sbb_mission.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void writeAnswer(Question question, AnswerRequest answerRequest, Member member) {
        Answer answer = Answer.builder()
                .content(answerRequest.content())
                .question(question)
                .author(member)
                .build();

        answerRepository.save(answer);
    }

}
