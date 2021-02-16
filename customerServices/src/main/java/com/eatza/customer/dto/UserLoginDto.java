package com.eatza.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserLoginDto {

	
	 private String email;
     private String password;
	    
		public UserLoginDto(String email, String password) {
			super();
			this.email = email;
			this.password = password;
		}
	    
	    
}
