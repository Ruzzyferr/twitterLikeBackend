package com.ruzzyfer.twitterlike.util;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class SparkSegmentationAnalysis {

    public static void main(String[] args) {
        // Zamanlayıcı oluştur
        Timer timer = new Timer();

        // TimerTask sınıfı aracılığıyla zamanlanmış görevi tanımla
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // SparkSession oluştur
                SparkSession spark = SparkSession.builder()
                        .appName("SegmentationAnalysis")
                        .master("local[*]") // Yerel modda çalıştır
                        .getOrCreate();

                // JDBC bağlantısı
                String jdbcUrl = "jdbc:postgresql://localhost:5432/twitter-like";
                String username = "postgres";
                String password = "postgres";

                // Veri okuma
                Dataset<Row> data = spark.read()
                        .format("jdbc")
                        .option("url", jdbcUrl)
                        .option("dbtable", "user_interaction") // Veritabanı tablosu adı
                        .option("user", username)
                        .option("password", password)
                        .load();

                // Veri analizi
                Dataset<Row> segmentationResults = data.groupBy("userid", "interactionType", "category")
                        .count()
                        .orderBy("userid", "interactionType", "count");

                // Analiz sonuçlarını yazdır
                segmentationResults.show();

                // SparkSession'ı kapat
                spark.stop();
            }
        };

        // Her gün saat 23:50'de görevi çalıştır
        timer.schedule(task, getDelay(), 24 * 60 * 60 * 1000); // Bir gün aralıklarla çalıştır

        // Programı sonlandırmamak için sonsuz bir döngüye gir
        while (true) {
            try {
                Thread.sleep(1000); // CPU'yu yormamak için bekle
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Belirli bir zamana kadar kalan gecikmeyi hesapla
    private static long getDelay() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();

        int targetHour = 23;
        int targetMinute = 50;

        // Eğer hedef saat ve dakika geçmişse, bir sonraki gün için hesapla
        if ((hour > targetHour) || (hour == targetHour && minute >= targetMinute)) {
            hour = targetHour;
            minute = targetMinute;
        }

        // Hedef saat ve dakikaya kadar kalan gecikmeyi hesapla
        long currentMillis = System.currentTimeMillis();
        long targetMillis = (hour * 60 + minute) * 60 * 1000;
        return targetMillis - currentMillis;
    }
}
