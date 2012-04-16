package com.sk.frontend.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class UniqueIdGeneratorService {

	public String newUniqueId() {
		return UUID.randomUUID().toString();
	}

}
