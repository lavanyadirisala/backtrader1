/*
 * package com.spring.backtracking1.usertest;
 * 
 * import static org.assertj.core.api.Assertions.assertThat;
 * 
 * import static org.junit.Assert.assertEquals; import static
 * org.junit.Assert.assertFalse; import static org.junit.Assert.assertTrue;
 * import static org.junit.jupiter.api.Assertions.assertThrows; import static
 * org.mockito.ArgumentMatchers.any; import static
 * org.mockito.ArgumentMatchers.isNull; import static org.mockito.Mockito.never;
 * import static org.mockito.Mockito.times; import static
 * org.mockito.Mockito.verify; import static org.mockito.Mockito.when;
 * 
 * import java.util.ArrayList; import java.util.Collection.*; import
 * java.util.List;
 * 
 * import org.assertj.core.api.Assertions; import org.hamcrest.core.IsEqual;
 * import org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test;
 * import org.junit.jupiter.api.extension.ExtendWith; import
 * org.junit.runner.RunWith; import org.mockito.InjectMocks; import
 * org.mockito.Mock; import org.mockito.MockitoAnnotations; import
 * org.mockito.junit.MockitoJUnitRunner; import
 * org.mockito.junit.jupiter.MockitoExtension; import
 * org.mockito.stubbing.OngoingStubbing; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.security.core.Authentication; import
 * org.springframework.security.core.GrantedAuthority; import
 * org.springframework.security.core.authority.AuthorityUtils; import
 * org.springframework.security.core.authority.SimpleGrantedAuthority; import
 * org.springframework.security.core.userdetails.User; import
 * org.springframework.security.core.userdetails.UserDetails; import
 * org.springframework.security.core.userdetails.UserDetailsService; import
 * org.springframework.security.core.userdetails.UsernameNotFoundException;
 * import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 * 
 * import com.spring.backtracking1.auth.JwtUtil; import
 * com.spring.backtracking1.entity.ForgotPasswordToken; import
 * com.spring.backtracking1.entity.Roles; import
 * com.spring.backtracking1.entity.UserData; import
 * com.spring.backtracking1.repository.ForgotPasswordTokenRepository; import
 * com.spring.backtracking1.repository.UserRepository; import
 * com.spring.backtracking1.service.UserService;
 * 
 * import io.jsonwebtoken.lang.Collections;
 * 
 * @ExtendWith(MockitoExtension.class) public class UserServiceTest {
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
 * // Creating new User
 * 
 * @Test public void testAddUser_UserExists() { // Arrange UserData userInfo =
 * new UserData(); userInfo.setEmail("manaskumar@gmail.com");
 * userInfo.setPassword("12345");
 * 
 * UserData existingUser = new UserData();
 * existingUser.setEmail("manaskumar@gmail.com"); userRepository.save(userInfo);
 * when(userRepository.findByEmail(userInfo.getEmail())).thenReturn(existingUser
 * );
 * 
 * // Act boolean result = userService.addUser(userInfo);
 * 
 * // Assert assertFalse(result);
 * 
 * }
 * 
 * // Creating New User with Null Values
 * 
 * @Test void testAddUser_Success() { UserData users = new UserData();
 * users.setId(1); users.setEmail("lavanyamadhu@mail.com");
 * users.setFirstName("Lavanya"); users.setLastName("Madhu");
 * users.setPassword("123@"); userRepository.save(users);
 * when(userRepository.findByEmail(users.getEmail())).thenReturn(null);
 * when(passwordEncoder.encode(users.getPassword())).thenReturn(
 * "encodedPassword");
 * 
 * // Act boolean result = userService.addUser(users);
 * 
 * // Assert assertTrue(result); }
 * 
 * @Test public void testLoadUserByUsername_UserFound() { // Arrange String
 * username = "test@example.com"; UserData user = new UserData();
 * user.setEmail(username); user.setPassword("testPassword"); Roles role = new
 * Roles(); role.setRoles("ADMIN"); List<Roles> rolesList = new ArrayList<>();
 * rolesList.add(role); user.setRoles(rolesList);
 * 
 * when(userRepository.findByEmail(username)).thenReturn(user);
 * 
 * // Act UserDetails userDetails = userService.loadUserByUsername(username);
 * 
 * // Assert assertEquals(username, userDetails.getUsername());
 * assertEquals("testPassword", userDetails.getPassword()); assertEquals(1,
 * userDetails.getAuthorities().size()); assertEquals("ADMIN",
 * userDetails.getAuthorities().iterator().next().getAuthority()); }
 * 
 * @Test public void testLoadUserByUsername_UserNotFound() { // Arrange String
 * username = "unknown@example.com";
 * 
 * when(userRepository.findByEmail(username)).thenReturn(null); // Act & Assert
 * assertThrows(UsernameNotFoundException.class, () ->
 * userService.loadUserByUsername(username));
 * 
 * }
 * 
 * @Test public void testCreateAdmin_Success() { // Arrange UserData adminData =
 * new UserData(); adminData.setPassword("testPassword");
 * 
 * String token = "testToken"; String userEmail = "admin@example.com";
 * 
 * when(jwtUtil.getUserNameFromJwtToken(token)).thenReturn(userEmail);
 * 
 * UserDetails userDetails = new User(userEmail, "password",
 * AuthorityUtils.createAuthorityList("ADMIN"));
 * 
 * // when(userService.loadUserByUsername(userEmail)).thenReturn(userDetails);
 * 
 * when(passwordEncoder.encode(adminData.getPassword())).thenReturn(
 * "encodedPassword");
 * 
 * // Act // String result = userService.createAdmin(adminData, token);
 * 
 * // Assert // assertEquals("Admin created", result);
 * userRepository.save(adminData); }
 * 
 * @Test public void testCreateAdmin_NoAdminRole() { // Arrange UserData
 * adminData = new UserData(); String token = "testToken"; String userEmail =
 * "user@example.com";
 * 
 * // when(jwtUtil.getUserNameFromJwtToken(token)).thenReturn(userEmail);
 * 
 * UserDetails userDetails = new User(userEmail, "password",
 * AuthorityUtils.createAuthorityList("USER"));
 * 
 * //when(userService.loadUserByUsername(userEmail)).thenReturn(userDetails);
 * assertThrows(UsernameNotFoundException.class, () ->
 * userService.loadUserByUsername(userEmail)); // Act //String result =
 * userService.createAdmin(adminData, token);
 * 
 * // Assert //assertEquals("Root user not found", result);
 * 
 * 
 * }
 * 
 * @Test public void testCreateAdmin_UserNotFound() { // Arrange UserData
 * adminData = new UserData();
 * 
 * String token = "testToken"; String userEmail = "admin@example.com";
 * 
 * assertThrows(UsernameNotFoundException.class, () ->
 * userService.loadUserByUsername(userEmail)); }
 * 
 * }
 */