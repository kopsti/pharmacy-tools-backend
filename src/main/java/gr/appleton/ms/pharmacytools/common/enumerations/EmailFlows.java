package gr.appleton.ms.pharmacytools.common.enumerations;

import gr.appleton.ms.pharmacytools.common.constants.EmailFlowsIds;
import gr.appleton.ms.pharmacytools.common.constants.VerbalKeys;

/**
 * The enumeration with flows that require sending email.
 */
public enum EmailFlows {

    /**
     * Pass reset link email flows.
     */
    PASS_RESET_LINK(
        EmailFlowsIds.PASS_RESET_LINK,
        VerbalKeys.PASS_RESET_LINK_SUBJ,
        VerbalKeys.PASS_RESET_LINK_INTRO,
        VerbalKeys.PASS_RESET_LINK_BODY,
        VerbalKeys.PASS_RESET_LINK_END),

    /**
     * Account act link email flows.
     */
    ACCOUNT_ACT_LINK(
        EmailFlowsIds.ACCOUNT_ACT_LINK,
        VerbalKeys.ACCOUNT_ACT_LINK_SUBJ,
        VerbalKeys.ACCOUNT_ACT_LINK_INTRO,
        VerbalKeys.ACCOUNT_ACT_LINK_BODY,
        VerbalKeys.ACCOUNT_ACT_LINK_END),

    /**
     * Account act registrar email flows.
     */
    ACCOUNT_ACT_REGISTRAR(
        EmailFlowsIds.ACCOUNT_ACT_REGISTRAR,
        VerbalKeys.ACCOUNT_ACT_REGISTRAR_SUBJ,
        VerbalKeys.ACCOUNT_ACT_REGISTRAR_INTRO,
        VerbalKeys.ACCOUNT_ACT_REGISTRAR_BODY,
        VerbalKeys.ACCOUNT_ACT_REGISTRAR_END);

    private final String id;
    private final String subjectKey;
    private final String introKey;
    private final String bodyKey;
    private final String endingKey;

    EmailFlows(final String id, final String subjectKey, final String introKey, final String bodyKey,
               final String endingKey) {
        this.id = id;
        this.subjectKey = subjectKey;
        this.introKey = introKey;
        this.bodyKey = bodyKey;
        this.endingKey = endingKey;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets subject key.
     *
     * @return the subject key
     */
    public String getSubjectKey() {
        return subjectKey;
    }

    /**
     * Gets intro key.
     *
     * @return the intro key
     */
    public String getIntroKey() {
        return introKey;
    }

    /**
     * Gets body key.
     *
     * @return the body key
     */
    public String getBodyKey() {
        return bodyKey;
    }

    /**
     * Gets ending key.
     *
     * @return the ending key
     */
    public String getEndingKey() {
        return endingKey;
    }

    @Override
    public String toString() {
        return "EmailFlows{" +
            "id='" + id + '\'' +
            ", subjectKey='" + subjectKey + '\'' +
            ", introKey='" + introKey + '\'' +
            ", bodyKey='" + bodyKey + '\'' +
            ", endingKey='" + endingKey + '\'' +
            '}';
    }

}
