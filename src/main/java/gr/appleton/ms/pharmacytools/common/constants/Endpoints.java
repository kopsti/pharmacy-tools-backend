package gr.appleton.ms.pharmacytools.common.constants;

/**
 * The class with all application endpoints.
 */
public final class Endpoints {

    //api endpoints, for users & power_users
    public static final String CUSTOMERS = "api/customers";
    public static final String NOTES = "api/notes";
    public static final String SUPPLIERS = "api/suppliers";
    public static final String ORDERS = "api/orders";
    public static final String BANK_ACCOUNTS = "api/bank-accounts";

    //management endpoints for sys_admins
    public static final String CLEAR_CACHE = "management/clear-cache/";
    public static final String VERBALS = "management/verbals";
    public static final String BANKS = "management/banks";

    //public endpoints, not even need for JWT
    public static final String PASS_RESET_INIT = "/public/access/password/reset/init";
    public static final String PASS_RESET_GRANT = "/public/access/password/reset/grant";
    public static final String PASS_SAVE = "/public/access/password/save";
    public static final String ACCESS_LOGIN = "/public/access/login";
    public static final String ACTIVATE_ACCOUNT = "/public/account/activate";

    //need for JWT, no matter the role of the user
    public static final String ACCESS_LOGOUT = "/access/logout";
    public static final String PASS_CHANGE = "/access/password/change";
    public static final String USER_INFO = "/access/user-info";

    //only for admins & power_users
    public static final String ACCESS_REGISTER = "/access/register";

    private Endpoints() {
    }

}
