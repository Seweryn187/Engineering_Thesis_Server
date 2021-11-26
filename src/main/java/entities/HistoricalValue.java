package entities;

import javax.persistence.*;
import java.util.Date;

@Table(name = "\"public.historicalValues\"")
@Entity
public class HistoricalValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "meanValue")
    private Integer meanValue;

    @Column(name = "meanBuyValue")
    private Integer meanBuyValue;

    @Column(name = "meanSellValue")
    private Integer meanSellValue;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sourceId")
    private Source source;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "currencyId")
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}