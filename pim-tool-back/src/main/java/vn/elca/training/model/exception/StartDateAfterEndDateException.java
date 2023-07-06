package vn.elca.training.model.exception;

import java.time.LocalDate;

public class StartDateAfterEndDateException extends Exception{
    private final LocalDate projectEndDate;
    private final LocalDate projectStartDate;

    public StartDateAfterEndDateException(LocalDate projectEndDate, LocalDate projectStartDate) {
        super(String.format("Start date %s is after end date %s.", projectStartDate, projectEndDate));
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
    }

    public LocalDate getProjectEndDate() {
        return projectEndDate;
    }

    public LocalDate getProjectStartDate() {
        return projectStartDate;
    }

}
