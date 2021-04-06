package com.bg.loanservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {
	 private String errorMessage;
     private String statusCode;
}
