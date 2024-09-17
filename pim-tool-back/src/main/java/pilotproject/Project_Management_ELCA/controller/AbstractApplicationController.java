package pilotproject.Project_Management_ELCA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import pilotproject.Project_Management_ELCA.util.ApplicationMapper;

public abstract class AbstractApplicationController {
    @Autowired
    ApplicationMapper mapper;
}
