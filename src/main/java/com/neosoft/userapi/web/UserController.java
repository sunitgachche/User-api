package com.neosoft.userapi.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neosoft.userapi.jwtutil.JwtUtil;
import com.neosoft.userapi.models.AuthenticationRequest;
import com.neosoft.userapi.models.AuthenticationResponse;
import com.neosoft.userapi.models.UserDetail;
import com.neosoft.userapi.request.UserRequest;
import com.neosoft.userapi.response.MessageResponse;
import com.neosoft.userapi.service.MyUserDetailsService;
import com.neosoft.userapi.service.UserService;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	UserService userService;

	/**
	 * This method return all the users available in db
	 * 
	 * @return
	 */
	@GetMapping("/all")
	public ResponseEntity<List<UserDetail>> getAllusers() {
		List<UserDetail> footballers = userService.getAllUser();
		return new ResponseEntity<>(footballers, HttpStatus.OK);
	}

	/**
	 * This method is return only one user from db and  its used for  to find by ID 
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/find/{id}")
	public ResponseEntity<UserDetail> getUserById(@PathVariable("id") Integer id) {
		UserDetail user = userService.getASingleUser(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	/**
	 * These method is return the string "Hello World" from db
	 * @return
	 */
	@GetMapping(path = "/hello")
	public String hello() {
		return "Hello World";
	}
	
	/**
	 *  These method is used to add th user in db
	 * @param userJson
	 * @param file
	 * @return
	 */
	@PostMapping(value = { "/add" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<MessageResponse> addUser(@RequestPart("userRequest") String userJson,
			@RequestPart("file") MultipartFile file) {

		UserRequest userRequest = mapJsonToUser(userJson);
		MessageResponse newUser = userService.createUser(userRequest, file);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}
	
	/**
	 * These method is used for the get the JWT Token
	 * @param authenticationRequest
	 * @return
	 * @throws Exception
	 */

	@PostMapping(value = { "/authenticate" })
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

	private UserRequest mapJsonToUser(String userJson) {
		// TODO Auto-generated method stub
		UserRequest userRequest = new UserRequest();
		ObjectMapper mapper = new ObjectMapper();
		try {
			userRequest = mapper.readValue(userJson, UserRequest.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userRequest;
	} 
	
	/**
	 * These method is used for update a user by ID
	 * @param id
	 * @param user
	 * @return
	 */
	@PutMapping("/update/{id}")
	public Optional<UserDetail> updateUser ( @PathVariable Integer id, @ModelAttribute UserRequest user) {
		return userService.updateUser(id, user);

	}

	/**
	 * These method is used for delete user from db by ID
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
