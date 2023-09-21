package com.mjc.school.view.console.command.news;

import com.mjc.school.controller.exception.UnifiedControllerException;
import com.mjc.school.view.console.command.Command;
import com.mjc.school.view.console.command.CommandDict;
import com.mjc.school.view.console.command.CommandDispatcher;
import com.mjc.school.view.console.errors.ErrorsDict;
import com.mjc.school.view.exceptin.ApplicationException;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.mjc.school.view.console.utils.InputUtil.inputLong;

@Component("NEWS_REMOVE_BY_ID")
public class RemoveByIdCommand implements Command {

    private final CommandDispatcher commandDispatcher;
    private final Scanner reader;

    public RemoveByIdCommand(CommandDispatcher commandDispatcher, Scanner reader) {
        this.commandDispatcher = commandDispatcher;

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
            commandDispatcher.execute(hoAmI().name(), userChoice);
        } catch (UnifiedControllerException e) {
            e.printInfo();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public CommandDict hoAmI() {
        return CommandDict.NEWS_REMOVE_BY_ID;
    }
}
