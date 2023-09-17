package com.mjc.school.view.console.operation;

import com.mjc.school.controller.NewsController;
import com.mjc.school.repository.exception.NewsNotFoundException;
import com.mjc.school.view.console.error.ErrorsDict;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.mjc.school.view.console.utils.InputUtil.inputLong;

@Component
public class RemoveByIdOperation implements Operation {

    private final NewsController controller;
    private final Scanner reader;

    public RemoveByIdOperation(NewsController controller, Scanner reader) {
        this.controller = controller;
        this.reader = reader;
    }

    private static final String STEP_1 = """
            Operation: Remove news by id.
            Enter news id:""";

    @Override
    public void doIt() {
        Long userChoice = null;
        try {
            userChoice = inputLong(STEP_1, reader, ErrorsDict.NEWS_ID_SHOULD_BE_NUMBER);

            System.out.println(controller.deleteById(userChoice));
        } catch (NewsNotFoundException e) {
            ErrorsDict.NEWS_WITH_ID_DOES_NOT_EXIST.printLn(userChoice);
        }
    }

    @Override
    public OperationName hoAmI() {
        return OperationName.REMOVE_BY_ID;
    }
}
