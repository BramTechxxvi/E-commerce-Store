package org.bram.services;

import org.bram.TestConfig.CloudinaryTestConfig;
import org.bram.data.repository.*;
import org.bram.dtos.request.*;
import org.bram.dtos.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Import(CloudinaryTestConfig.class)
public class AuthenticationServiceImplTest {

    @Autowired
    private AuthenticationService authenticationService;
    private RegisterRequest registerRequest;
    private RegisterResponse registerResponse;
    private LoginRequest loginRequest;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private AdminRepository adminRepository;

    @BeforeEach
    public void setUp(){
        registerRequest = new RegisterRequest();
        registerResponse = new RegisterResponse();
        customerRepository.deleteAll();
        loginRequest = new LoginRequest();
        userRepository.deleteAll();
        sellerRepository.deleteAll();
        adminRepository.deleteAll();
    }

    @Test
    public void registerACustomer__registerTest() {
        registerCustomer();
        assertNotNull(registerResponse);
        assertTrue(registerResponse.isSuccess());
        assertEquals(1, customerRepository.count());
        assertEquals(1, userRepository.count());
    }

    @Test
    public void registerASeller__registerTest() {
        registerSeller();
        assertNotNull(registerResponse);
        assertTrue(registerResponse.isSuccess());
        assertEquals(1, sellerRepository.count());
        assertEquals(1, userRepository.count());
    }

    @Test
    public void registerAnAdmin__registerTest() {
        registerAdmin();
        assertNotNull(registerResponse);
        assertTrue(registerResponse.isSuccess());
        assertEquals(1, adminRepository.count());
        assertEquals(1, userRepository.count());
    }

    @Test
    public void loginCustomer__loginTest() {
        registerCustomer();
        loginRequest.setEmail("John@doe.com");
        loginRequest.setPassword("password111");
        LoginResponse loginResponse = authenticationService.login(loginRequest);
        assertNotNull(loginResponse.getToken());
        assertEquals("Welcome back John Doe", loginResponse.getMessage());
    }

    @Test
    public void loginSeller__loginTest() {
        registerSeller();
        loginRequest.setEmail("grace@ayoola.com");
        loginRequest.setPassword("password111");
        LoginResponse loginResponse = authenticationService.login(loginRequest);
        assertNotNull(loginResponse.getToken());
        assertEquals("Welcome back Grace Ayoola", loginResponse.getMessage());
        assertTrue(loginResponse.isSuccess());
    }

    @Test
    public void logoutSeller__logoutTest() {
        registerSeller();
        loginRequest.setEmail("grace@ayoola.com");
        loginRequest.setPassword("password111");
        LoginResponse loginResponse = authenticationService.login(loginRequest);
        assertTrue(loginResponse.isSuccess());
        String token = loginResponse.getToken();
        LogoutResponse logoutResponse = authenticationService.logout(token);
        assertTrue(logoutResponse.isSuccess());
        assertEquals("We hope to see you soon...", logoutResponse.getMessage());
    }

    private void registerCustomer() {
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("john@doe.com");
        registerRequest.setPassword("password111");
        registerRequest.setPhone("090373542523");
        registerRequest.setUserRole("CUSTOMER");

        registerResponse = authenticationService.register(registerRequest);
    }

    private void registerSeller() {
        registerRequest.setFirstName("Grace");
        registerRequest.setLastName("Ayoola");
        registerRequest.setEmail("grace@ayoola.com");
        registerRequest.setPassword("password111");
        registerRequest.setPhone("090373542529");
        registerRequest.setUserRole("SELLER");

        registerResponse = authenticationService.register(registerRequest);
    }

    private void registerAdmin() {
        registerRequest.setFirstName("Wisdom");
        registerRequest.setLastName("Babalola");
        registerRequest.setEmail("wisdom@gmail.com");
        registerRequest.setPassword("password111");
        registerRequest.setPhone("09037354211");
        registerRequest.setUserRole("ADMIN");

        registerResponse = authenticationService.register(registerRequest);
    }


}