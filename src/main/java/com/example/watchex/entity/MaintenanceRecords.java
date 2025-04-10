package com.example.watchex.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "maintenance_records")
@Getter
@Setter
public class MaintenanceRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="devices_id", nullable=false)
    private Devices devices;

    @ManyToOne
    @JoinColumn(name = "reported_by", nullable = false)
    private User reportedUser;

    @ManyToOne
    @JoinColumn(name = "maintenance_by")
    private User maintenanceUser;

    private String loiThietBi;

    private String maintenance_status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;


}
