package org.reallylastone.snowflake;

import com.snowflake.snowpark_java.types.DataTypes;
import com.snowflake.snowpark_java.types.StructField;
import com.snowflake.snowpark_java.types.StructType;

/**
 * Class that encapsulates the information about Snowflake's tables definition
 */
public class TableTypes {
    private TableTypes() {
        throw new AssertionError();
    }

    public static StructType getTradeType() {
        return StructType.create(
                new StructField("price", DataTypes.DoubleType),
                new StructField("symbol", DataTypes.StringType),
                new StructField("timestamp", DataTypes.TimestampType),
                new StructField("float", DataTypes.DoubleType));
    }
}
