package com.bruce.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@ToString
@Entity
@Table(name = "employee")
@EntityListeners(AuditingEntityListener.class)
public class Employee implements Serializable {

    @Id
    private long id;

    private String name;

    private String depId;

    private String gender;

    private String tel;

    private String address;

    private int age;

    @Formula(value = "(select d.name from department d where d.id = dep_id)")
    @Fetch(FetchMode.SELECT)
    private String depName;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;
}
