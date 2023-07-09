/*
 * EmptyDeadlineNotAllow
 *
 * Project: KStA ZHQUEST
 *
 * Copyright 2014 by ELCA Informatik AG
 * Steinstrasse 21, CH-8036 Zurich
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of ELCA Informatik AG ("Confidential Information"). You
 * shall not disclose such "Confidential Information" and shall
 * use it only in accordance with the terms of the license
 * agreement you entered into with ELCA.
 */

package vn.elca.training.model.exception;

import java.time.LocalDate;

/**
 * @author vlp
 */
public class DeadlineAfterFinishingDateException extends Exception {
    private final LocalDate projectFinishingDate;
    private final LocalDate taskDeadline;

    public DeadlineAfterFinishingDateException(LocalDate projectFinishingDate, LocalDate taskDeadline) {
        super(String.format("Deadline %s is after finishing date %s.", taskDeadline, projectFinishingDate));
        this.projectFinishingDate = projectFinishingDate;
        this.taskDeadline = taskDeadline;
    }

    public LocalDate getProjectFinishingDate() {
        return projectFinishingDate;
    }

    public LocalDate getTaskDeadline() {
        return taskDeadline;
    }
}
