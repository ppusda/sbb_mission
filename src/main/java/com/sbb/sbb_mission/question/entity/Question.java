package com.sbb.sbb_mission.question.entity;

import com.sbb.sbb_mission.answer.entity.Answer;
import com.sbb.sbb_mission.global.entity.BaseEntity;
import com.sbb.sbb_mission.member.entity.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity @Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private Member author;

    @Builder
    public Question(String subject, String content, List<Answer> answerList, Member author) {
        this.subject = subject;
        this.content = content;
        this.answerList = answerList;
        this.author = author;
    }

    public void modifyQuestion(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
}
