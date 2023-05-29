package com.temantani.project.service.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.temantani.project.service.domain.dto.ProjectObject;
import com.temantani.project.service.domain.jpa.ProjectEntity;
import com.temantani.project.service.domain.jpa.ProjectJpaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProjectRunner {

  private ProjectJpaRepository projectJpaRepository;
  private ObjectMapper objectMapper;
  private EntityManager manager;
  private Mapper mapper;

  public ProjectRunner(ProjectJpaRepository projectJpaRepository, ObjectMapper objectMapper, EntityManager manager,
      Mapper mapper) {
    this.projectJpaRepository = projectJpaRepository;
    this.objectMapper = objectMapper;
    this.manager = manager;
    this.mapper = mapper;
  }

  @Transactional
  public void run() {
    // testUser();
    testProject();
  }

  private void testProject() {
    Optional<List<ProjectEntity>> op = projectJpaRepository
        .findByStatusAndNotHavingInvestmentStatus("CLOSING", "PENDING");

    if (op.isPresent()) {
      List<ProjectEntity> entities = op.get();
      List<ProjectObject> projects = entities.stream().map(mapper::projectEntityToProjectObject)
          .collect(Collectors.toList());

      log.info(json(projects));
    }

    log.info("===");

    ProjectObject project = projectJpaRepository
        .findByInvestmentId(UUID.fromString("3019e4b0-3c7d-4b62-8a0b-2e656af00fa4"))
        .map(mapper::projectEntityToProjectObject).orElse(null);

    log.info(json(project));

  }

  private String json(Object projects) {
    try {
      return objectMapper.writeValueAsString(projects);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }
    return "";
  }

  // private void testUser() {
  // log.info("Project Service is running...");

  // Optional<ProjectEntity> userOp =
  // userJpaRepository.findUserWithPostsByTitle(1L, "Post Naruto");
  // if (userOp.isEmpty()) {
  // log.info("User not found");
  // return;
  // }
  // ProjectEntity user = userOp.get();

  // try {
  // log.info("User: {}", objectMapper.writeValueAsString(mapEntity(user)));
  // } catch (Exception e) {
  // log.error(e.getMessage());
  // }

  // try {
  // // Using Manager notes:
  // // - if we fetch an existing entity, update attribute, save it, then the
  // manager
  // // will update the record because we use the same object as we fetched
  // // previously
  // // - if we create brand new object with the ID of exisiting record, then
  // manager
  // // will throw EntityExistsException

  // // userJpaRepository.save(UserEntity.builder().id(1L).name("Riza
  // // Enzo").build());

  // manager.persist(ProjectEntity.builder().id(2L).name("Iqbal").build());
  // manager.flush(); // to make sure after this, when fetching data, we get also
  // the ID

  // log.info("User: {}",
  // objectMapper.writeValueAsString(mapEntity(userJpaRepository.findById(2L).get())));

  // } catch (EntityExistsException e) {
  // log.error(e.getMessage(), e);
  // } catch (Exception e) {
  // log.error(e.getMessage(), e);
  // }

  // log.info("END OF RUN");
  // }

  // @Getter
  // @Builder
  // @AllArgsConstructor
  // public static class PostObject {
  // private Long id;
  // private String title;
  // private Long userId;
  // }

  // @Getter
  // @Builder
  // @AllArgsConstructor
  // public static class UserObject {
  // private Long id;
  // private String name;
  // private List<PostObject> posts;
  // }

}
