package com.server.data;

import com.server.entities.CurrentValue;
import com.server.entities.HistoricalValue;
import com.server.repositories.HistoricalValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArchiveData {

    private final HistoricalValueRepository historicalValueRepository;
    private static final Logger log = LoggerFactory.getLogger(ArchiveData.class);

    @Autowired
    public ArchiveData(HistoricalValueRepository historicalValueRepository){
        this.historicalValueRepository = historicalValueRepository;
    }

    @Transactional
    public void archiveCurrentValue(CurrentValue record) {
        int count = 0;
        int maxTries = 3;

        while(count < maxTries) {
            try {
                if (record != null) {
                    HistoricalValue oldValue = new HistoricalValue();
                    oldValue.setMeanValue(record.getMeanValue());
                    oldValue.setMeanBidValue(record.getBidValue());
                    oldValue.setMeanAskValue(record.getAskValue());
                    oldValue.setSource(record.getSource());
                    oldValue.setCurrency(record.getCurrency());
                    oldValue.setDate(record.getDate());
                    oldValue.setSpread(record.getSpread());

                    HistoricalValue archivedRecord = this.historicalValueRepository.save(oldValue);
                    log.info("I've archived value: " + archivedRecord);
                    if(archivedRecord != null){
                        break;
                    }
                }
            } catch (Exception ex) {
                log.error("Error archiving value in database: " + ex.getMessage());
                if(++count == maxTries) throw ex;
            }
        }
    }
}
