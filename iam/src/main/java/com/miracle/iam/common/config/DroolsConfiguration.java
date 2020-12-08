package com.miracle.iam.common.config;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.spring.annotations.KModuleAnnotationPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Configuration
@Slf4j
public class DroolsConfiguration {

    private Resource[] getRuleFiles(String rulePath) throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResources("classpath*:" + rulePath + "**/*.*");
    }

    @Bean
    public KieServices kieServices(){
        return KieServices.Factory.get();
    }

    @Bean
    public KieFileSystem KieFileSystem(KieServices kieServices){
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        String rules = "";
        if(StringUtils.isEmpty(rules)){

        }
        kieFileSystem.write("",rules);
        return kieFileSystem;
    }

    @Bean
    public KieContainer kieContainer(KieServices kieServices,KieFileSystem kieFileSystem){
        KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(()->kieRepository.getDefaultReleaseId());
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        return kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
    }

    @Bean
    public KieSession kiesession(KieContainer kieContainer){
        return kieContainer.newKieSession();
    }

    @Bean
    public KieBase kieBase(KieContainer kieContainer){
        return kieContainer.getKieBase();
    }

    @Bean
    public KModuleAnnotationPostProcessor kiePostProcessor(){
        return new KModuleAnnotationPostProcessor();
    }

}
