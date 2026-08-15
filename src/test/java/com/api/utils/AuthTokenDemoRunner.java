package com.api.utils;

import java.time.Duration;

import com.api.constant.Role;

public class AuthTokenDemoRunner {

	public static void main(String[] args) throws InterruptedException {

		for (int i = 0; i <= 100; i++) {
			String token = AuthTokenProvider.getToken(Role.FD);
			Thread.sleep(Duration.ofSeconds(3));
			System.out.println(token);
		}
	}
}