package Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Getter
@Setter
public class ComputerManager {
    public static String COMPUTER_MANAGER="ComputerManager";
    private ArrayList<Computer> computers;

    public ComputerManager() {
        this.computers = new ArrayList<>();
        computers.add(new Computer("LP13", "Laptop HP 15s-fq2663TU",200 , 9990000L, "Laptop","usa","window", "Intel Core i3 1115G4", 256, "4 GB", "China"));
        computers.add(new Computer("LP14", "Laptop Lenovo IdeaPad 5 Pro 16IAH7",500, 22490000L, "Laptop", "China","Window", "Intel Core i5 12500H", 512, "16 GB", "Vietnam"));
        computers.add(new Computer("LP15", "Laptop Lenovo IdeaPad 5 Pro 16IAH7",75, 25390000L, "Laptop", "China","Window", "Intel Core i7 12700H", 512, "16 GB", "Vietnam"));
        computers.add(new Computer("LP16", "Laptop MSI Gaming Alpha 15",300, 21990000L, "Laptop","China","Window", "Intel Core i5 12450H", 512, "8 GB", "Vietnam"));
        computers.add(new Computer("LP17", "Laptop MSI Gaming Katana GF66",650, 21590000L, "Laptop","China","Window", "Intel Core i5 12500H", 512, "8 GB", "Vietnam"));
        computers.add(new Computer("LP18", "Laptop HP Gaming Victus 16",120, 19490000L, "Laptop","China","Window", "AMD Ryzen 5 5600H", 512, "8 GB", "Vietnam"));
        computers.add(new Computer("LP19", "Laptop MSI GF63 Thin 11UC",320, 15990000L, "Laptop","China","Window", "Intel Core i5 11400H", 512, "8 GB", "Vietnam"));
        computers.add(new Computer("LP20", "Laptop ASUS TUF Gaming FX516PE",650, 23990000L, "Laptop","China","Window", "Intel Core i5 12450H", 512, "8 GB", "Vietnam"));
        computers.add(new Computer("LP21", "Laptop Lenovo Yoga Slim 7",320 , 29990000L, "Laptop","China","Window", "Intel Core i5 12450H", 512, "16 GB", "Vietnam"));
        computers.add(new Computer("LP22", "Laptop Lenovo IdeaPad U4",390, 20990000L, "Laptop","China","Window", "Intel Core i5 12400H", 512, "16 GB", "Vietnam"));
        computers.add(new Computer("LP23", "Laptop Dell Vostro 5410",123, 13190000L, "Laptop","China","Window", "Intel Core i5 12500H", 512, "8 GB", "Vietnam"));
        computers.add(new Computer("LP24", "Laptop Dell Vostro 3510",432, 14190000L, "Laptop","China","Window", "Intel Core i3 1135G7", 256, "4 GB", "Vietnam"));
        computers.add(new Computer("LP25", "Laptop Acer Nitro 5",763, 33990000L, "Laptop","China","Window", "Intel Core i5 11400H", 512, "8 GB", "Vietnam"));
        computers.add(new Computer("LP26", "Laptop Lenovo ThinkPad E14",653, 21490000L, "Laptop","China","Window", "Intel Core i5 11300H", 512, "16 GB", "Vietnam"));
        computers.add(new Computer("PC1", "PC E-Power Office 01",672, 6199000L, "PC/Case", "China","Window","Intel Core i5 11400", 240, "16 GB", "Vietnam"));
        computers.add(new Computer("PC2", "PC E-Power Office 02",520, 7699000L, "PC/Case","China","Window", "Intel Core i5 10400", 240, "8 GB", "Vietnam"));
        computers.add(new Computer("PC3", "PC E-Power Office 03",340, 2590000L, "PC/Case","China","Window", "Ryzen 7 5800H", 512, "8 GB", "Vietnam"));
        computers.add(new Computer("PC4", "PC Gaming E-Power G1650",542, 8999000L, "PC/Case","China","Window", "Intel Core i5 11400H", 512, "16 GB", "Vietnam"));
        computers.add(new Computer("PC5", "PC E-Power Office 06",320, 9199000L, "PC/Case","China","Window", "Intel Core i5 11400H", 240, "16 GB", "Vietnam"));
        computers.add(new Computer("PC6", "PC Acer Aspire AS-XC7C07",530, 11120000L, "PC/Case","China","Window", "Intel Core i5 7400", 1000, "4 GB", "Vietnam"));

    }

    public boolean setQuanlityByProductCode(String code, int qualityBought ){
        for (int i = 0; i < this.computers.size(); i++) {
            if (computers.get(i).equalProductCode(code)){
                int qualityNow = computers.get(i).getQuality();
                computers.get(i).setQuality(qualityNow - qualityBought);
                return  true;
            }

        }
        return false;
    }

    public ArrayList<Computer> getAll() {
        return computers;
    }

    public ArrayList<Computer> findByName(String name ){

        ArrayList<Computer> computersFound  = (ArrayList<Computer>) computers.stream()
                .filter(computer -> computer.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());


        return computersFound;
    }

    private void printAll(){
        for( Computer c : this.computers)
            System.out.println(c);
    }




}
