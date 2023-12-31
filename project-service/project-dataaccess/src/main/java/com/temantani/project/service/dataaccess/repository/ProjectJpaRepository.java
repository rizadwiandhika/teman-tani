package com.temantani.project.service.dataaccess.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.temantani.project.service.dataaccess.entity.ProjectEntity;

@Repository
public interface ProjectJpaRepository extends JpaRepository<ProjectEntity, UUID> {

  Optional<List<ProjectEntity>> findByLandId(UUID landId);

}
