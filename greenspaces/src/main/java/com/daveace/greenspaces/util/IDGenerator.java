package com.daveace.greenspaces.util;

import java.util.UUID;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class IDGenerator implements IdentifierGenerator{

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return UUID.randomUUID().toString();
    }


}

