package gr.appleton.ms.pharmacytools.common.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Log interceptor.
 */
@Component
public class LogInterceptor implements HandlerInterceptor {

    private final LoggingService logging;

    /**
     * Instantiates a new Log Interceptor.
     *
     * @param logging the logging service
     */
    @Autowired
    public LogInterceptor(final LoggingService logging) {
        this.logging = logging;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {

        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())
            && HttpMethod.GET.name().equals(request.getMethod())) {
            logging.logRequest(request, null);
        }

        return true;
    }

}
