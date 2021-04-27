package com.example.maven.expression;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;

/**
 * @author ZhengHao Lou
 * @date 2020/9/13
 */
@Slf4j
public class Expression {
    @Builder
    private static class QuoteEvent {
        long stockId;
        private long timestamp;
        private double latestPrice;
        private String latestTime;
        private long volume;
        private long open;
    }

    @Builder
    @Data
    private static class Item {
        long stockId;
        ConditionType type;
        Operator op;
        double value;
        ConditionConnective connective;

        boolean eval(QuoteEvent quoteEvent, Boolean lastVal) {
            boolean result = false;
            switch (type) {
                case PRICE:
                    result = eval(quoteEvent.latestPrice);
                    break;
                case VOLUME:
                    result = eval(quoteEvent.volume);
                    break;
            }
            if (lastVal == null) {
                return result;
            }
            switch (connective) {
                case OR:
                    result = result || lastVal;
                    break;
                case AND:
                    result = result && lastVal;
                    break;
            }
            return result;
        }

        boolean eval(double val) {
            switch (op) {
                case GE:
                    return val >= value;
                case LE:
                    return val <= value;
                default:
                    break;
            }
            return false;
        }

        void copyOf(Item other) {
            this.setStockId(other.stockId);
            this.setType(other.getType());
            this.setOp(other.getOp());
            this.setValue(other.value);
            this.setConnective(other.getConnective());
        }

    }
    private static final int MAX_NUM = 10;
    private final Item[] items;
    private int curLen = 0;

    public static Expression newExpression(Item... items) {
        Expression expression = new Expression();
        expression.buildExpression(items);
        return expression;
    }

    private Expression() {
        items = new Item[MAX_NUM];
        for (int i = 0; i < MAX_NUM; i++) {
            this.items[i] = Item.builder().build();
        }
    }

    private void buildExpression(Item... items) {
        this.curLen = Math.min(MAX_NUM, items.length);
        for (int i = 0; i < Math.min(MAX_NUM, items.length); i++) {
            this.items[i].copyOf(items[i]);
        }
    }

    public boolean eval(Map<Long, QuoteEvent> params) {
        if (this.curLen == 0) {
            return false;
        }
        Boolean lastVal = null;
        for (int i = 0; i < this.curLen; i++) {
            long stockId = items[i].getStockId();
            if (!params.containsKey(stockId)) {
                return false;
            }
            lastVal = items[i].eval(params.get(stockId),  lastVal);
        }
        return lastVal;
    }

    @Override
    public String toString() {
        return Joiner.on(", ").skipNulls().join(Arrays.asList(this.items).subList(0, this.curLen));
    }

    public static void main(String[] args) {
        final long stockId = 1L;
        // stockId = 1L, price >= 5.0
        Item item1 = Item.builder().stockId(stockId).type(ConditionType.PRICE).op(Operator.GE).value(10.0).build();
        Item item2 = Item.builder().stockId(stockId).type(ConditionType.VOLUME).op(Operator.LE).value(10000).connective(ConditionConnective.AND).build();
        Item[] items = new Item[]{item1, item2};

        Expression expression = Expression.newExpression(items);

        QuoteEvent quoteEvent = QuoteEvent.builder().latestPrice(6.0).volume(10000).build();
        Map<Long, QuoteEvent> params = Maps.newHashMap();
        params.put(stockId, quoteEvent);
        boolean result = expression.eval(params);
        log.info("expression: {}, result: {}", expression, result);
    }

}
