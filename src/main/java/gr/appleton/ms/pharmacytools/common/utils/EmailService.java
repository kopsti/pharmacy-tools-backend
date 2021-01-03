package gr.appleton.ms.pharmacytools.common.utils;

import static gr.appleton.ms.pharmacytools.common.constants.Endpoints.ACTIVATE_ACCOUNT;
import static gr.appleton.ms.pharmacytools.common.constants.Endpoints.PASS_RESET_GRANT;

import freemarker.template.Configuration;
import gr.appleton.ms.pharmacytools.PharmacyToolsApplication;
import gr.appleton.ms.pharmacytools.authorization.persistence.UserDao;
import gr.appleton.ms.pharmacytools.common.constants.EmailFlowsIds;
import gr.appleton.ms.pharmacytools.common.constants.VerbalKeys;
import gr.appleton.ms.pharmacytools.common.enumerations.EmailFlows;
import gr.appleton.ms.pharmacytools.common.enumerations.SecTokenFlows;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * The Service for sending emails.
 */
@Service
@Slf4j
public class EmailService {

    private final CommonService common;
    private final JavaMailSender mailSender;
    private final Configuration fm;
    private final CacheService cache;

    /**
     * Instantiates a new Email service.
     *
     * @param common     the common service
     * @param mailSender the mail sender
     * @param fm         the fm configuration
     * @param cache      the cache service
     */
    @Autowired
    public EmailService(final CommonService common, final JavaMailSender mailSender, final Configuration fm,
                        final CacheService cache) {
        this.common = common;
        this.mailSender = mailSender;
        this.fm = fm;
        this.cache = cache;
    }

    /**
     * Construct and send email.
     *
     * @param flow      the flow
     * @param to        the recipient
     * @param extraBody the extra body to include
     */
    @Async
    public void constructAndSendEmail(final EmailFlows flow, final String to, final String extraBody) {

        final Map<String, Object> model = new HashMap<>();

        switch (flow.getId()) {
            case EmailFlowsIds.ACCOUNT_ACT_LINK:
                addModelAttributes(EmailFlows.ACCOUNT_ACT_LINK, model, extraBody);
                break;
            case EmailFlowsIds.ACCOUNT_ACT_REGISTRAR:
                addModelAttributes(EmailFlows.ACCOUNT_ACT_REGISTRAR, model, extraBody);
                break;
            case EmailFlowsIds.PASS_RESET_LINK:
                addModelAttributes(EmailFlows.PASS_RESET_LINK, model, extraBody);
                break;
            default:
                break;
        }

        model.put("contact", common.getVerbalByKey(VerbalKeys.CONTACT));

        sendEmail(to, common.getVerbalByKey(flow.getSubjectKey()), geContentFromTemplate(model));
    }

    /**
     * Send email for sec token.
     *
     * @param username    the username
     * @param flow        the flow
     * @param contextPath the context path
     * @param uuid        the uuid
     * @throws GenericException the generic exception
     */
    @Async
    public void sendEmailForSecToken(final String username, final SecTokenFlows flow, final String contextPath,
                                     final String uuid, final String redirectionUrl) throws GenericException {
        final UserDao userDao = cache.getUserByUsername(username);

        if (flow == SecTokenFlows.RESET_PASS) {
            constructAndSendEmail(EmailFlows.PASS_RESET_LINK, userDao.getEmail(),
                contextPath + PASS_RESET_GRANT + "?t=" + uuid + "&i=" + username + "&n=" + redirectionUrl);
        } else {
            constructAndSendEmail(EmailFlows.ACCOUNT_ACT_LINK, userDao.getEmail(),
                contextPath + ACTIVATE_ACCOUNT + "?t=" + uuid + "&i=" + username + "&n=" + redirectionUrl);
        }
    }

    private void sendEmail(final String to, final String subject, final String msg) {
        try {
            final MimeMessage message = mailSender.createMimeMessage();
            message.setSubject(subject);
            final MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("support@retman.gr");
            helper.setTo(to);
            helper.setText(msg, true);
            mailSender.send(message);
        } catch (final MessagingException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void addModelAttributes(final EmailFlows flow, Map<String, Object> model, final String extraBody) {
        model.put("intro", common.getVerbalByKey(flow.getIntroKey()));
        model.put("body", common.getVerbalByKey(flow.getBodyKey()) + (extraBody != null ? extraBody : ""));
        model.put("ending", common.getVerbalByKey(flow.getEndingKey()));
    }

    private String geContentFromTemplate(final Map<String, Object> model) {
        final StringBuilder content = new StringBuilder();
        fm.setClassForTemplateLoading(PharmacyToolsApplication.class, "/email-templates");
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fm.getTemplate("basic.html"), model));
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
        return content.toString();
    }

}
