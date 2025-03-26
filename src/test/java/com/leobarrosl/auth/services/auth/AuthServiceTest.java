package com.leobarrosl.auth.services.auth;

import com.leobarrosl.auth.models.dto.AuthDTO;
import com.leobarrosl.auth.models.dto.LoginResponseDTO;
import com.leobarrosl.auth.models.dto.RegisterDTO;
import com.leobarrosl.auth.models.entity.User;
import com.leobarrosl.auth.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    private User testUser;
    private AuthDTO authDTO;
    private RegisterDTO validRegisterDTO;
    private RegisterDTO invalidRegisterDTO;

    @BeforeEach
    void setUp() {
        testUser = new User("test@example.com", "senha123", "João", "Silva", "123456789");
        authDTO = new AuthDTO("test@example.com", "senha123");
        validRegisterDTO = new RegisterDTO("senha12345", "João", "Silva", "test@example.com", "123456789");
        invalidRegisterDTO = new RegisterDTO("123", "", "Silva", "test@example.com", "123456789");
    }

    @Test
    void loadUserByUsername_DeveRetornarUsuario_QuandoEncontrado() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        var result = authService.loadUserByUsername("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getUsername());
        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    void loadUserByUsername_DeveLancarExcecao_QuandoUsuarioNaoEncontrado() {
        when(userRepository.findByEmail("naoexiste@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            authService.loadUserByUsername("naoexiste@example.com");
        });
    }

    @Test
    void login_DeveRetornarToken_QuandoCredenciaisValidas() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(testUser);
        when(tokenService.generateToken(testUser)).thenReturn("token-teste");

        LoginResponseDTO response = authService.login(authDTO);

        assertNotNull(response);
        assertEquals("token-teste", response.token());
        verify(tokenService).generateToken(testUser);
    }

    @Test
    void register_DeveSalvarUsuario_QuandoDadosValidos() {
        authService.register(validRegisterDTO);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_DeveLancarExcecao_QuandoDadosInvalidos() {
        assertThrows(BadCredentialsException.class, () -> {
            authService.register(invalidRegisterDTO);
        });

        verify(userRepository, never()).save(any(User.class));
    }
}