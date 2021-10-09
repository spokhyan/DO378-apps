package org.redhat.currency;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Currency extends PanacheEntityBase {
    @Id
    public String name;
    public String sign;
}
