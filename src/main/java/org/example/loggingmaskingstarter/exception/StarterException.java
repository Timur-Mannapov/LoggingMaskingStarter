package org.example.loggingmaskingstarter.exception;

/**
 * Пользовательское исключение, выбрасываемое при ошибках в стартере.
 */
public class StarterException extends RuntimeException{
    /**
     * Создает экземпляр {@link StarterException} с заданным сообщением.
     *
     * @param message Сообщение об ошибке.
     */
    public StarterException(String message) {
        super(message);
    }
}
