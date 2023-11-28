package com.sbb.sbb_mission.answer.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sbb.sbb_mission.global.entity.BaseEntity;
import com.sbb.sbb_mission.member.entity.Member;
import com.sbb.sbb_mission.question.entity.Question;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity @Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @JsonBackReference
    @JoinColumn(name = "question_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @ManyToOne
    private Member author;

    @Builder
    public Answer(String content, Question question, Member author) {
        this.content = content;
        this.question = question;
        this.author = author;
    }

    public void modifyAnswer(String content) {
        this.content = content;
    }
}
