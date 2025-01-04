package com.parting.dippin.entity.report;

import com.parting.dippin.core.base.BaseEntity;
import com.parting.dippin.entity.member.MemberEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member_report")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberReportEntity extends BaseEntity {

    @Id
    @Column(name = "report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private MemberEntity reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_member_id")
    private MemberEntity reportedMember;

    @Builder
    private MemberReportEntity(
            MemberEntity reporter,
            MemberEntity reportedMember
    ) {
        this.reporter = reporter;
        this.reportedMember = reportedMember;
    }
}


