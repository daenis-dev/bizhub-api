package com.greenpalmsolutions.security.userfriends;

import com.greenpalmsolutions.security.accounts.api.behavior.FindCurrentAccount;
import com.greenpalmsolutions.security.accounts.api.behavior.FindUserIdForUsername;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserFriendService implements FindCurrentUserFriends {

    private final UserFriendListRepository userFriendListRepository;
    private final FindCurrentAccount findCurrentAccount;
    private final FindUserIdForUsername findUserIdForUsername;

    @Override
    public boolean doesNotHaveFriendWithUsername(String username) {
        return userFriendListRepository.userIdIsNotInFriendListForOtherUserId(
                findCurrentAccount.getUserIdForCurrentAccount(), findUserIdForUsername.findForUsername(username));
    }
}
