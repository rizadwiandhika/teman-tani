package com.temantani.project.service.domain.ports.input.message.listener;

import com.temantani.project.service.domain.dto.message.land.LandRegisteredMessage;

public interface LandRegisteredMessageListener {

  void createLand(LandRegisteredMessage message);

}
