package com.clinic.userservice.dtos.request;

import com.clinic.userservice.enumData.Day;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleRequest {

    private List<Day> days;
    private LocalTime timeFrom;
    private LocalTime timeTo;
    private LocalTime consultationDuration;

}
