package com.mjc.school.view.console.command.author;

import com.mjc.school.repository.exception.AuthorNotFoundException;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.exception.AuthorDtoValidationException;
import com.mjc.school.view.console.command.Command;
import com.mjc.school.view.console.command.CommandDict;
import com.mjc.school.view.console.command.CommandDispatcher;
import com.mjc.school.view.console.error.ErrorsDict;
import com.mjc.school.view.exceptin.ApplicationException;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.mjc.school.view.console.utils.InputUtil.inputLong;
import static com.mjc.school.view.console.utils.InputUtil.inputString;

@Component("AUTHOR_UPDATE")
public class UpdateCommand implements Command {

    private final CommandDispatcher commandDispatcher;
    private final Scanner reader;

    public UpdateCommand(CommandDispatcher commandDispatcher, Scanner reader) {
        this.commandDispatcher = commandDispatcher;
        this.reader = reader;
    }

    private static final String STEP_1 = """
            Operation: Update an author.
            Enter author's id:""";
    private static final String STEP_2 = "Enter author's name:";

    @Override
    public void doIt() {
        AuthorDto authorDto = new AuthorDto();
        try {
            authorDto.setId(inputLong(STEP_1, reader, ErrorsDict.AUTHOR_ID_SHOULD_BE_NUMBER));
            authorDto.setName(inputString(STEP_2, reader));


            commandDispatcher.execute(hoAmI().name(), authorDto);

        } catch (AuthorNotFoundException e) {
            ErrorsDict.AUTHOR_ID_DOES_NOT_EXIST.printLn(authorDto.getId());
        } catch (AuthorDtoValidationException e) {
            ErrorsDict.AUTHOR_DTO_VALIDATION.printLn(e.getMessage(), e.getField(), e.getValue());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public CommandDict hoAmI() {
        return CommandDict.AUTHOR_UPDATE;
    }
}
