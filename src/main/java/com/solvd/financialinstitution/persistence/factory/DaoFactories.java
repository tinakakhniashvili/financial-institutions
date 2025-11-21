package com.solvd.financialinstitution.persistence.factory;

public final class DaoFactories {

    private DaoFactories() {
    }

    public static DaoFactory create(String type) {
        if ("jdbc".equalsIgnoreCase(type)) {
            return new JdbcDaoFactory();
        }
        if ("mybatis".equalsIgnoreCase(type)) {
            return new MyBatisDaoFactory();
        }
        throw new IllegalArgumentException("Unknown DaoFactory type: " + type);
    }
}
