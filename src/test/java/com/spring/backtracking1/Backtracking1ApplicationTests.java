package com.spring.backtracking1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import com.spring.backtracking1.entity.Roles;
import com.spring.backtracking1.entity.UserData;
import com.spring.backtracking1.repository.UserRepository;

@SpringBootTest
class Backtracking1ApplicationTests {

	@Autowired
	UserRepository userepo;
	
	@Test
	 public void createUserTest(){
    	UserData users=new UserData();
        users.setId(1);
        users.setEmail("kamalteja1510@gmail.com");
        users.setFirstName("Kamal");
        users.setLastName("teja");
        users.setPassword("Kamal025.");
        
        Roles lrole = new Roles();
        lrole.setRoles("Support");
        lrole.setId(1);
        lrole.setRoles("Admin");
        lrole.setId(2);
        	
        List<Roles>list=new ArrayList<>();
        list.add(lrole);
        users.setRoles(list);
        
        UserData user=userepo.save(users);
        assertThat(user).isNotNull();
        //user.getEmail().equals("kamaltej1510@gmail.com");
        assertEquals("kamaltej1510@gmail.com", user.getEmail());
       

	}
}
