package com.springbank.user.core.events;

import com.springbank.user.core.models.User;
import lombok.Builder;
import lombok.Data;

@Data
public class UserRemovedEvent {

    private String id;
}
