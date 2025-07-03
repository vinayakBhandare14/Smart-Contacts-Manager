package com.scm.entities;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class User implements UserDetails {

    @Id
    private String userId;
    @Column(name="User_name")
    private String name;
    @Column(unique=true,nullable=false)
    private String email;
    @Getter(value = AccessLevel.NONE)
    private String password;

    @Lob
    @Column()
    private String about;
    @Lob
    @Column()
    private String profilePic;
    private String phoneNumber;

    //Information
    @Getter(value = AccessLevel.NONE)
    @Builder.Default
    private boolean enabled = true;
    @Builder.Default
    private boolean emailVerified = false;
    @Builder.Default
    private boolean phoneVerified = false;

    @Enumerated(value = EnumType.STRING)
    //self,google,facebook,(Authentication)
    private Provider provider = Provider.SELF;
    private String providerUserId;

    // Add more field if needed
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
    private List<Contact> contacts = new ArrayList<>();

    
//    roll list
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();
    
//    role 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
//		collection of roles[USER,ADMIN]
//		Collection of SimpleGrantedAuthority [roles{USER,ADMIN}]
	Collection<SimpleGrantedAuthority> roles = 	roleList.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
		
		return roles;
	}

	
//	Over emailId is Over Username
	@Override
	public String getUsername() {
		return this.email;
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
		return this.enabled;
	}


	@Override
	public String getPassword() {		
		return this.password;
	}



}
