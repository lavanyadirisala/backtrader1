package com.spring.backtracking1.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.spring.backtracking1.entity.UserData;
import com.spring.backtracking1.repository.ForgotPasswordTokenRepository;
import com.spring.backtracking1.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.spring.backtracking1.auth.JwtUtil;
import com.spring.backtracking1.entity.ForgotPasswordToken;
import com.spring.backtracking1.entity.Password;
import com.spring.backtracking1.entity.Roles;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService implements UserDetailsService {
	@Value("${app.pwdresetExpire}")
	private long expTime;
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private UserRepository userRepo;
	
	@Autowired
	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	@Autowired
	private ForgotPasswordTokenRepository forgotpwdrepo;

	@Autowired
	private JwtUtil jwtutil;

	 Logger logger = LoggerFactory.getLogger(UserService.class);
	public String createAdmin(UserData admindata, String token) {
		/*
		 * Collection<? extends GrantedAuthority> authorities =
		 * authentication.getAuthorities();
		 * 
		 * List<String> roles = new ArrayList<>(); for (GrantedAuthority authority :
		 * authorities) { roles.add(authority.getAuthority()); }
		 */		
		
		String gmail = jwtutil.getUserNameFromToken(token);
		UserDetails user = loadUserByUsername(gmail);
		//if (roles.contains("ADMIN")) {
		if(user.getAuthorities().contains("ADMIN")) {
			admindata.setPassword(passwordEncoder.encode(admindata.getPassword()));
			Roles role = new Roles();
			role.setRoles("ADMIN");
			System.out.print(role.getRoles());
			List<Roles> roleslist = new ArrayList<>();
			roleslist.add(role);
			admindata.setRoles(roleslist);
			userRepo.save(admindata);

			logger.info("ADMIN_CREATED");
			return "ADMIN_CREATED";
		}
		logger.info("ROOT_USER_NOT_FOUND");
		return "ROOT_USER_NOT_FOUND";
	}

	public Boolean addUser(UserData userInfo) {
		UserData user = userRepo.findByEmail(userInfo.getEmail());
		if (user == null) {
			System.out.print(userInfo.getPassword());
			userInfo.setFirstname(userInfo.getFirstname());
			userInfo.setLastname(userInfo.getLastname());
			userInfo.setEmail(userInfo.getEmail());
			userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
			userInfo.setPhone(userInfo.getPhone());
			Roles role = new Roles();
			role.setRoles("USER");
			List<Roles> list = new ArrayList<>();
			list.add(role);
			userInfo.setRoles(list);
			userRepo.save(userInfo);
			logger.info("USER_CREATED with "+ userInfo.getEmail());
			return true;
		}
		return false;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("load user method called");
		UserData user = userRepo.findByEmail(username);
		if (user != null) {
			return new User(user.getEmail(), user.getPassword(), user.getRoles());
		} 
		else {
			throw new UsernameNotFoundException("Unable to fetch user details");
		}

	}

	public Boolean forgotpwd(String email) {
		//String email = users.getEmail();
		UserData user = userRepo.findByEmail(email);
		String token = UUID.randomUUID().toString();
		if (user != null) {
			ForgotPasswordToken pwdtoken = new ForgotPasswordToken();
			pwdtoken.setCreatedTime(new Date(System.currentTimeMillis()).getTime());
			pwdtoken.setToken(token);
			pwdtoken.setUser(user);
			forgotpwdrepo.save(pwdtoken);
			System.out.println(pwdtoken);
			//logger.info("MAIL_IS_SENT!RESET_YOUR_PASSWORD");
			return true;
		} else {
			logger.warn("NO_USER_FOUND_WITH_MAILID");
		}
		return false;
	}

	public boolean resetToNewPwd(Password pwdDto) {
		String token = pwdDto.getToken();
		String newPwd = pwdDto.getNewPassword();
		String confirmPwd = pwdDto.getConfirmPassword();
		ForgotPasswordToken tokendata = forgotpwdrepo.findByToken(token);
		String savedtoken = tokendata.getToken();
		UserData user = tokendata.getUser();
		long changedPwdTime = tokendata.getCreatedTime();
		if (token.equals(savedtoken)) {
			long currentTime = new Date(System.currentTimeMillis()).getTime();
			if (currentTime - changedPwdTime <= expTime && newPwd.equals(confirmPwd)) {
				user.setPassword(passwordEncoder.encode(pwdDto.getConfirmPassword()));
				userRepo.save(user);
				logger.info(savedtoken);
				System.out.println("Password Reset completed");
				forgotpwdrepo.deleteById(tokendata.getToken_id());
				logger.info("PASSWORD_RESET_COMPLETED");
				System.out.println("Deleted data from ForgotPWDTable as he can forgot password again");
				return true;
			}
		}
		logger.warn("RETRY!FAILED RESETING PASSWORD_FOR_USER "+ user.getId() + user.getFirstname());
		System.out.println("RE-Try!Password Reset Failed");
		return false;

	}
	public UserData findByEmail(String email) {
		return userRepo.findByEmail(email);
	}


}
