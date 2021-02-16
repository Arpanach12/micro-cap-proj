package com.eatza.customer.dto;

import java.util.List;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserDto {

	 private String firstName;
	    private String lastName;
	    private String email;
	    private String password;
		
	    public UserDto(String firstName, String lastName, String email, String password) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.password = password;
		}
	    
}
