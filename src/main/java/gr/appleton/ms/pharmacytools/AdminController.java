package gr.appleton.ms.pharmacytools;

import gr.appleton.ms.pharmacytools.common.constants.CacheKeys;
import gr.appleton.ms.pharmacytools.common.constants.Endpoints;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import gr.appleton.ms.pharmacytools.common.enumerations.ClientResponses;
import gr.appleton.ms.pharmacytools.common.utils.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Admin Controller class.
 */
@CrossOrigin
@RestController
@Slf4j
public class AdminController {

    private final CacheService cache;

    /**
     * Instantiates a new Admin Controller.
     *
     * @param cache the cache service
     */
    @Autowired
    public AdminController(final CacheService cache) {
        this.cache = cache;
    }

    /**
     * Clear cache by cache name.
     *
     * @param name the cache name to be cleared
     * @return the result
     */
    @PostMapping(Endpoints.CLEAR_CACHE + "{name}")
    public ResponseEntity<CommonModel> clearCache(@PathVariable("name") final String name) {
        try {
            switch (name) {
                case CacheKeys.ALL:
                    cache.clearAll();
                    break;
                case CacheKeys.USERS:
                    cache.clearUsersCache();
                    break;
                case CacheKeys.VERBALS:
                    cache.clearVerbalsCache();
                    break;
                default:
                    return ResponseEntity
                        .ok(new CommonModel(null, ClientResponses.NOK.getCode(), ClientResponses.NOK.getMessage()));
            }
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity
                .ok(new CommonModel(null, ClientResponses.NOK.getCode(), ClientResponses.NOK.getMessage()));
        }

        return ResponseEntity
            .ok(new CommonModel(null, ClientResponses.OK.getCode(), ClientResponses.OK.getMessage()));
    }

}
