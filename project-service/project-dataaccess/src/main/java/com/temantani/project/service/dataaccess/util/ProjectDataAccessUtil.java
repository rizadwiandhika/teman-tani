package com.temantani.project.service.dataaccess.util;

import java.sql.SQLException;

import javax.persistence.PersistenceException;

import org.postgresql.util.PSQLState;
import org.springframework.stereotype.Component;

@Component
public class ProjectDataAccessUtil {

  public Boolean isUniqueViolation(PersistenceException e) {
    if (e.getCause().getCause() instanceof SQLException == false) {
      return false;
    }

    SQLException sqlException = (SQLException) e.getCause().getCause();
    return sqlException != null && sqlException.getSQLState() != null
        && PSQLState.UNIQUE_VIOLATION.getState().equals(sqlException.getSQLState());
  }

}
