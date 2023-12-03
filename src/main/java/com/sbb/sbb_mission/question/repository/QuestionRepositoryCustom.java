package com.sbb.sbb_mission.question.repository;

import com.sbb.sbb_mission.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepositoryCustom {

    Page<Question> searchQuestions(String keyword, Pageable pageable);
}
