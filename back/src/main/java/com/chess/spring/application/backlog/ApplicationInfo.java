package com.chess.spring.application.backlog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationInfo {
    private String projectName;
    private String projectDescription;
    private String version;
    private String profile;
}
