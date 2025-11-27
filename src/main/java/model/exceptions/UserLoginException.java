package main.java.model.exceptions;

public class UserLoginException extends RuntimeException {
  public UserLoginException(String message) {
    super(message);
  }
}
