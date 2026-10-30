package ua.lab2.knives;

/** Signals a failure while parsing, validating or transforming Knife XML documents. */
public class KnifeException extends Exception {

    public KnifeException(String message, Throwable cause) {
        super(message, cause);
    }
}
