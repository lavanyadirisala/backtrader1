package com.spring.backtracking1.service;

import java.util.Date;
import java.util.UUID;
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
import com.spring.backtracking1.entity.ForgotPasswordToken;
import com.spring.backtracking1.entity.Password;

@Service
public class UserService implements UserDetailsService {
	@Value("${app.pwdresetExpire}")
	private long expTime;

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	@Autowired
	private UserRepository repo;

	@Autowired
	private ForgotPasswordTokenRepository forgotpwdrepo;

	public UserData addUser(UserData userInfo) {
		UserData user = repo.findByEmail(userInfo.getEmail());
		if (user == null) {
			System.out.print(userInfo.getPassword());
			userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
			repo.save(userInfo);
			System.out.println("USER_CREATED");
		} else {
			System.out.println("USER_ALREADY_EXISTS");
		}
		return userInfo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("load user method called");
		UserData user = repo.findByEmail(username);
		if (user != null) {
			return new User(user.getEmail(), user.getPassword(), user.getRoles());

		} else {
			throw new UsernameNotFoundException("Unable to fetch user details");
		}

	}

	public Boolean forgotpwd(String email) {
		UserData user = repo.findByEmail(email);
		String token = UUID.randomUUID().toString();
		if (user != null) {
			ForgotPasswordToken pwdtoken = new ForgotPasswordToken();
			pwdtoken.setCreatedTime(new Date(System.currentTimeMillis()).getTime());
			pwdtoken.setToken(token);
			pwdtoken.setUser(user);
			forgotpwdrepo.save(pwdtoken);
			System.out.println(pwdtoken);
			System.out.println("MAIL_IS_SENT!RESET_YOUR_PASSWORD");
			return true;
		} else {
			System.out.println("NO_USER_FOUND_WITH_MAILID");
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
				repo.save(user);
				System.out.println("Password Reset completed");
				return true;
			}
		}
		System.out.println("RE-Try!Password Reset Failed");
		return false;

	}

}
