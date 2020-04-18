package org.fjh.security.service;

import org.fjh.security.entity.User;

public interface UserService {
    default User getByUcode(String ucode){return null;};
}
