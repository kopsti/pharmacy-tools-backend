package gr.appleton.ms.pharmacytools.common.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Service used for logging purposes.
 */
@Service
@Slf4j
public class LoggingService {

    /**
     * Logs request.
     *
     * @param httpServletRequest the http servlet request
     * @param body               the body
     */
    public void logRequest(final HttpServletRequest httpServletRequest, final Object body) {
        final StringBuilder stringBuilder = new StringBuilder();
        final Map<String, String> parameters = buildParametersMap(httpServletRequest);

        stringBuilder.append("REQUEST ");
        stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
        stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
        stringBuilder.append("headers=[").append(buildHeadersMap(httpServletRequest)).append("] ");

        if (!parameters.isEmpty()) {
            stringBuilder.append("parameters=[").append(parameters).append("] ");
        }

        if (body != null) {
            stringBuilder.append("body=[").append(body).append("]");
        }

        log.info(stringBuilder.toString());
    }

    /**
     * Logs response.
     *
     * @param httpServletRequest  the http servlet request
     * @param httpServletResponse the http servlet response
     * @param body                the body
     */
    public void logResponse(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse,
                            final Object body) {

        final String stringBuilder = "RESPONSE "
            + "method=[" + httpServletRequest.getMethod() + "] "
            + "path=[" + httpServletRequest.getRequestURI() + "] "
            + "responseHeaders=[" + buildHeadersMap(httpServletResponse) + "] "
            + "responseBody=[" + body + "] ";

        log.info(stringBuilder);
    }

    private Map<String, String> buildParametersMap(final HttpServletRequest httpServletRequest) {
        final Map<String, String> resultMap = new HashMap<>();
        final Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            final String key = parameterNames.nextElement();
            final String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    private Map<String, String> buildHeadersMap(final HttpServletRequest request) {
        final Map<String, String> map = new HashMap<>();
        final Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            final String key = headerNames.nextElement();
            final String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    private Map<String, String> buildHeadersMap(final HttpServletResponse response) {
        final Map<String, String> map = new HashMap<>();
        final Collection<String> headerNames = response.getHeaderNames();

        for (final String header : headerNames) {
            map.put(header, response.getHeader(header));
        }

        return map;
    }

}
