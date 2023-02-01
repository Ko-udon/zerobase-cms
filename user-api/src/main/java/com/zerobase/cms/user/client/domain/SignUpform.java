package com.zerobase.cms.user.client.domain;

import com.zerobase.cms.user.client.domain.model.Customer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpform {

    private String email;
    private String name;
    private String password;
    private LocalDate birth;
    private String phone;

}
