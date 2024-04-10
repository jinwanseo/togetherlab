package com.studytogether.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")   // 순환참조 방지를 위한 id 값만 비교
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    //  이메일 인증 여부
    private boolean emailVerified;

    // 이메일 인증 토큰
    private String emailCheckToken;

    // 가입 날짜
    private LocalDateTime joinedAt;

    // 프로필 정보
    private String bio;
    // 웹사이트
    private String url;
    // 직업 정보
    private String occupation;
    // 사용자 지역 정보
    private String location;

    @Lob    // 데이터베이스에 큰 데이터를 저장할 때 사용
    @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    // 스터디 생성 알림
    private boolean studyCreatedByEmail;
    private boolean studyCreatedByWeb;

    // 스터디 참여 신청 결과 알림
    private boolean studyEnrollmentResultByEmail;
    private boolean studyEnrollmentResultByWeb;

    // 스터디 업데이트 알림
    private boolean studyUpdatedByEmail;
    private boolean studyUpdatedByWeb;

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
    }

    public void completeSignUp() {
        this.emailVerified = true;
        this.joinedAt = LocalDateTime.now();
    }
}
