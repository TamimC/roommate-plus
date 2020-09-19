package com.tamimtechnology.roommateplus.model.SecurityEntities;

import com.tamimtechnology.roommateplus.model.BusinessEntities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date createdDate;
    @OneToOne
    private User user;
    private Date expiresAt;

    public RefreshToken(String token, Date createdDate, Date expiresAt, User user) {
        this.token = token;
        this.createdDate = createdDate;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
