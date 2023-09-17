package com.mjc.school.view.console.operation;

import com.mjc.school.controller.NewsController;
import org.springframework.stereotype.Component;

@Component
public class GetAllOperation implements Operation {

    private final NewsController controller;

    public GetAllOperation(NewsController controller) {
        this.controller = controller;
    }

    @Override
    public void doIt() {
        controller.readAll().forEach(System.out::println);
    }

    @Override
    public OperationName hoAmI() {
        return OperationName.GET_ALL;
    }
}
