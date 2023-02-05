package com.zerobase.cms.user.client.service.customer;

import com.zerobase.cms.user.client.domain.customer.ChangeBalanceFrom;
import com.zerobase.cms.user.client.domain.model.CustomerBalanceHistory;
import com.zerobase.cms.user.client.domain.repository.CustomerBalanceHistoryRepository;
import com.zerobase.cms.user.client.domain.repository.CustomerRepository;
import com.zerobase.cms.user.client.exception.CustomException;
import com.zerobase.cms.user.client.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerBalanceService {

  private final CustomerBalanceHistoryRepository customerBalanceHistoryRepository;
  private final CustomerRepository customerRepository;

  @Transactional(noRollbackFor = {CustomException.class})
  public CustomerBalanceHistory changeBalance(Long customerId, ChangeBalanceFrom from)
      throws CustomException {
    CustomerBalanceHistory customerBalanceHistory = customerBalanceHistoryRepository.findFirstByCustomer_IdAndOrderByDesc(
            customerId)
        .orElse(CustomerBalanceHistory.builder()
            .changeMoney(0)
            .currentMoney(0)
            .customer(customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER)))
            .build());

    //마이너스 통장의 경우
    if (customerBalanceHistory.getCurrentMoney() + from.getMoney() < 0) {
      throw new CustomException(ErrorCode.NOT_ENOUGH_BALANCE);
    }

    customerBalanceHistory = CustomerBalanceHistory.builder()
        .changeMoney(from.getMoney())
        .currentMoney(customerBalanceHistory.getCurrentMoney())
        .description(from.getMessage())
        .fromMessage(from.getFrom())
        .customer(customerBalanceHistory.getCustomer())
        .build();
    customerBalanceHistory.getCustomer().setBalance(customerBalanceHistory.getChangeMoney());

    return customerBalanceHistory;
  }


}
