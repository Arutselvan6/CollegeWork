package com.niit.BackEnd.Service;

import com.niit.BackEnd.Model.UserOrder;

public interface UserOrderService {

    void addUserOrder(UserOrder userOrder);

    double getUserOrderGrandTotal(int cartId);
}
