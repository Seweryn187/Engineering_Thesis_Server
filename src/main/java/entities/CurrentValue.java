package entities;

import javax.persistence.*;

@Table(name = "\"public.currentValue\"")
@Entity
public class CurrentValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "buyValue")
    private Integer buyValue;

    @Column(name = "sellValue")
    private Integer sellValue;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sourceId")
    private Source source;

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
}