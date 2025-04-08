package com.example.watchex.entity;

import com.example.watchex.utils.CommonUtils;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "devices")
@Getter
@Setter
public class Devices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
    private String slug;
    private String avatar;

    private String serial_number;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String operational_status;
    private String availability_status;
    private String description;
    private String location;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public void setSlug() {
        this.slug = CommonUtils.toSlug(this.name);
    }

    public String getAvatar() {
        if (this.avatar != null) {
            return "http://localhost:8081/uploads/" + this.avatar;
        }
        return "http://localhost:8081/view/img/no-image.png";
    }
}
