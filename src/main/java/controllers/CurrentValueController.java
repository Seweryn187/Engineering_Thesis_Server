package controllers;

import entities.CurrentValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import services.CurrentValueService;

import java.util.List;

@RestController
public class CurrentValueController {

    private final CurrentValueService currentValueService;

    @Autowired
    public CurrentValueController(CurrentValueService currentValueService) {
        this.currentValueService = currentValueService;
    }

    @GetMapping("/current-values")
    public List<CurrentValue> getAllCurrentValues() {
        return this.currentValueService.getAllCurrentValues();
    }
}
