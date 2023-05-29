package com.temantani.project.service.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.project.service.domain.dto.message.land.LandRegisteredMessage;
import com.temantani.project.service.domain.entity.Land;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.mapper.ProjectDataMapper;
import com.temantani.project.service.domain.ports.input.message.listener.LandRegisteredMessageListener;
import com.temantani.project.service.domain.ports.output.repository.LandRepository;

@Component
public class LandRegisteredMessageListenerImpl implements LandRegisteredMessageListener {

  private final ProjectDataMapper mapper;
  private final LandRepository landRepository;

  public LandRegisteredMessageListenerImpl(ProjectDataMapper mapper, LandRepository landRepository) {
    this.mapper = mapper;
    this.landRepository = landRepository;
  }

  @Override
  @Transactional
  public void createLand(LandRegisteredMessage message) {
    Land land = landRepository.create(mapper.createLandMessageToLand(message));
    if (land == null) {
      throw new ProjectDomainException("Failed to create land");
    }
  }

}
