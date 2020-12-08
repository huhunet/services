package com.miracle.iam.service;

import com.miracle.iam.domain.entity.Role;

public interface IRoleService {
    Role getAuthoritiesByUserName(String userName);
}
