package com.temantani.domain.helper;

import static com.temantani.domain.DomainConstant.TIMEZONE;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class Helper {

  public static final Boolean validateNonNull(Object... field) {
    return List.of(field).stream().allMatch((f) -> f != null);
  }

  public static final Boolean validateNonEmpty(String... field) {
    return List.of(field).stream().allMatch((s) -> {
      Boolean b = s != null;
      b = s.isBlank() == false;
      return b;
    });
  }

  public static final ZonedDateTime now() {
    return ZonedDateTime.now(ZoneId.of(TIMEZONE));
  }

}
