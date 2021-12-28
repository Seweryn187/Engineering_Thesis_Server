package controllers;

import entities.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import services.SourceService;

import java.util.List;

@RestController
public class SourceController {
    private final SourceService sourceService;

    @Autowired
    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @GetMapping("/sources")
    public List<Source> getAllSources() {
        return sourceService.getAllSources();
    }
}
