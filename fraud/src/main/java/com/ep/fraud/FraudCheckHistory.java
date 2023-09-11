package com.ep.fraud;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@JsonPOJOBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FraudCheckHistory {
    @Id
    @SequenceGenerator(name = "fraud_id_seq",
            sequenceName = "fraud_id_seq")
    @GeneratedValue(strategy= GenerationType.SEQUENCE,
            generator = "fraud_id_seq")
    private Integer id;
    private Integer customerId;
    private Boolean isFraudster;
    private LocalDateTime createdAt;

}
