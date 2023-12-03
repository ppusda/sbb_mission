package com.sbb.sbb_mission.question.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sbb.sbb_mission.answer.entity.QAnswer;
import com.sbb.sbb_mission.member.entity.QMember;
import com.sbb.sbb_mission.question.entity.QQuestion;
import com.sbb.sbb_mission.question.entity.Question;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Question> searchQuestions(String keyword, Pageable pageable) {
        QQuestion question = QQuestion.question;
        QAnswer answer = QAnswer.answer;
        QMember questionAuthor = new QMember("questionAuthor");
        QMember answerAuthor = new QMember("answerAuthor");

        List<Question> searchedQuestionList = jpaQueryFactory
                .selectFrom(question)
                .leftJoin(question.author, questionAuthor)
                .leftJoin(question.answerList, answer)
                .leftJoin(answer.author, answerAuthor)
                .where(likeKeyword(question.subject, keyword)
                        .or(likeKeyword(question.content, keyword))
                        .or(likeKeyword(questionAuthor.username, keyword))
                        .or(likeKeyword(answer.content, keyword))
                        .or(likeKeyword(answerAuthor.username, keyword)))
                .distinct()
                .fetch();

        return new PageImpl<>(searchedQuestionList, pageable, searchedQuestionList.size());
    }

    private BooleanExpression likeKeyword(StringPath path, String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }
        return path.containsIgnoreCase(keyword);
    }

}
