package entities;

import javax.persistence.*;

@Entity
@Table(name = "current_value")
public class CurrentValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "buy_value", nullable = false)
    private Integer buyValue;

    @Column(name = "sell_value", nullable = false)
    private Integer sellValue;

    @ManyToOne(optional = false)
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    @Column(name = "mean_value", nullable = false)
    private Integer meanValue;

    public Integer getMeanValue() {
        return meanValue;
    }

    public void setMeanValue(Integer meanValue) {
        this.meanValue = meanValue;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Integer getSellValue() {
        return sellValue;
    }

    public void setSellValue(Integer sellValue) {
        this.sellValue = sellValue;
    }

    public Integer getBuyValue() {
        return buyValue;
    }

    public void setBuyValue(Integer buyValue) {
        this.buyValue = buyValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CurrentValue{" +
                "id=" + id +
                ", buyValue=" + buyValue +
                ", sellValue=" + sellValue +
                ", source=" + source +
                ", meanValue=" + meanValue +
                '}';
    }
}