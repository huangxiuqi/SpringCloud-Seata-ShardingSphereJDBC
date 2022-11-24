package org.apache.shardingsphere.transaction.base.seata.at;

import io.seata.tm.api.GlobalTransaction;

/**
 * @author huangxiuqi
 */
public final class SeataTransactionHolder {

    private SeataTransactionHolder() {

    }

    private static final ThreadLocal<GlobalTransaction> CONTEXT = new ThreadLocal<>();

    /**
     * Set seata global transaction.
     *
     * @param transaction global transaction context
     */
    public static void set(final GlobalTransaction transaction) {
        CONTEXT.set(transaction);
    }

    /**
     * Get seata global transaction.
     *
     * @return global transaction
     */
    public static GlobalTransaction get() {
        return CONTEXT.get();
    }

    /**
     * Clear global transaction.
     */
    public static void clear() {
        CONTEXT.remove();
    }
}