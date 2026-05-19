package com.aboglerfm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TopArtistDto implements Serializable{

    private String artist;
    private long plays;
    private static final long serialVersionUID = 1L;
}
