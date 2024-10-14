package io.datadynamics.pilot.kafka.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "sqs_filter_conditions", indexes = @Index(name = "idx_sqs_filter_conditions", columnList = "id"))
public class FilterConditions implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "table_id", columnDefinition = "VARCHAR(50)")
    private String tableId;

    @Column(name = "conditions", columnDefinition = "VARCHAR(255)")
    private String conditions;

    @Column(name = "system_name", columnDefinition = "INTEGER")
    private Integer systemName;
}
