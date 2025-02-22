package com.greenpalmsolutions.security.userfriends;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserFriendListTest {

    private UserFriendList userFriendList;

    @BeforeEach
    void init() {
        userFriendList = new UserFriendList();
        userFriendList.setId(1);
        userFriendList.setCreatedDateTime(ZonedDateTime.of(1990, 12, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
    }

    @Test
    void isEqualToUserFriendListWithTheSameProperties() {
        UserFriendList otherUserFriendList = new UserFriendList();
        otherUserFriendList.setId(1);
        otherUserFriendList.setCreatedDateTime(ZonedDateTime.of(1990, 12, 1, 0, 0, 0, 0, ZoneId.of("UTC")));

        boolean userFriendListEqualsOtherUserFriendList = userFriendList.equals(otherUserFriendList);

        assertThat(userFriendListEqualsOtherUserFriendList).isTrue();
    }

    @Test
    void generatesTheExpectedHashCode() {
        int theHashCode = userFriendList.hashCode();

        assertThat(theHashCode).isEqualTo(215457027);
    }

}