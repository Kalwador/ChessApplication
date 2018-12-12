package com.chess.spring.services;

import com.chess.spring.dto.ApplicationInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
    @Value("${application.project.name}")
    private String projectName;

    @Value("${application.project.description}")
    private String projectDescription;

    @Value("${application.project.version}")
    private String version;

    @Value("${spring.profiles.active}")
    private String profile;

    public ApplicationInfo getInfo() {
        return ApplicationInfo.builder()
                .version(version)
                .projectName(projectName)
                .projectDescription(projectDescription)
                .profile(profile)
                .build();
    }

    //TODO - work log z gita
}
