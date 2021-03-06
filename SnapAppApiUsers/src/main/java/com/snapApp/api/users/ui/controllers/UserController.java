package com.snapApp.api.users.ui.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snapApp.api.users.data.UserEntity;
import com.snapApp.api.users.service.UserService;
import com.snapApp.api.users.shared.UserDTO;
import com.snapApp.api.users.ui.model.UserInfoRequestModel;
import com.snapApp.api.users.ui.model.UserInfoResponseModel;
import com.snapApp.api.users.ui.model.UserResponseModel;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private Environment env;

	@Autowired
	public UserService userService;

	@GetMapping("/status/check")
	public String status() {

		return "Working on port " + env.getProperty("local.server.port")+ "using "+ env.getProperty("token.secret");
	}

	@PostMapping
	public ResponseEntity<UserInfoResponseModel> createUser(@RequestBody UserInfoRequestModel userInfoRequestModel) {

		UserDTO userDto = new ModelMapper().map(userInfoRequestModel, UserDTO.class);
		UserDTO userResponseDto = userService.createUser(userDto);
		UserInfoResponseModel userResponseModel = new UserInfoResponseModel();
		userResponseModel = new ModelMapper().map(userResponseDto, UserInfoResponseModel.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(userResponseModel);

	}

	@GetMapping(value="/{userId}",produces={MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userID) {

		UserDTO userDto = userService.getUserId(userID);
		UserResponseModel userResponseModel = new ModelMapper().map(userDto, UserResponseModel.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponseModel);

	}
}
