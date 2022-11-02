package com.stussy.stussyclone20220929jaehyo.service.auth;

import com.stussy.stussyclone20220929jaehyo.domain.User;
import com.stussy.stussyclone20220929jaehyo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = accountRepository.findUserByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
        }

        return new PrincipalDetails(user);
    }
}
