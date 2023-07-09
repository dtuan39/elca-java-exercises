package vn.elca.training.validator;

import vn.elca.training.enumaration.Status;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidStatusValidator implements ConstraintValidator<ValidStatus, Status> {
    @Override
    public boolean isValid(Status value, ConstraintValidatorContext context) {
        return value != null && (value == Status.NEW || value == Status.PLA || value == Status.INP || value == Status.FIN);
    }
}
