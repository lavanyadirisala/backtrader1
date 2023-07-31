/*
 * package com.spring.backtracking1.forgotpwdtest;
 * 
 * import static org.junit.Assert.assertFalse; import static
 * org.junit.Assert.assertTrue; import static
 * org.junit.jupiter.api.Assertions.assertEquals; import static
 * org.mockito.Mockito.when;
 * 
 * import org.junit.jupiter.api.Test; import
 * org.junit.jupiter.api.extension.ExtendWith; import org.mockito.InjectMocks;
 * import org.mockito.Mock; import org.mockito.junit.jupiter.MockitoExtension;
 * import org.springframework.security.core.Authentication; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import
 * com.spring.backtracking1.auth.JwtUtil; import
 * com.spring.backtracking1.entity.ForgotPasswordToken; import
 * com.spring.backtracking1.entity.Password; import
 * com.spring.backtracking1.entity.UserData; import
 * com.spring.backtracking1.repository.ForgotPasswordTokenRepository; import
 * com.spring.backtracking1.repository.UserRepository; import
 * com.spring.backtracking1.service.UserService;
 * 
 * @ExtendWith(MockitoExtension.class) public class ForgotPwdTest {
 * 
 * @InjectMocks private UserService userService;
 * 
 * @Mock private UserRepository userRepository;
 * 
 * @Mock private JwtUtil jwtUtil;
 * 
 * @Mock private ForgotPasswordTokenRepository forgotpwdrepo;
 * 
 * @Mock Authentication authentication;
 * 
 * @Mock private BCryptPasswordEncoder passwordEncoder;
 * 
 * @Test public void testForgotPassword__Success() { String email =
 * "manaskumar@gmail.com"; UserData users = new UserData(1, "Manas", "Kumar",
 * email, "1234", null); userRepository.save(users);
 * when(userRepository.findByEmail(users.getEmail())).thenReturn(users); Boolean
 * result = userService.forgotpwd(email); assertTrue(result); }
 * 
 * @Test void testForgotPassword_NoUser_Success() {
 * when(userRepository.findByEmail("john@gmail.com")).thenReturn(null);
 * 
 * // Act boolean result = userService.forgotpwd("john@gmail.com");
 * 
 * // Assert assertFalse(result); }
 * 
 * @Test public void testResetToNewPwd_Success() { // Arrange String token =
 * "testToken"; String newPwd = "newPassword"; String confirmPwd =
 * "newPassword";
 * 
 * ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
 * forgotPasswordToken.setToken(token); UserData user = new UserData();
 * user.setPassword("oldPassword");
 * when(forgotpwdrepo.findByToken(token)).thenReturn(forgotPasswordToken);
 * when(passwordEncoder.encode(confirmPwd)).thenReturn("encodedNewPassword"); //
 * Act Password pwdDto = new Password(); pwdDto.setToken(token);
 * pwdDto.setNewPassword(newPwd); pwdDto.setConfirmPassword(confirmPwd); boolean
 * result = userService.resetToNewPwd(pwdDto);
 * 
 * // Assert assertTrue(result);
 * 
 * assertEquals("encodedNewPassword", user.getPassword());
 * userRepository.save(user);
 * forgotpwdrepo.deleteById(forgotPasswordToken.getToken_id());
 * 
 * }
 * 
 * @Test public void testResetToNewPwd_InvalidToken() { // Arrange String token
 * = "invalidToken"; String newPwd = "newPassword"; String confirmPwd =
 * "newPassword";
 * 
 * Password pwdDto = new Password(); pwdDto.setToken(token);
 * pwdDto.setNewPassword(newPwd); pwdDto.setConfirmPassword(confirmPwd);
 * 
 * 
 * // Act
 * 
 * boolean result = userService.resetToNewPwd(pwdDto);
 * 
 * // Assert assertFalse(result);
 * 
 * }
 * 
 * @Test public void testResetToNewPwd_ExpiredToken() { // Arrange String token
 * = "expiredToken"; String newPwd = "newPassword"; String confirmPwd =
 * "newPassword";
 * 
 * ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
 * forgotPasswordToken.setToken(token);
 * forgotPasswordToken.setCreatedTime(200000); // Set the token as expired
 * when(forgotpwdrepo.findByToken(token)).thenReturn(forgotPasswordToken);
 * 
 * // Act Password pwdDto = new Password(); pwdDto.setToken(token);
 * pwdDto.setNewPassword(newPwd); pwdDto.setConfirmPassword(confirmPwd); boolean
 * result = userService.resetToNewPwd(pwdDto);
 * 
 * // Assert assertFalse(result);
 * 
 * }
 * 
 * @Test public void testResetToNewPwd_PasswordMismatch() { // Arrange String
 * token = "testToken"; String newPwd = "newPassword"; String confirmPwd =
 * "differentPassword";
 * 
 * ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
 * forgotPasswordToken.setToken(token);
 * 
 * when(forgotpwdrepo.findByToken(token)).thenReturn(forgotPasswordToken);
 * 
 * // Act Password pwdDto = new Password(); pwdDto.setToken(token);
 * pwdDto.setNewPassword(newPwd); pwdDto.setConfirmPassword(confirmPwd); boolean
 * result = userService.resetToNewPwd(pwdDto);
 * 
 * // Assert assertFalse(result);
 * 
 * } }
 */