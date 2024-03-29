package com.ebious.aviasale.utils;

import org.springframework.stereotype.Service;

@Service
public class DistanceUtil {

    /**
     * lat = широта; lng = долгота; PointX = lng; PointY = lat;
     */
    public double distByCoordinates(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = (earthRadius * c) / 1000;
        return distance - distance % 10;
    }
}
