package com.epam.testsystem.github;

import com.epam.testsystem.github.dao.UserDao;
import com.epam.testsystem.github.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;

import static com.epam.testsystem.github.EnvironmentConstant.SPRING_PROFILE_TEST;

/**
 * github_test
 * Created on 05.07.17.
 */

@Service
@Profile(value = SPRING_PROFILE_TEST)
@RequiredArgsConstructor
public class TestUtil {
    private final UserDao userDao;
    private static SecureRandom random = new SecureRandom();

    public User makeUser() {
        return userDao.add(generateString(), generateString());
    }

    public User makeUser(final String email, final String githubNick) {
        return userDao.add(email, githubNick);
    }

    private static String generateString() {
        return new BigInteger(130, random).toString(32);
    }
}