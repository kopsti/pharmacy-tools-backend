package gr.appleton.ms.pharmacytools.common.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;
import javax.servlet.http.HttpServletRequest;

/**
 * The Class used for request automatic logging.
 */
@ControllerAdvice
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

    private final LoggingService logging;
    private final HttpServletRequest httpServletRequest;

    /**
     * Instantiates a new Custom Request Body Advice Adapter.
     *
     * @param logging            the logging service
     * @param httpServletRequest the http servlet request
     */
    @Autowired
    public CustomRequestBodyAdviceAdapter(final LoggingService logging, final HttpServletRequest httpServletRequest) {
        this.logging = logging;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public boolean supports(final MethodParameter methodParameter, final Type type,
                            final Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object afterBodyRead(final Object body, final HttpInputMessage inputMessage,
                                final MethodParameter parameter, final Type targetType,
                                final Class<? extends HttpMessageConverter<?>> converterType) {

        logging.logRequest(httpServletRequest, body);

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

}
