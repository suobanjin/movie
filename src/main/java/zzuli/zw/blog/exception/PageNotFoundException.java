package zzuli.zw.blog.exception;

public class PageNotFoundException extends Exception{
    public PageNotFoundException() {
        super();
    }
    public PageNotFoundException(String message) {
        super(message);
    }
}
