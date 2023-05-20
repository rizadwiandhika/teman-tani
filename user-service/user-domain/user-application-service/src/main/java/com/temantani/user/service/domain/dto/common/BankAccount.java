package com.temantani.user.service.domain.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {

  @Default
  private String bank = "";

  @Default
  private String accountNumber = "";

  @Default
  private String accountHolderName = "";

}
