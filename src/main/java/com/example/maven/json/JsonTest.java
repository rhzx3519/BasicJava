package com.example.maven.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/11/05
 */
@Slf4j
public class JsonTest {
    private static class Order implements Serializable {

        private Long id;
        private Long accountId;
        private String origClOrdId;
        private String clOrdId;
        private String segType;
        private Long execAccountId;
        private String currency;
        private String secType;
        private String externalId;
        private Long stockId;
        private String market;
        private String symbol;
        private String expireDate;
        private String strike;
        private Boolean isCall;
        private String action;
        private Boolean isLong;
        private String orderType;
        private Double price;
        private BigDecimal limitPrice;
        private BigDecimal stopPrice;
        private Double multiplier;
        private Double refPrice;
        private BigDecimal referencePrice;
        private Double baseToUsdRate;
        /**
         * @deprecated
         */
        @Deprecated
        private Integer totalQty;
        private BigDecimal totalQuantityDecimal;
        /**
         * @deprecated
         */
        @Deprecated
        private Integer leavesQty;
        private BigDecimal leavesQuantityDecimal;
        /**
         * @deprecated
         */
        @Deprecated
        private Integer filledQty;
        private BigDecimal filledQuantityDecimal;
        private Double avgFillPrice;
        private BigDecimal avgFilledPrice;
        private String status;
        private String timeInForce;
        private LocalDate goodUntilDate;
        private Boolean onlyRth;
        private Double commission;
        private Double gst;
        private BigDecimal displayCommission;
        private Boolean isSettled;
        private Boolean isLiquidation;
        private Byte discount;
        private Double realizedPnl;
        private String message;
        private String messageData;
        private Byte cancelStatus;
        private Byte replaceStatus;
        private String replaceRequest;
        private Boolean hasParams;
        private String params;
        private String source;
        private Integer attr;
        private String execInst;
        private String remark;
        private Long placeUserId;
        private Date statusUpdatedAt;
        private Date updatedAt;
        private Date createdAt;
        private static final long serialVersionUID = 1L;
    }

    public static void main(String[] args) {
        String json = "[\n"
                + "        {\n"
                + "            \"symbol\": \"01114\",\n"
                + "            \"orderType\": \"STP_LMT\",\n"
                + "            \"isLevel0Price\": false,\n"
                + "            \"replaceStatus\": \"FAILED\",\n"
                + "            \"cancelStatus\": \"NONE\",\n"
                + "            \"statusUpdatedAt\": 1604563866,\n"
                + "            \"nameCN\": \"华晨中国\",\n"
                + "            \"discount\": 0,\n"
                + "            \"secType\": \"STK\",\n"
                + "            \"canModify\": true,\n"
                + "            \"filledQuantity\": 0,\n"
                + "            \"totalQuantityScale\": 0,\n"
                + "            \"createdAt\": 1604563866,\n"
                + "            \"totalQuantity\": 2000,\n"
                + "            \"price\": 50.0,\n"
                + "            \"action\": \"BUY\",\n"
                + "            \"currency\": \"HKD\",\n"
                + "            \"commission\": 0.0,\n"
                + "            \"id\": 20403279148745728,\n"
                + "            \"filledQuantityScale\": 0,\n"
                + "            \"timeInForce\": \"DAY\",\n"
                + "            \"commissionAndFee\": 0.0,\n"
                + "            \"attrDesc\": \"\",\n"
                + "            \"updatedAt\": 1604564118,\n"
                + "            \"idStr\": \"20403279148745728\",\n"
                + "            \"liquidation\": false,\n"
                + "            \"latestPrice\": 7.7,\n"
                + "            \"statusDesc\": \"已接受\",\n"
                + "            \"onlyRth\": true,\n"
                + "            \"message\": \"algo.order.modify.reject.immutable.triggeredOrder.param\",\n"
                + "            \"attrList\": [\n"
                + "                \"ALGORITHM\"\n"
                + "            ],\n"
                + "            \"market\": \"HK\",\n"
                + "            \"stopPrice\": 30.0,\n"
                + "            \"avgFillPrice\": 0.0,\n"
                + "            \"realizedPnl\": 0.0,\n"
                + "            \"messageCode\": \"algo.order.modify.reject.immutable.triggeredOrder.param\",\n"
                + "            \"status\": \"New\",\n"
                + "            \"canCancel\": true\n"
                + "        }\n"
                + "    ]";
        JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
        jsonArray.forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.has("messageCode")) {
                String messageCode = jsonObject.get("messageCode").getAsString();
            }

        });
    }

}
