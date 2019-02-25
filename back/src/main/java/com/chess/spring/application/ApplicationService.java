package com.chess.spring.application;

import com.chess.spring.application.articles.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    private ArticleRepository articleRepository;

    @Autowired
    public ApplicationService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ApplicationInfoDTO getInfo() {
        return ApplicationInfoDTO.builder()
                .version(version)
                .projectName(projectName)
                .projectDescription(projectDescription)
                .profile(profile)
                .build();
    }

}
