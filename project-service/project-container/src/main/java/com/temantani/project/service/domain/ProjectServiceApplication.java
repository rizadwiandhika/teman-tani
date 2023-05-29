package com.temantani.project.service.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EntityScan(basePackages = { "com.temantani" })
@EnableJpaRepositories(basePackages = { "com.temantani" })
@RestController
public class ProjectServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProjectServiceApplication.class, args);
  }

  @PostMapping("/accept")
  public ResponseEntity<String> accept(
      @RequestParam("files") List<MultipartFile> files,
      @RequestParam("userId") List<String> userIds) {

    log.info("Files: {}",
        files.stream().map(MultipartFile::getOriginalFilename).collect(Collectors.joining(";", "[", "]")));

    log.info("UserIds: {}", userIds.stream().collect(Collectors.joining(";", "[", "]")));

    return ResponseEntity.ok("OK");
  }

  @Bean
  public CommandLineRunner commandLineRunner(ProjectRunner runner) {
    return args -> {
      try {
        runner.run();
      } catch (Throwable e) {

      }
    };
  }

}