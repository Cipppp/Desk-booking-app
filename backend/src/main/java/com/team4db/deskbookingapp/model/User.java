package com.team4db.deskbookingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {
    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String userID;
    @JsonIgnore
    private String username; // used by interface UserDetails not by program
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean enabled;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private int nrOfBookedDesks = 0;

    @ManyToOne
    private Manager manager;

    @OneToMany
    private List<Booking> userBookings;

    // TODO if needed
//    @ElementCollection(targetClass=Desk.class)
//    List<Desk> bookedDesksHistory;

    @Column
    @ElementCollection(targetClass=GrantedAuthority.class, fetch = FetchType.EAGER)
    private Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
