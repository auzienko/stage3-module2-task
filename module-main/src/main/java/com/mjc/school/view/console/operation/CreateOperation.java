package com.mjc.school.view.console.operation;

import com.mjc.school.controller.NewsController;
import com.mjc.school.repository.exception.AuthorNotFoundException;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.NewsDtoValidationException;
import com.mjc.school.view.console.error.ErrorsDict;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.mjc.school.view.console.utils.InputUtil.inputLong;
import static com.mjc.school.view.console.utils.InputUtil.inputString;

@Component
public class CreateOperation implements Operation {

    private final NewsController controller;
    private final Scanner reader;

    public CreateOperation(NewsController controller, Scanner reader) {
        this.controller = controller;
        this.reader = reader;
    }


    private static final String STEP_1 = """
            Operation: Create news.
            Enter news title:""";

    private static final String STEP_2 = "Enter news content:";

    private static final String STEP_3 = "Enter author id:";

    @Override
    public void doIt() {

        NewsDtoResponse newsDto = new NewsDtoResponse();
        try {
            newsDto
                    .setTitle(inputString(STEP_1, reader))
                    .setContent(inputString(STEP_2, reader))
                    .setAuthorId(inputLong(STEP_3, reader, ErrorsDict.AUTHOR_ID_SHOULD_BE_NUMBER));

            System.out.println(controller.create(newsDto));

        } catch (AuthorNotFoundException e) {
            ErrorsDict.AUTHOR_ID_DOES_NOT_EXIST.printLn(newsDto.getAuthorId());
        } catch (NewsDtoValidationException e) {
            ErrorsDict.NEWS_DTO_VALIDATION.printLn(e.getMessage(), e.getField(), e.getValue());
        }
    }

    @Override
    public OperationName hoAmI() {
        return OperationName.CREATE;
    }
}
