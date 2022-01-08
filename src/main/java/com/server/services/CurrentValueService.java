package com.server.services;

import com.server.entities.CurrentValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.server.repositories.CurrentValueRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrentValueService {

    private final CurrentValueRepository currentValueRepository;

    @Autowired
    public CurrentValueService(CurrentValueRepository currentValueRepository) {
        this.currentValueRepository = currentValueRepository;
    }

    public List<CurrentValue> getAllCurrentValues() {
        List<CurrentValue> currentValuesList = new ArrayList<>();
        currentValueRepository.findAll().forEach(currentValuesList::add);
        return currentValuesList;
    }

    public List<CurrentValue> getCurrentValuesBySourceName(String name) {
        List<CurrentValue> currentValuesList = new ArrayList<>();
        currentValueRepository.findCurrentValueBySourceName(name).forEach(currentValuesList::add);
        return currentValuesList;
    }
}
