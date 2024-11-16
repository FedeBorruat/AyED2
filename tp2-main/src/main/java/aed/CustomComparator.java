package aed;

import java.util.Comparator; 

public class CustomComparator { 

    //si t1 > t2 -> res > 0
    //si t1 < t2 -> res < 0
    //si t1 = t2 -> res = 0

    public static final Comparator<Traslado> BY_GANANCIA = (t1, t2) -> {
        int res = Integer.compare(t1.getGananciaNeta(), (t2.getGananciaNeta()));
        if (res == 0){
            return Integer.compare( - (t1.getId()), - (t2.getId()));
        }
        return res;
    };
        
    public static final Comparator<Traslado> BY_TIMESTAMP = (t1, t2) -> {
        int res = Integer.compare(-(t1.getTimestamp()), -(t2.getTimestamp()));
        if (res == 0){
            return Integer.compare( - (t1.getId()), - (t2.getId()));
        }
        return res;
    };

    public static final Comparator<Ciudad> BY_BALANCE = (c1, c2) -> { 
        int res = Integer.compare(c1.getBalance(), (c2.getBalance()));
        if (res == 0){
            return Integer.compare( - (c1.getId()), - (c2.getId()));
        }
        return res;
    };
}

