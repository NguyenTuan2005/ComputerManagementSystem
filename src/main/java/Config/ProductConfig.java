package Config;

import Model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

// create undo
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductConfig {

    private  Stack<ArrayList<Product>> undoAction = new Stack<>();

    private int action ;

    public ArrayList<Product> getProductsOnStream(){
        // len -1
        ArrayList<Product> products = undoAction.pop();
        return (ArrayList<Product>) products.stream().filter(
                product -> product.getDeleteRow() == 1
        ).collect(Collectors.toList());
    }

    public static final int DELETE_ROW_ON_TABLE = 2;

    public static final int DELETE_ROW_ON_TABLE_SEARCH  = 1;

    public ProductConfig(Stack<ArrayList<Product>> undoAction) {
        this.undoAction = undoAction;
    }

    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
