package br.com.udemy.tasks.model;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

public class ErrorResponse {

    public int getStatus() {
        return status;
    }

    public String getMessage(){
        return message;
    }


    public ErrorResponse(){

    }

    public ErrorResponse(Builder builder){
        this.message = builder.message;
        this.status = builder.status;
    }


    private int status;

    private String message;

    public static Builder builder(){
        return new Builder();
    }

    public static Builder builderFrom(ErrorResponse errorResponse){
        return new Builder(errorResponse);
    }

    public static ErrorResponse internalError(RuntimeException e) {
        return ErrorResponse.builder().withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withMessage(e.getMessage())
                .build();
    }
    public static ErrorResponse invalidArgumentsError(FieldError e) {
        return ErrorResponse.builder().withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withMessage(e.getDefaultMessage())
                .build();
    }



    public static class Builder{
        private int status;
        private String message;

        public Builder(){

        }
        public Builder(ErrorResponse errorResponse){
            this.message = errorResponse.message;
            this.status = errorResponse.status;
        }

        public Builder withStatus(int status){
            this.status = status;
            return this;
        }

        public Builder withMessage(String message){
            this.message = message;
            return this;
        }

        public ErrorResponse build(){
            return new ErrorResponse(this);
        }


    }



}
