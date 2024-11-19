package com.system.creditOnline.service;

import com.system.creditOnline.entity.User;

public interface UserService {
    User registerUser(User user);

    int calculateCreditLine(int age);
}
