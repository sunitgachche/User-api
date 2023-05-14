package com.neosoft.userapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.neosoft.userapi.exception.ResourceNotFoundException;
import com.neosoft.userapi.models.UserDetail;
import com.neosoft.userapi.request.UserRequest;
import com.neosoft.userapi.response.MessageResponse;

@Component
public interface UserService {

	MessageResponse createUser(UserRequest userRequest, MultipartFile file);

	Optional<UserDetail> updateUser(UserRequest userRequest, MultipartFile file);

	void deleteUser(Integer userId);

	UserDetail getASingleUser(Integer userId);

	List<UserDetail> getAllUser();

	Optional<UserDetail> updateUser(Integer id, UserRequest user);

	//Optional<UserDetail> updateUser(Integer userId, UserRequest userRequest) throws ResourceNotFoundException;
}
