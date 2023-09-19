package com.mjc.school.view.console.command.news;

import com.mjc.school.view.console.command.Command;
import com.mjc.school.view.console.command.CommandDict;
import com.mjc.school.view.console.command.CommandDispatcher;
import com.mjc.school.view.exceptin.ApplicationException;
import org.springframework.stereotype.Component;

@Component("NEWS_GET_ALL")
public class GetAllCommand implements Command {
    private final CommandDispatcher commandDispatcher;

    public GetAllCommand(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @Override
    public void doIt() {
        try {
            commandDispatcher.execute(hoAmI().name());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public CommandDict hoAmI() {
        return CommandDict.NEWS_GET_ALL;
    }
}