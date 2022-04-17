package com.ead.course.configurations.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Arrays
import java.util.UUID
import java.util.stream.Collectors

class UserDetailsImpl(var id: UUID, private var authorities: Collection<GrantedAuthority>) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String? {
        return null
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun setAuthorities(authorities: Collection<GrantedAuthority>) {
        this.authorities = authorities
    }

    companion object {
        fun build(userID: UUID, rolesStr: String): UserDetailsImpl {
            val authorities: Collection<GrantedAuthority> = Arrays.stream(
                rolesStr.split(",").toTypedArray()
            )
                .map { role: String? -> SimpleGrantedAuthority(role) }
                .collect(Collectors.toList())
            return UserDetailsImpl(
                userID,
                authorities
            )
        }
    }
}