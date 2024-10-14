package io.datadynamics.pilot.kafka.service;

import io.datadynamics.pilot.kafka.model.FilterConditions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterConditionsRepository extends CrudRepository<FilterConditions, String> {
}
