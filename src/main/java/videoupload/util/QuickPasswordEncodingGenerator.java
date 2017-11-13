package videoupload.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class QuickPasswordEncodingGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			String password = "abc125";
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			System.out.println(passwordEncoder.encode(password));
	}

}
