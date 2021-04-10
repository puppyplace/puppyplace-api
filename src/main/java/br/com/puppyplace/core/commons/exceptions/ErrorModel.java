package br.com.puppyplace.core.commons.exceptions;

import java.io.Serializable;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder({ "error", "status_code", "message", "messages" })
@JsonInclude(Include.NON_NULL)
public class ErrorModel implements Serializable {

	private static final long serialVersionUID = -9054193687126443791L;

	@JsonProperty("status_code")
	private String statusCode;

	private String message;

	private Collection<String> messages;

}
