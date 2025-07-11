package org.bram.services;

import org.bram.TestConfig.CloudinaryTestConfig;
import org.bram.data.repository.CustomerRepository;
import org.bram.data.repository.UserRepository;
import org.bram.dtos.request.*;
import org.bram.dtos.response.ApiResponse;
import org.bram.dtos.response.LoginResponse;
import org.bram.dtos.response.RegisterResponse;
import org.bram.exceptions.IncorrectOldEmailException;
import org.bram.exceptions.IncorrectOldPasswordException;
import org.bram.exceptions.SameEmailException;
import org.bram.exceptions.SamePasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Import(CloudinaryTestConfig.class)
class CustomerServicesImplTest {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private CustomerServicesImpl customerServices;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    private RegisterRequest registerRequest;
    private RegisterResponse registerResponse;
    private LoginRequest loginRequest;
    private LoginResponse loginResponse;
    private ChangeEmailRequest changeEmailRequest;
    private ChangePasswordRequest changePasswordRequest;
    private UpdateCustomerProfileRequest updateRequest;
    private ApiResponse apiResponse;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        customerRepository.deleteAll();
        registerRequest = new RegisterRequest();
        registerResponse = new RegisterResponse();
        loginResponse = new LoginResponse();
        loginRequest = new LoginRequest();
        changeEmailRequest = new ChangeEmailRequest();
        changePasswordRequest = new ChangePasswordRequest();
        updateRequest = new UpdateCustomerProfileRequest();
        apiResponse = new ApiResponse();
    }

    @Test
    public void changeCustomerEmail__changeEmailTest() {
        registerACustomerAndLogin();
        assertEquals("Registered successfully", registerResponse.getMessage());
        assertEquals("Welcome back Amanda Onyekachi", loginResponse.getMessage());

        changeEmailRequest.setOldEmail("amanda@gmail.com");
        changeEmailRequest.setNewEmail("onyekachi@gmail.com");
        apiResponse = customerServices.changeEmail(changeEmailRequest);
        assertTrue(apiResponse.isSuccess());
        assertEquals("Email changed successfully", apiResponse.getMessage());
    }

    @Test
    public void changeCustomerEmailWithSameOldEmail__throwsException() {
        registerACustomerAndLogin();
        changeEmailRequest.setOldEmail("amanda@gmail.com");
        changeEmailRequest.setNewEmail("amanda@gmail.com");
        Exception error = assertThrows(SameEmailException.class,()-> customerServices.changeEmail(changeEmailRequest));
        assertEquals("New email cannot be same as old email", error.getMessage());
    }

    @Test
    public void changeCustomerEmailWithWrongOldEmail__throwsException() {
        registerACustomerAndLogin();
        changeEmailRequest.setOldEmail("amanda@yahoo.com");
        changeEmailRequest.setNewEmail("amanda@fake.com");
        Exception error = assertThrows(IncorrectOldEmailException.class, () -> customerServices.changeEmail(changeEmailRequest));
        assertEquals("Old email not correct", error.getMessage());
    }

    @Test
    public void changeCustomerPassword__changePasswordTest() {
        registerACustomerAndLogin();
        changePasswordRequest.setOldPassword("password111");
        changePasswordRequest.setNewPassword("password222");
        apiResponse = customerServices.changePassword(changePasswordRequest);
        assertTrue(apiResponse.isSuccess());
        assertEquals("Password changed successfully", apiResponse.getMessage());
    }

    @Test
    public void changedPasswordWithSameOldPassword__throwsException() {
        registerACustomerAndLogin();
        changePasswordRequest.setOldPassword("password111");
        changePasswordRequest.setNewPassword("password111");
        Exception error = assertThrows(SamePasswordException.class, ()-> customerServices.changePassword(changePasswordRequest));
        assertEquals("New password cannot be the same as old password", error.getMessage());
    }

    @Test
    public void changePasswordWithWrongOldPassword() {
        registerACustomerAndLogin();
        changePasswordRequest.setOldPassword("password555");
        changePasswordRequest.setNewPassword("password222");
        Exception error = assertThrows(IncorrectOldPasswordException.class, ()-> customerServices.changePassword(changePasswordRequest));
        assertEquals("Old password not correct", error.getMessage());
    }

    @Test
    public void updateCustomerProfileTest() {
        registerACustomerAndLogin();
        updateRequest.setPhoneNumber("0904-272-3719");
        updateRequest.setHouseNumber("10");
        updateRequest.setStreet("Main street");
        updateRequest.setCity("Lagos city");
        updateRequest.setState("Lagos state");
        updateRequest.setCountry("Nigeria");

        apiResponse = customerServices.updateProfile(updateRequest);
        assertTrue(apiResponse.isSuccess());
        assertEquals("Profile updated successfully", apiResponse.getMessage());

    }

    private void registerACustomerAndLogin() {
        registerRequest.setFirstName("Amanda");
        registerRequest.setLastName("Onyekachi");
        registerRequest.setEmail("amanda@gmail.com");
        registerRequest.setPassword("password111");
        registerRequest.setPhone("09018754229");
        registerRequest.setUserRole("Customer");
        registerResponse = authenticationService.register(registerRequest);

        loginRequest.setEmail("amanda@gmail.com");
        loginRequest.setPassword("password111");
        loginResponse = authenticationService.login(loginRequest);
        var auth = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), null, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}