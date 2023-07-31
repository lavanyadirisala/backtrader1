/*
 * package com.spring.backtracking1.usertest;
 * 
 * import static org.assertj.core.api.Assertions.assertThat; import static
 * org.hamcrest.CoreMatchers.nullValue; import static
 * org.junit.jupiter.api.Assertions.assertEquals; import static
 * org.junit.jupiter.api.Assertions.assertNotNull; import java.util.ArrayList;
 * import java.util.List; import java.util.Set;
 * 
 * import org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test;
 * import org.junit.jupiter.api.extension.ExtendWith; import
 * org.mockito.InjectMocks; import org.mockito.Mock; import
 * org.mockito.junit.jupiter.MockitoExtension;
 * 
 * import com.spring.backtracking1.entity.Roles; import
 * com.spring.backtracking1.entity.UserData;
 * 
 * import jakarta.validation.ConstraintViolation; import
 * jakarta.validation.Validation;
 * 
 * @ExtendWith(MockitoExtension.class) class UserEntityTest {
 * 
 * @InjectMocks private UserData userdata; private List<Roles> role = new
 * ArrayList<>();
 * 
 * @Mock private Roles roles;
 * 
 * UserData users;
 * 
 * @BeforeEach public void setup() { roles = new Roles(); roles.setId(1);
 * roles.setRoles("USER"); role.add(roles); users = new UserData(1, "John",
 * "Doe", "john.doe@example.com", "john123$", role);
 * 
 * }
 * 
 * @Test void testAllArgConstructor() { UserData user = new UserData(2, "anil",
 * "kumar", "anil@gmail.com", "anil1234", role); assertThat(user).isNotNull(); }
 * 
 * @Test void testNoArgConstructor() { UserData user = new UserData();
 * user.setId(4); user.setFirstName("Hel"); user.setLastName("killer");
 * user.setEmail("hel"); user.setPassword(null); }
 * 
 * @Test public void testUserDataGettersAndSetters() { // Arrange UserData
 * userData = new UserData(); userData.setId(1); userData.setFirstName("John");
 * userData.setLastName("Doe"); userData.setEmail("john@example.com");
 * userData.setPassword("testPassword"); Roles role = new Roles();
 * role.setRoles("USER"); List<Roles> rolesList = new ArrayList<>();
 * rolesList.add(role); userData.setRoles(rolesList);
 * 
 * // Act & Assert assertEquals(1, userData.getId()); assertEquals("John",
 * userData.getFirstName()); assertEquals("Doe", userData.getLastName());
 * assertEquals("john@example.com", userData.getEmail());
 * assertEquals("testPassword", userData.getPassword()); assertEquals(1,
 * userData.getRoles().size()); assertEquals("USER",
 * userData.getRoles().get(0).getRoles());
 * 
 * // Test setters UserData userdata = new UserData(); userdata.setId(3);
 * userdata.setFirstName("Jane"); userdata.setLastName("Smith");
 * userdata.setEmail("jane.smith@example.com");
 * userdata.setPassword("newpassword"); role = new Roles();
 * role.setRoles("USER"); rolesList = new ArrayList<>(); rolesList.add(role);
 * userData.setRoles(rolesList);
 * 
 * assertEquals(3, userdata.getId()); assertEquals("Jane",
 * userdata.getFirstName()); assertEquals("Smith", userdata.getLastName());
 * assertEquals("jane.smith@example.com", userdata.getEmail());
 * assertEquals("newpassword", userdata.getPassword());
 * assertNotNull(userdata.getRoles()); }
 * 
 * @Test public void testUserDataValidation() { // Arrange UserData userData =
 * new UserData();
 * 
 * // Act & Assert Set<ConstraintViolation<UserData>> violations =
 * Validation.buildDefaultValidatorFactory().getValidator().validate(userData);
 * assertThat(violations.size());
 * 
 * }
 * 
 * 
 * 
 * 
 * @Test void testAddRole() { userdata.setRoles(role);
 * assertNotNull(userdata.getRoles()); assertEquals(1,
 * userdata.getRoles().size()); assertEquals("USER",
 * userdata.getRoles().get(0).getRoles()); }
 * 
 * @Test void testToString() { String user = users.toString(); String returned =
 * "" + users; assertEquals(user, returned); }
 * 
 * }
 */