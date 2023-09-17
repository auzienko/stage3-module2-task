package com.mjc.school.view.console;

import com.mjc.school.view.View;
import com.mjc.school.view.console.error.ErrorsDict;
import com.mjc.school.view.console.operation.Operation;
import com.mjc.school.view.console.operation.OperationName;
import com.mjc.school.view.console.utils.InputUtil;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component("consoleView")
public class ConsoleView implements View {

    private static final String TITLE = "Enter the number of operation:\n";
    private static final String TEMPLATE = "%d - %s\n";
    private static final String EXIT = "0 - Exit.";
    private static final String OPERATION_NOT_FOUND = "Command not found.";

    private final String menu;

    private final Scanner reader;
    private Map<Long, Operation> operationMap;

    public ConsoleView(Set<Operation> operationSet, Scanner reader) {
        this.reader = reader;
        makeOperationMap(operationSet);
        menu = makeMenu();
    }

    private void makeOperationMap(Set<Operation> operationSet) {
        operationMap = operationSet.stream()
                .collect(Collectors.toMap(e -> e.hoAmI().getId(), Function.identity()));
    }

    public void show() {
        long userChoice;
        do {
            userChoice = InputUtil.inputLong(menu, reader, ErrorsDict.OPERATION_NOT_FOUND);
            runOperation(userChoice);
        } while (userChoice != 0);
    }

    private String makeMenu() {
        StringBuilder sb = new StringBuilder(TITLE);
        for (OperationName o : OperationName.values()) {
            sb.append(TEMPLATE.formatted(o.getId(), o.getDescription()));
        }
        sb.append(EXIT);
        return sb.toString();
    }

    private void runOperation(long userChoice) {
        if (userChoice == 0) {
            return;
        }

        Operation operation = operationMap.get(userChoice);

        if (operation == null) {
            System.out.println(OPERATION_NOT_FOUND);
        } else {
            operation.doIt();
        }
    }
}
