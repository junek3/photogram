package com.rootlab.photogram.config.auth;

import com.rootlab.photogram.domain.User;
import com.rootlab.photogram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // password 자동 matching: DaoAuthenticationProvider에서 password match
    // AuthenticationProvider 인터페이스를 구현해서 커스터마이징 가능
    // loadUserByUsername 성공시 세션 생성
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("유저 정보가 존재하지 않습니다."));
        User userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            return null;
        } else {
            return new PrincipalDetails(userEntity);
        }
    }
}
