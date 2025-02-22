package com.greenpalmsolutions.security.userfriends;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserFriendTest {

    private UserFriend userFriend;

    @BeforeEach
    void init() {
        userFriend = new UserFriend();
        userFriend.setId(1);
        userFriend.setUserFriendListId(1);
        userFriend.setFriendUserId("123-abc");
    }

    @Test
    void isEqualToUserFriendWithTheSameProperties() {
        UserFriend otherUserFriend = new UserFriend();
        otherUserFriend.setId(1);
        otherUserFriend.setUserFriendListId(1);
        otherUserFriend.setFriendUserId("123-abc");

        boolean userFriendEqualsOtherUserFriend = userFriend.equals(otherUserFriend);

        assertThat(userFriendEqualsOtherUserFriend).isTrue();
    }

    @Test
    void generatesTheExpectedHashCode() {
        int theHashCode = userFriend.hashCode();

        assertThat(theHashCode).isEqualTo(-1184670763);
    }
}