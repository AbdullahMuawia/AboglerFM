package com.aboglerfm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TopTrackDto implements Serializable{

    private String name;
    private String artist;
    private String album;
    private long plays;
    private static final long serialVersionUID = 1L;
}
