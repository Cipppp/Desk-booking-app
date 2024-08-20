package com.team4db.deskbookingapp.modelDTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team4db.deskbookingapp.model.Manager;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class UserDTO{
//    @Id
//    @GeneratedValue(generator="system-uuid")
//    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String userID;
//    @JsonIgnore
//    private String username; // used by interface UserDetails not by program
//    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean enabled;
//    private Boolean accountNonExpired;
//    private Boolean accountNonLocked;
//    private boolean credentialsNonExpired;

//    @ManyToOne
    private Manager manager;

//    @Column
//    @ElementCollection(targetClass= GrantedAuthority.class, fetch = FetchType.EAGER)
//    private Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return accountNonExpired;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return accountNonLocked;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return credentialsNonExpired;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return enabled;
//    }
}
