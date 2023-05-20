package com.temantani.user.service.domain.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {

  private String bank;

  private String accountNumber;

  private String accountHolderName;

}
