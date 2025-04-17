package com.example.watchex.entity;

import com.example.watchex.utils.CommonUtils;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45, nullable = false)
    private String name;

    private String avatar;

    @Column(nullable = false, unique = true)
    private String email;

    private String provider;

    @Column(length = 255)
    private String provider_id;

    private String phone;
    private String password;
    private String student_id;
    private String role;
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public void setPassword(String password) {
        this.password = CommonUtils.encode(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getUsername() {
        return null;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getAvatar() {
        if (this.avatar != null) {
            return "http://localhost:8081/" + this.avatar;
        }
        return "http://localhost:8081/view/img/no-image.png";
    }
}
