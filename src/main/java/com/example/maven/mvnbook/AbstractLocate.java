package com.example.maven.mvnbook;

/**
 * @author ZhengHao Lou
 * @date 2020/05/07
 */
public abstract class AbstractLocate {

   /**
    *
     * @param orderId   订单id
    * @param contractId 股票id
    * @param quantity   额度
    * @return
    */
   abstract boolean locate(int orderId, int contractId, int quantity);

}




