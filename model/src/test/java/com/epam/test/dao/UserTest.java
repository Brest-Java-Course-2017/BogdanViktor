package com.epam.test.dao;

import junit.framework.Assert;

/**
 * Created by sw0rd on 14.02.17.
 */
public class UserTest {
    public static final int USER_ID =11;
    public static final String USER_LOGIN ="login";
    public static final String USER_DESCRIPTION ="login";
    public static final String USER_PASSWORD ="pass";


    @org.junit.Test
    public void getUserId() throws Exception {
        User user = new User();
        user.setUserId(11);
        Assert.assertEquals( "User id:", (Integer)USER_ID, user.getUserId() );
    }


    @org.junit.Test
    public void getLogin() throws Exception {
        User user = new User();
        user.setLogin(USER_LOGIN);
        Assert.assertEquals("user login", USER_LOGIN, user.getLogin() );

    }


    @org.junit.Test
    public void getPassword() throws Exception {
        User user = new User();
        user.setPassword(USER_PASSWORD);
        Assert.assertEquals("user password", USER_PASSWORD, user.getPassword() );
    }



    @org.junit.Test
    public void getDescription() throws Exception {
        User user = new User();
        user.setDescription(USER_DESCRIPTION);
        Assert.assertEquals("user description", USER_DESCRIPTION, user.getDescription() );
    }


}