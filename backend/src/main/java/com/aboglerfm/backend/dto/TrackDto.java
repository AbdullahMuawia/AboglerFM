package com.aboglerfm.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackDto implements Serializable {

    private String name;
    private String artist;
    private String album;
    private String imageUrl;
    private String playedAt;
    private Long playedAtEpoch;
    private boolean nowPlaying;
    private static final long serialVersionUID = 1L;


}
