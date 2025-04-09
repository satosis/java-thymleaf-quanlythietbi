package com.example.watchex.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "borrow_histories")
@Getter
@Setter
public class BorrowHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="devices_id", nullable=false)
    private Devices devices;

    @Column(name = "borrow_date", nullable = false)
    private Date borrowDate;

    @Column(name = "expected_return_date", nullable = false)
    private Date expectedReturnDate;

    @Column(name = "actual_return_date")
    private Date actualReturnDate;

    @Column(name = "status_device")
    private Integer statusDevice;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;


}
