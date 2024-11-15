package Config;

import Model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

// create undo
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductConfig {

    private  Stack<ArrayList<Product>> undoAction = new Stack<>();

    private int action ;

    public static final int DELETE_ROW_ON_TABLE = 2;

    public static final int DELETE_ROW_ON_TABLE_SEARCH  = 1;

    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
