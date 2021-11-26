package entities;

import javax.persistence.*;

@Table(name = "\"public.currencies\"", indexes = {
        @Index(name = "public.Currencies_abbr_key", columnList = "abbr", unique = true),
        @Index(name = "public.Currencies_name_key", columnList = "name", unique = true)
})
@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "abbr", length = 3)
    private String abbr;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "currentValueId")
    private CurrentValue currentValue;

    public CurrentValue getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(CurrentValue currentValue) {
        this.currentValue = currentValue;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}