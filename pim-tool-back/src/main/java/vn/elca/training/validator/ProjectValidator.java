package vn.elca.training.validator;

import org.springframework.stereotype.Component;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.exception.StartDateAfterEndDateException;

@Component
public class ProjectValidator {

    // TODO : Can't always believe in the input data from client, so we need to validate it again
    //1. Presence Check: Ensures that a required field is not empty or null.
    //2. Format Check: Verifies that data follows a specified format (e.g., date format, email format, phone number format).
    //3. Range Check: Validates that a value falls within a specified range or set of allowable values.
    //4. Length Check: Verifies that the length of a string or the number of characters in a field is within the specified limits.
    //5. Type Check: Ensures that the data type of a value matches the expected data type (e.g., string, number, boolean).
    //6. Consistency Check: Validates the consistency and relationships between multiple data elements (e.g., matching IDs, related data in different tables).
    //7. Cross-Field Validation: Checks the relationship and consistency between multiple fields or attributes (e.g., start date and end date).
    //8. Unique Check: Verifies that a value or combination of values is unique in the dataset or database (e.g., unique username, unique product code).
    //9. Business Rule Check: Validates data against specific business rules or requirements defined by the application or domain.
    //10. Referential Integrity Check: Ensures that foreign key relationships between tables or entities are valid and refer to existing records.
    //11. Database Check: Verifies that data meets the requirements of the database (e.g., data type, length, format).

    public void validate(ProjectDto projectDto) throws StartDateAfterEndDateException {
        if (projectDto.getEndDate() != null && projectDto.getStartDate().isAfter(projectDto.getEndDate())) {
            throw new StartDateAfterEndDateException(projectDto.getEndDate(),
                    projectDto.getStartDate());
        }
    }
}
