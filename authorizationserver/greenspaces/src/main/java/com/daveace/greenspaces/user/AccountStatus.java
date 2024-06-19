package com.daveace.greenspaces.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AccountStatus {

    private boolean locked;
    private boolean expired;
    private boolean credentialsExpired;
    private boolean enabled;

    private AccountStatus(Builder builder){
        this.locked = builder.locked;
        this.expired = builder.expired;
        this.credentialsExpired=builder.credentialsExpired;
        this.enabled = builder.enabled;
    }

    public static Builder Builder(){
        return new Builder();
    }

    public static class Builder{
        private boolean locked;
        private boolean expired;
        private boolean credentialsExpired;

        private boolean enabled;

        public Builder locked(boolean locked){
            this.locked = locked;
            return this;
        }

        private Builder(){}

        public Builder expired(boolean expired){
            this.expired = expired;
            return this;
        }

        public Builder credentialsExpired(boolean credentialsExpired){
            this.credentialsExpired = credentialsExpired;
            return this;
        }

        public Builder enabled(boolean enabled){
            this.enabled = enabled;
            return this;
        }

        public Builder user(User user){
            this.locked=!user.isAccountNonLocked();
            this.expired=!user.isAccountNonExpired();
            this.credentialsExpired=!user.isCredentialsNonExpired();
            this.enabled=!user.isEnabled();
            return this;
        }

        public AccountStatus build(){
            return new AccountStatus(this);
        }
    }

}
