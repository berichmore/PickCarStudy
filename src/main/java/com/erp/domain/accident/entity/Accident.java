package com.erp.domain.accident.entity;

import com.erp.common.entity.BaseTimeEntity;
import com.erp.domain.car.entity.Car;
import com.erp.domain.client.entity.Client;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "accident")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Accident extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Enumerated(EnumType.STRING)
    @Column(name = "accident_status")
    private AccidentStatus accidentStatus;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "accident_time", nullable = false)
    private LocalDateTime accidentTime;

    @Column(name = "accident_detail")
    private String accidentDetail;

    @Column(name = "accident_locate", nullable = false)
    private String accidentLocate;

    @Column(name = "accident_part")
    private String accidentPart;

    @Column(name = "accident_image")
    private String accidentImage;

    @Column(name = "insurance_info", nullable = false)
    private String insuranceInfo;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "year", nullable = false)
    private String year;
}
