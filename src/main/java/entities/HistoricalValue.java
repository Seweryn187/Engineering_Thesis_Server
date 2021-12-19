package entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "historical_value")
public class HistoricalValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "mean_value", nullable = false)
    private Integer meanValue;

    @Column(name = "mean_buy_value", nullable = false)
    private Integer meanBuyValue;

    @Column(name = "mean_sell_value", nullable = false)
    private Integer meanSellValue;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    @ManyToOne(optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getMeanSellValue() {
        return meanSellValue;
    }

    public void setMeanSellValue(Integer meanSellValue) {
        this.meanSellValue = meanSellValue;
    }

    public Integer getMeanBuyValue() {
        return meanBuyValue;
    }

    public void setMeanBuyValue(Integer meanBuyValue) {
        this.meanBuyValue = meanBuyValue;
    }

    public Integer getMeanValue() {
        return meanValue;
    }

    public void setMeanValue(Integer meanValue) {
        this.meanValue = meanValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "HistoricalValue{" +
                "id=" + id +
                ", meanValue=" + meanValue +
                ", meanBuyValue=" + meanBuyValue +
                ", meanSellValue=" + meanSellValue +
                ", date=" + date +
                ", source=" + source +
                ", currency=" + currency +
                '}';
    }
}