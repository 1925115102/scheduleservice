package edu.iu.c322.project.scheduleservice.repository;

import edu.iu.c322.project.scheduleservice.model.Request;
import edu.iu.c322.project.scheduleservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Student, Integer> {
}
