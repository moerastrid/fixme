package fixme.core;

import java.util.Arrays;

// https://www.onixs.biz/fix-dictionary/4.2/fields_by_tag.html
public enum FixTag {

    // head:
    SENDER_ID(49), //int. Id assigned by router. "Assigned value used to identify firm sending message." ALWAYS FIRST FIELD
    FIXME_TYPE(5001), //MessageType, our own message type tag (String). Defines message type (BUY, SELL, EXECUTED, REJECTED). ALWAYS SECOND FIELD
    TARGET_ID(56), //String. "Assigned value used to identify receiving firm."

    // broker message:
    INSTRUMENT(55), //String. "Ticker symbol."
    BROKER_ORDER_ID(37), //int. "Unique identifier for Order as assigned by broker. "
    QUANTITY(38), //int. "Number of shares ordered."
    MARKET(5002), //String.
    PRICE(44), //int. "Price per share."

    // tail:
    CHECKSUM(10); //int. "3 byte, simple checksum." ALWAYS LAST FIELD.

    // BEGIN_STRING(8), //String. Identifies beginning of new message and protocol version. ALWAYS FIRST FIELD IN MESSAGE. (Always unencrypted)
    // BODY_LENGTH(9), //int. Message length, in bytes, forward to the CheckSum <10> field. ALWAYS SECOND FIELD IN MESSAGE. (Always unencrypted)
    // MSG_TYPE(35), //String. Defines message type. ALWAYS THIRD FIELD IN MESSAGE. (Always unencrypted)

    // ORDER_ID(37), //String. Unique identifier for Order as assigned by broker. Uniqueness must be guaranteed within a single trading day. Firms which accept multi-day orders should consider embedding a date within the OrderID <37> field to assure uniqueness across days.
    // ORDER_QTY(38), //int. Number of shares ordered. This represents the number of shares for equities or based on normal convention the number of contracts for options, futures, convertible bonds, etc.
    // ORD_STATUS(39), //char. Identifies current status of order.
    // PRICE(44), //float. Price per share
    // SENDER_COMP_ID(49), //String. Assigned value used to identify firm sending message.
    // SIDE(54), //char. Side of order
    // SYMBOL(55), //String. Ticker symbol. (for us: instrument?)
    // TARGET_COMP_ID(56), //String. Assigned value used to identify receiving firm.
    // EX_DESTINATION(100), //String. Execution destination as defined by institution when order is entered.
    // EXEC_TYPE(150), //char. Describes the specific ExecutionRpt <8> (i.e. Pending Cancel) while OrdStatus <39> will always identify the current order status (i.e. Partially Filled)

    // CHECK_SUM(10); //String. Three byte, simple checksum (see Appendix B: CheckSum Calculation for description). ALWAYS LAST FIELD IN MESSAGE; i.e. serves, with the trailing <SOH>, as the end-of-message delimiter. Always defined as three characters. (Always unencrypted)


    private final int tag;

    FixTag(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public static boolean exists(String fixTagName) {
        return Arrays.stream(values()).anyMatch(predicate -> predicate.name().equals(fixTagName));
    }

    public static boolean exists(int fixTag) {
        return Arrays.stream(values()).anyMatch(predicate -> predicate.equals(fixTag));
    }

}
