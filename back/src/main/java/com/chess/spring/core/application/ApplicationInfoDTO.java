package com.chess.spring.core.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationInfoDTO {
    private String projectName;
    private String projectDescription;
    private String version;
    private String profile;
}
