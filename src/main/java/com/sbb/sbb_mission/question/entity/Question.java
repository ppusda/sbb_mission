package com.sbb.sbb_mission.question.entity;

import com.sbb.sbb_mission.answer.entity.Answer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity @Getter
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDate modifyDate;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @Builder
    public Question(String subject, String content, List<Answer> answerList) {
        this.subject = subject;
        this.content = content;
        this.answerList = answerList;
    }

    public void modifyQuestion(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
}
