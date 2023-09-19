package com.mjc.school.view.console.command.author;

import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.exception.AuthorDtoValidationException;
import com.mjc.school.view.console.command.Command;
import com.mjc.school.view.console.command.CommandDict;
import com.mjc.school.view.console.command.CommandDispatcher;
import com.mjc.school.view.console.error.ErrorsDict;
import com.mjc.school.view.exceptin.ApplicationException;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.mjc.school.view.console.utils.InputUtil.inputString;

@Component("AUTHOR_CREATE")
public class CreateCommand implements Command {

    private final CommandDispatcher commandDispatcher;
    private final Scanner reader;

    public CreateCommand(CommandDispatcher commandDispatcher, Scanner reader) {
        this.commandDispatcher = commandDispatcher;
        this.reader = reader;
    }


    private static final String STEP_1 = """
            Operation: Create a new author.
            Enter author's name:""";

    @Override
    public void doIt() {

        AuthorDto authorDto = new AuthorDto();
        try {
            authorDto.setName(inputString(STEP_1, reader));

            commandDispatcher.execute(hoAmI().name(), authorDto);

        } catch (AuthorDtoValidationException e) {
            ErrorsDict.AUTHOR_DTO_VALIDATION.printLn(e.getMessage(), e.getField(), e.getValue());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public CommandDict hoAmI() {
        return CommandDict.AUTHOR_CREATE;
    }
}