package vn.elca.training.service.impl.dummy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author gtn
 */
public abstract class AbstractDummyProjectService {
    private final Log logger = LogFactory.getLog(getClass());

    @Value("${spring.profiles.active}")
    private String activeProfiles;

    protected void printCurrentActiveProfiles() {
        logger.debug("Currently active profile - " + activeProfiles);
    }
}
