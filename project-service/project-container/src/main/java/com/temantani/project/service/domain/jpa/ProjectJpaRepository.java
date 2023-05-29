package com.temantani.project.service.domain.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectJpaRepository extends JpaRepository<ProjectEntity, UUID> {

  // @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.posts p WHERE u.id =
  // :userId AND p.title = :postTitle")
  // Optional<ProjectEntity> findUserWithPostsByTitle(@Param("userId") Long
  // userId, @Param("postTitle") String postTitle);

  // This will fetch project along with only investment of the specified
  // investmentId
  @Query("SELECT p FROM ProjectEntity p LEFT JOIN FETCH p.investments i WHERE i.id = :investmentId")
  // This will fetch project along with all associated investments
  // @Query("SELECT p FROM ProjectEntity p WHERE p.id = (SELECT i.project.id FROM
  // InvestmentEntity i WHERE i.id = :investmentId)")
  Optional<ProjectEntity> findByInvestmentId(@Param("investmentId") UUID investmentId);

  // @Query("SELECT p FROM ProjectEntity p WHERE p.status = :projectStatus AND
  // p.id NOT IN (SELECT i.project.id FROM InvestmentEntity i WHERE i.status =
  // :investmentStatus)")
  // Optional<List<ProjectEntity>> findByProjectStatusAndInvestmentStatusNot(
  // @Param("projectStatus") String projectStatus,
  // @Param("investmentStatus") String investmentStatus);

  // @Query("SELECT DISTINCT p FROM ProjectEntity p LEFT JOIN FETCH p.investments
  // i WHERE p.status = :projectStatus AND p.id NOT IN (SELECT DISTINCT
  // i.project.id FROM InvestmentEntity i WHERE i.status = :investmentStatus)")
  // Optional<List<ProjectEntity>> findByStatusAndNotHavingInvestmentStatus(
  // @Param("projectStatus") String projectStatus,
  // @Param("investmentStatus") String investmentStatus);

  @Query("SELECT p FROM ProjectEntity p WHERE p.status = :projectStatus AND p.id NOT IN (SELECT DISTINCT i.project.id FROM InvestmentEntity i WHERE i.status = :investmentStatus)")
  Optional<List<ProjectEntity>> findByStatusAndNotHavingInvestmentStatus(
      @Param("projectStatus") String projectStatus,
      @Param("investmentStatus") String investmentStatus);

}
