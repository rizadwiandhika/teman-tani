package com.temantani.user.service.domain.ports.input.service;

import com.temantani.domain.dto.BasicResponse;
import com.temantani.domain.valueobject.UserId;
import com.temantani.user.service.domain.dto.registration.AdminRegistrationRequest;
import com.temantani.user.service.domain.dto.registration.UserRegistrationRequest;
import com.temantani.user.service.domain.dto.registration.UserRegistrationResponse;
import com.temantani.user.service.domain.dto.roleactivation.RoleActivationRequest;
import com.temantani.user.service.domain.dto.track.UserDetailTrackResponse;
import com.temantani.user.service.domain.dto.updateprofile.UpdateProfileRequest;

public interface UserApplicationService {

  UserRegistrationResponse registerUser(UserRegistrationRequest request);

  BasicResponse registerAdmin(UserId registratorId, AdminRegistrationRequest request);

  BasicResponse updateProfile(UserId initiatorId, UserId userId, UpdateProfileRequest request);

  void activateRole(UserId initiatorId, UserId userId, RoleActivationRequest request);

  UserDetailTrackResponse trackUserDetail(UserId trackerId, UserId userId);

  BasicResponse changePassword(UserId userId, String oldPassword, String newPassword);

}
