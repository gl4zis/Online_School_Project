package ru.school.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.school.authservice.exception.AuthException;
import ru.school.authservice.model.Account;
import ru.school.authservice.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findById(username)
                .orElseGet(() -> accountRepository.getByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found")));
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    public boolean isUsernameUnique(String username) {
        return !accountRepository.existsById(username);
    }

    public Account getByRefresh(String refresh) throws AuthException {
        return accountRepository.getByRefreshToken(refresh)
                .orElseThrow(() -> new AuthException("No such refresh token"));
    }
}
