package geocode;

import geocode.GeoName;
import geocode.kdtree.KDTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ldc on 17/12/15.
 * <p>
 * Find nearest location from within a list. Relies on externally defined geoplaces. Good for one
 * lookup only -- geoplaces data must be re-read for new one.
 */
public class FilteredGeoCode {

    public static String findnearest(InputStream geoplaces, List<String> candidates, String search) throws IOException {
        ArrayList<GeoName> arPlaceNames;
        Set<String> neededPlaces = new HashSet<>();
        neededPlaces.add(search);
        neededPlaces.addAll(candidates);
        arPlaceNames = new ArrayList<>();
        BufferedReader in = new BufferedReader(new InputStreamReader(geoplaces));
        String str;
        GeoName toSearch = null;
        while ((str = in.readLine()) != null) {
            GeoName newPlace = new GeoName(str);
            if (candidates.contains(newPlace.name)) {
                arPlaceNames.add(newPlace);
                neededPlaces.remove(newPlace.name);
            }
            if (newPlace.name.endsWith(search)) {
                toSearch = newPlace;
            }
            if (neededPlaces.isEmpty()) {
                break;
            }
        }
        return new KDTree<>(arPlaceNames).findNearest(toSearch).name;
    }

}
