package com.sekom.banking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @CreationTimestamp
    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;

    @OneToMany(mappedBy = "bank", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Account> accounts = new ArrayList<>();

    public void addAccount(Account account){
        this.accounts.add(account);
        account.setBank(this);
    }

    public void deleteAccount(Account account){
        this.accounts.remove(account);
        account.setBank(null);
    }

}
