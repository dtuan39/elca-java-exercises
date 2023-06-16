/*
 * TaskDeadlineConstraintValidator
 *
 * Project: Training
 *
 * Copyright 2015 by ELCA
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of ELCA. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreement you entered into with ELCA.
 */

package vn.elca.training.validator;

import org.springframework.stereotype.Component;
import vn.elca.training.model.entity.Task;
import vn.elca.training.model.exception.DeadlineAfterFinishingDateException;

/**
 * @author gtn
 */
@Component
public class TaskValidator {

    public void validate(Task entity) throws DeadlineAfterFinishingDateException {
        if (entity.getProject() != null
                && entity.getDeadline().isAfter(entity.getProject().getFinishingDate())) {
            throw new DeadlineAfterFinishingDateException(
                    entity.getProject().getFinishingDate(), entity.getDeadline());
        }
    }
}
