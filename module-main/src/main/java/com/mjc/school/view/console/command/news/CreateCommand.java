package com.mjc.school.view.console.command.news;

import com.mjc.school.repository.exception.AuthorNotFoundException;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.exception.NewsDtoValidationException;
import com.mjc.school.view.console.command.Command;
import com.mjc.school.view.console.command.CommandDict;
import com.mjc.school.view.console.command.CommandDispatcher;
import com.mjc.school.view.console.error.ErrorsDict;
import com.mjc.school.view.exceptin.ApplicationException;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.mjc.school.view.console.utils.InputUtil.inputLong;
import static com.mjc.school.view.console.utils.InputUtil.inputString;

@Component("NEWS_CREATE")
public class CreateCommand implements Command {

    private final CommandDispatcher commandDispatcher;
    private final Scanner reader;

    public CreateCommand(CommandDispatcher commandDispatcher, Scanner reader) {
        this.commandDispatcher = commandDispatcher;
        this.reader = reader;
    }


    private static final String STEP_1 = """
            Operation: Create news.
            Enter news title:""";

    private static final String STEP_2 = "Enter news content:";

    private static final String STEP_3 = "Enter author id:";

    @Override
    public void doIt() {

        NewsDto newsDto = new NewsDto();
        try {
            newsDto
                    .setTitle(inputString(STEP_1, reader))
                    .setContent(inputString(STEP_2, reader))
                    .setAuthorId(inputLong(STEP_3, reader, ErrorsDict.AUTHOR_ID_SHOULD_BE_NUMBER));

            commandDispatcher.execute(hoAmI().name(), newsDto);

        } catch (AuthorNotFoundException e) {
            ErrorsDict.AUTHOR_ID_DOES_NOT_EXIST.printLn(newsDto.getAuthorId());
        } catch (NewsDtoValidationException e) {
            ErrorsDict.NEWS_DTO_VALIDATION.printLn(e.getMessage(), e.getField(), e.getValue());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public CommandDict hoAmI() {
        return CommandDict.NEWS_CREATE;
    }
}
