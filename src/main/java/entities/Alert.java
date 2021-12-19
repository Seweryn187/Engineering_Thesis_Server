package entities;

import javax.persistence.*;

@Entity
@Table(name = "alerts")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "alert_value", nullable = false)
    private Integer alertValue;

    @Column(name = "increase", nullable = false)
    private Boolean increase = false;

    @Column(name = "repeatable", nullable = false)
    private Boolean repeatable = false;

    public Alert() {}

    public Alert(Integer id, Currency currency, User user, Integer alertValue, Boolean increase, Boolean repeatable) {
        this.id = id;
        this.currency = currency;
        this.user = user;
        this.alertValue = alertValue;
        this.increase = increase;
        this.repeatable = repeatable;
    }

    public Boolean getRepeatable() {
        return repeatable;
    }

    public void setRepeatable(Boolean repeatable) {
        this.repeatable = repeatable;
    }

    public Boolean getIncrease() {
        return increase;
    }

    public void setIncrease(Boolean increase) {
        this.increase = increase;
    }

    public Integer getAlertValue() {
        return alertValue;
    }

    public void setAlertValue(Integer alertValue) {
        this.alertValue = alertValue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                ", currency=" + currency +
                ", user=" + user +
                ", alertValue=" + alertValue +
                ", increase=" + increase +
                ", repeatable=" + repeatable +
                '}';
    }


}