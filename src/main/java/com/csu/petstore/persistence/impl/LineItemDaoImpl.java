package com.csu.petstore.persistence.impl;

import com.csu.petstore.domain.LineItem;
import com.csu.petstore.persistence.DBUtil;
import com.csu.petstore.persistence.LineItemDao;

import java.util.List;

public class LineItemDaoImpl implements LineItemDao {

    private static final String GET_LINEITEMS_BY_ORDERID = """
            SELECT
                  ORDERID,
                  LINENUM AS lineNumber,
                  ITEMID,
                  QUANTITY,
                  UNITPRICE
                FROM LINEITEM
                WHERE ORDERID = ?\s""";

    @Override
    public List<LineItem> getLineItemsByOrderId(int orderId) {
        return List.of();
    }

    public void insertLineItem(LineItem lineItem) {

    }
}
