package com.ruzzyfer.twitterlike.util;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkSegmentationAnalysis {

    public static void main(String[] args) {

        SparkSession spark = SparkSession.builder()
                .appName("SegmentationAnalysis")
                .master("local[*]") // Yerel modda çalıştır
                .getOrCreate();

        // JDBC bağlantısı
        String jdbcUrl = "jdbc:postgresql://localhost:5432/twitter-like";
        String username = "postgres";
        String password = "postgres";

        Dataset<Row> data = spark.read()
                .format("jdbc")
                .option("url", jdbcUrl)
                .option("dbtable", "user_interaction") // Veritabanı tablosu adı
                .option("user", username)
                .option("password", password)
                .load();

        // Veri analizi yap
        Dataset<Row> segmentationResults = data.groupBy("user_id", "interaction_type")
                .count()
                .orderBy("user_id");

        // Analiz sonuçlarını yazdır
        segmentationResults.show();

        // SparkSession'ı kapat
        spark.stop();
    }

}
