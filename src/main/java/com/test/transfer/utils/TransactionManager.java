package com.test.transfer.utils;

import java.sql.SQLException;

/**
 * @author 应癫
 *
 * 事务管理器类：负责手动事务的开启、提交、回滚
 */
public class TransactionManager {
    private TransactionManager(){

    }

    private static TransactionManager transactionManager = new TransactionManager();

    public static TransactionManager getInstance() {
        return  transactionManager;
    }


    // 开启手动事务控制
    public void beginTransaction() throws SQLException {
        ConnectionUtils.getInstance().getCurrentThreadConn().setAutoCommit(false);
    }


    // 提交事务
    public void commit() throws SQLException {
        ConnectionUtils.getInstance().getCurrentThreadConn().commit();
    }


    // 回滚事务
    public void rollback() throws SQLException {
        ConnectionUtils.getInstance().getCurrentThreadConn().rollback();
    }
}
