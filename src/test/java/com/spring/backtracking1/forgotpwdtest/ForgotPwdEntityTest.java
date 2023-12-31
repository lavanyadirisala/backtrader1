package com.spring.backtracking1.forgotpwdtest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.spring.backtracking1.entity.ForgotPasswordToken;
import com.spring.backtracking1.entity.UserData;

public class ForgotPwdEntityTest {

    @Test
    public void testCreateForgotPasswordToken() {
        // Given
        long createdTime = System.currentTimeMillis();
        String token = "sample_token";
        UserData user = new UserData();
        user.setId(1); // Set the appropriate user ID

        // When
        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
        forgotPasswordToken.setCreatedTime(createdTime);
        forgotPasswordToken.setToken(token);
        forgotPasswordToken.setUser(user);

        // Then
        Assertions.assertEquals(createdTime, forgotPasswordToken.getCreatedTime());
        Assertions.assertEquals(token, forgotPasswordToken.getToken());
        Assertions.assertEquals(user, forgotPasswordToken.getUser());
    }
    @Test
    public void testEmptyConstructor() {
        // Given, When
        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();

        // Then
        Assertions.assertEquals(0, forgotPasswordToken.getToken_id());
        Assertions.assertEquals(0, forgotPasswordToken.getCreatedTime());
        Assertions.assertNull(forgotPasswordToken.getToken());
        Assertions.assertNull(forgotPasswordToken.getUser());
    }

    @Test
    public void testNullToken() {
        // Given
        long createdTime = System.currentTimeMillis();
        UserData user = new UserData();
        user.setId(1); // Set the appropriate user ID

        // When
        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
        forgotPasswordToken.setCreatedTime(createdTime);
        forgotPasswordToken.setUser(user);

        // Then
        Assertions.assertEquals(createdTime, forgotPasswordToken.getCreatedTime());
        Assertions.assertNull(forgotPasswordToken.getToken());
        Assertions.assertEquals(user, forgotPasswordToken.getUser());
    }

    @Test
    public void testTokenIdGeneration() {
        // Given
        long createdTime = System.currentTimeMillis();
        String token = "sample_token";
        UserData user = new UserData();
        user.setId(1); // Set the appropriate user ID

        // When
        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
        forgotPasswordToken.setCreatedTime(createdTime);
        forgotPasswordToken.setToken(token);
        forgotPasswordToken.setUser(user);

        // Then
        Assertions.assertEquals(0, forgotPasswordToken.getToken_id()); // Token_id should be generated by the database
    }





    @Test
    public void testForgotPasswordTokenEquality() {
        // Given
        long createdTime = System.currentTimeMillis();
        String token = "sample_token";
        UserData user1 = new UserData();
        user1.setId(1); 
        UserData user2 = new UserData();
        user2.setId(2); 

        // When
        ForgotPasswordToken token1 = new ForgotPasswordToken(1,createdTime, token, user1);
        ForgotPasswordToken token2 = new ForgotPasswordToken(1,createdTime, token, user1);
        ForgotPasswordToken token3 = new ForgotPasswordToken(2,createdTime, token, user2);

        // Then
        Assertions.assertEquals(token1, token2);
        Assertions.assertNotEquals(token1, token3); 
    }

    

}

