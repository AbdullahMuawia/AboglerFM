package com.aboglerfm.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class TimelinePointDto implements Serializable{
    private LocalDate day;
    private Long plays;

    private static final long serialVersionUID = 1L;

    public TimelinePointDto(LocalDate day, Long plays) {
        this.day = day;
        this.plays = plays;
    }

    public TimelinePointDto(LocalDate day, long plays) {
        this.day = day;
        this.plays = plays;
    }

    public TimelinePointDto(java.sql.Date day, Long plays) {
        this.day = day.toLocalDate();
        this.plays = plays;
    }

    public TimelinePointDto(java.sql.Date day, long plays) {
        this.day = day.toLocalDate();
        this.plays = plays;
    }

    public TimelinePointDto(LocalDateTime day, Long plays) {
        this.day = day.toLocalDate();
        this.plays = plays;
    }

    public TimelinePointDto(LocalDateTime day, long plays) {
        this.day = day.toLocalDate();
        this.plays = plays;
    }

    public TimelinePointDto(java.sql.Timestamp day, Long plays) {
        this.day = day.toLocalDateTime().toLocalDate();
        this.plays = plays;
    }

    public TimelinePointDto(java.sql.Timestamp day, long plays) {
        this.day = day.toLocalDateTime().toLocalDate();
        this.plays = plays;
    }

    public TimelinePointDto(Date day, Long plays) {
        this.day = new java.sql.Date(day.getTime()).toLocalDate();
        this.plays = plays;
    }

    public TimelinePointDto(Date day, long plays) {
        this.day = new java.sql.Date(day.getTime()).toLocalDate();
        this.plays = plays;
    }
}