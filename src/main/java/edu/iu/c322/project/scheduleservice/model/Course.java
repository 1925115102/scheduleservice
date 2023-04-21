package edu.iu.c322.project.scheduleservice.model;

import java.util.List;

public record Course (int id,
                      String name,
                      String description,
                      String instructor,
                      List<TimeSlot> timeSlots){
}
