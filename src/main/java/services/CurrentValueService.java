package services;

import entities.Currency;
import entities.CurrentValue;
import entities.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.CurrentValueRepository;

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
}
