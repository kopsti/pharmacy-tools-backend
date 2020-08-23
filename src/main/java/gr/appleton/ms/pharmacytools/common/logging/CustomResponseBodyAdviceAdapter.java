package gr.appleton.ms.pharmacytools.common.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * The Class used for response automatic logging.
 */
@ControllerAdvice
public class CustomResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {

    private final LoggingService logging;

    /**
     * Instantiates a new Custom Response Body Advice Adapter.
     *
     * @param logging the logging service
     */
    @Autowired
    public CustomResponseBodyAdviceAdapter(final LoggingService logging) {
        this.logging = logging;
    }

    @Override
    public boolean supports(final MethodParameter methodParameter,
                            final Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(final Object o, final MethodParameter methodParameter, final MediaType mediaType,
                                  final Class<? extends HttpMessageConverter<?>> aClass,
                                  final ServerHttpRequest serverHttpRequest,
                                  final ServerHttpResponse serverHttpResponse) {

        if (serverHttpRequest instanceof ServletServerHttpRequest
            && serverHttpResponse instanceof ServletServerHttpResponse) {
            logging.logResponse(((ServletServerHttpRequest) serverHttpRequest).getServletRequest(),
                ((ServletServerHttpResponse) serverHttpResponse).getServletResponse(), o);
        }

        return o;
    }

}
