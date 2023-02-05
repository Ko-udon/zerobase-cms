package com.zerobase.cms.user.client.domain.customer;

import lombok.Getter;

@Getter
public class ChangeBalanceFrom {
  private String from;
  private String message;
  private Integer money;
}
