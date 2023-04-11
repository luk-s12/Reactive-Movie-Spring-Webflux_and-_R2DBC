package com.example.demo.rotuters.exceptions.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

import com.example.demo.rotuters.exceptions.ConstraintException;
import com.example.demo.rotuters.exceptions.ResponseException;
import com.example.demo.rotuters.exceptions.RouterException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import reactor.core.publisher.Mono;

@Component
public final class ErrorUtil {

	@Autowired
	private Validator validator;

	public <T> Mono<T> constraintViolation(T object) {
		Set<ConstraintViolation<T>> violations = validator.validate(object);
		Map<String, Object> errors = new HashMap<>();
		ConstraintException response = new ConstraintException();

		response.setStatus(HttpStatus.BAD_REQUEST.value());

		if (!violations.isEmpty()) {
			violations.forEach(error -> errors.put(error.getPropertyPath().toString(), error.getMessage()));
			response.setMessages(errors);
			return Mono.error(response);
		}

		return Mono.just(object);
	}

	public static BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> globalErrorHandler() {
		return (error, request) -> {
			return responseException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ConstantUtil.SERVER_ERROR);
		};
	}

	public static BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> customExceptionHandler() {
		return (error, request) -> {
			RouterException constraintException = (RouterException) error;
			return responseException(constraintException.status(), constraintException.messages());
		};
	}

	private static Mono<ServerResponse> responseException(Integer status, Object errors) {
		ResponseException response = new ResponseException(status, List.of(errors));
		return status(response.getStatus()).bodyValue(response);
	}

}
