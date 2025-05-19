package com.ssnagin.collectionmanager.user.objects;

import com.ssnagin.collectionmanager.description.annotations.Description;
import lombok.*;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@NoArgsConstructor
public class InternalUser extends User {


    @Description(name="", description = "")
    private User user;

    public void setUser(User user) {
        setUsername(user.getUsername());
    }

    @Description(name = "password", description = "Введите пароль")
    protected String unsafePassword = null;

    public void setUnsafePassword(String unsafePassword) {
        super.setPassword(unsafePassword.toCharArray());
    }

    public InternalUser(User user) {
        super.setId(user.getId());
        super.setUsername(user.getUsername());
        super.setIsBanned(user.getIsBanned());
        super.setCreated(user.getCreated());

        super.setPassword(user.getPassword());
    }
}
