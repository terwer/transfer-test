package com.test.transfer.utils;

/**
 * @author: terwer
 * @date: 2021/12/16 00:24
 * @description:
 */
public class createConnectionFactory {
    public static ConnectionUtils getInstanceStatic(){
        return new ConnectionUtils();
    }

    public ConnectionUtils getInstance(){
        return new ConnectionUtils();
    }
}
