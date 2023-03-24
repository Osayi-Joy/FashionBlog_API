package com.jconcept.fashionblog.entity;

import com.jconcept.fashionblog.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken extends BaseEntity {
    private final int EXPIRATION_TIME = 10;
    private String token;
    private Date date;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_PASSWORD_RESET_TOKEN"))
    private User user;

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.date = calculateExpirationDate(EXPIRATION_TIME);
    }

    public PasswordResetToken(String token) {
        this.token = token;
        this.date = calculateExpirationDate(EXPIRATION_TIME);
    }

    private Date calculateExpirationDate(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
