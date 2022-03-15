package com.geo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeoPointsMock {
    public List<GeoPoint> list;

    public GeoPointsMock() {
        list = new ArrayList<>();
        list.add(new GeoPoint(53.719097, 91.443676));
        list.add(new GeoPoint(53.71000759116399, 91.4248789830068));
        list.add(new GeoPoint(53.70556383029475, 91.4697052992018));
        list.add(new GeoPoint(53.73061861076997, 91.43119636564931));
        list.add(new GeoPoint(53.72230449589857, 91.44179979643202));
    }

    public List<GeoPoint> getList() {
        return list;
    }

    public GeoPoint getRandomGeoPoint() {
        if (!list.isEmpty()) {
            int index = (new Random()).nextInt(list.size());
            return list.get(index);
        }

        throw new ArrayStoreException("List of geocodes is empty");
    }
}
