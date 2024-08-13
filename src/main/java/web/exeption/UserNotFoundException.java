package web.exeption;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String massage){
        super(massage);
    }
}
