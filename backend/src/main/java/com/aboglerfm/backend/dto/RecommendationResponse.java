package com.aboglerfm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationResponse implements Serializable {


    private List<RecommendationDto> recommendations;
    private static final long serialVersionUID = 1L;

}
