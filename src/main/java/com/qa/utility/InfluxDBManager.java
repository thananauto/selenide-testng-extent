package com.qa.utility;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

public class InfluxDBManager {

    private static final InfluxDB influxDBInstance = InfluxDBFactory.connect("https://8086-port-bf4e07ca30eb4a36.labs.kodekloud.com", "root", "root");
    private static final String DB_NAME = "selenium_test_results";

    static {
        influxDBInstance.setDatabase(DB_NAME);
    }

    public static void post(final Point point) {
        influxDBInstance.write(point);
    }
}
