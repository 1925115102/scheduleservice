package edu.iu.c322.project.scheduleservice.model;

import java.util.List;

public record TimeSlot (int id,
        String startTime,
        String endTime,
        String daysOfWeek){

}
