package tinify;

import tinify.exception.AccountException;
import tinify.exception.ClientException;
import tinify.exception.ServerException;

public class Exception extends RuntimeException {
    static class Data {
        private String message;
        private String error;

        void setMessage(final String message) {
            this.message = message;
        }

        final String getMessage() {
            return message;
        }

        void setError(final String error) {
            this.error = error;
        }

        final String getError() {
            return error;
        }
    }

    static Exception create(final String message, final String type, final int status) {
        if (status == 401 || status == 429) {
            return new AccountException(message, type, status);
        } else if (status >= 400 && status <= 499) {
            return new ClientException(message, type, status);
        } else if (status >= 500 && status <= 599) {
            return new ServerException(message, type, status);
        } else {
            return new Exception(message, type, status);
        }
    }

    protected int status = 0;

    public Exception() {
        super();
    }

    public Exception(final Throwable t) {
        super(t);
    }

    public Exception(final String message) {
        super(message);
    }

    public Exception(final String message, final Throwable t) {
        super(message, t);
    }

    public Exception(final String message, final String type, final int status) {
        super(message + " (HTTP " + status + "/" + type + ")");
        this.status = status;
    }
}
