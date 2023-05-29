package com.temantani.project.service.dataaccess.util;

import java.sql.SQLException;

import org.postgresql.util.PSQLState;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component
public class ProjectDataAccessUtil {

  public Boolean isUniqueViolation(DataAccessException e) {
    if (e.getRootCause() instanceof SQLException == false) {
      return false;
    }

    SQLException sqlException = (SQLException) e.getRootCause();
    return sqlException != null && sqlException.getSQLState() != null
        && PSQLState.UNIQUE_VIOLATION.getState().equals(sqlException.getSQLState());
  }

}
