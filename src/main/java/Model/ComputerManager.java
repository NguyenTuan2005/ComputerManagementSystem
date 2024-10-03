package Model;

import Config.ExcelConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Getter
@Setter
public class ComputerManager {
    public static String COMPUTER_MANAGER = "ComputerManager";
    public static ArrayList<Computer> computers;

    public ComputerManager() {
        this.computers = new ArrayList<>();
        computers.add(new Computer("LP13", "Laptop HP 15s-fq2663TU", 200, 9990000L, "Laptop", "usa", "window", "Intel Core i3 1115G4", 256, "4 GB", "China"));
        computers.add(new Computer("LP14", "Laptop Lenovo IdeaPad 5 Pro 16IAH7", 500, 22490000L, "Laptop", "China", "Window", "Intel Core i5 12500H", 512, "16 GB", "Vietnam"));
        computers.add(new Computer("LP15", "Laptop Lenovo IdeaPad 5 Pro 16IAH7", 75, 25390000L, "Laptop", "China", "Window", "Intel Core i7 12700H", 512, "16 GB", "Vietnam"));
        computers.add(new Computer("LP16", "Laptop MSI Gaming Alpha 15", 300, 21990000L, "Laptop", "China", "Window", "Intel Core i5 12450H", 512, "8 GB", "Vietnam"));
        computers.add(new Computer("LP17", "Laptop MSI Gaming Katana GF66", 650, 21590000L, "Laptop", "China", "Window", "Intel Core i5 12500H", 512, "8 GB", "Vietnam"));
        computers.add(new Computer("LP18", "Laptop HP Gaming Victus 16", 120, 19490000L, "Laptop", "China", "Window", "AMD Ryzen 5 5600H", 512, "8 GB", "Vietnam"));
        computers.add(new Computer("LP19", "Laptop MSI GF63 Thin 11UC", 320, 15990000L, "Laptop", "China", "Window", "Intel Core i5 11400H", 512, "8 GB", "Vietnam"));
        computers.add(new Computer("LP20", "Laptop ASUS TUF Gaming FX516PE", 650, 23990000L, "Laptop", "China", "Window", "Intel Core i5 12450H", 512, "8 GB", "Vietnam"));
        computers.add(new Computer("LP21", "Laptop Lenovo Yoga Slim 7", 320, 29990000L, "Laptop", "China", "Window", "Intel Core i5 12450H", 512, "16 GB", "Vietnam"));
        computers.add(new Computer("LP22", "Laptop Lenovo IdeaPad U4", 390, 20990000L, "Laptop", "China", "Window", "Intel Core i5 12400H", 512, "16 GB", "Vietnam"));
        computers.add(new Computer("LP23", "Laptop Dell Vostro 5410", 123, 13190000L, "Laptop", "China", "Window", "Intel Core i5 12500H", 512, "8 GB", "Vietnam"));
        computers.add(new Computer("LP24", "Laptop Dell Vostro 3510", 432, 14190000L, "Laptop", "China", "Window", "Intel Core i3 1135G7", 256, "4 GB", "Vietnam"));
        computers.add(new Computer("LP25", "Laptop Acer Nitro 5", 763, 33990000L, "Laptop", "China", "Window", "Intel Core i5 11400H", 512, "8 GB", "Vietnam"));
        computers.add(new Computer("LP26", "Laptop Lenovo ThinkPad E14", 653, 21490000L, "Laptop", "China", "Window", "Intel Core i5 11300H", 512, "16 GB", "Vietnam"));
        computers.add(new Computer("PC1", "PC E-Power Office 01", 672, 6199000L, "PC/Case", "China", "Window", "Intel Core i5 11400", 240, "16 GB", "Vietnam"));
        computers.add(new Computer("PC2", "PC E-Power Office 02", 520, 7699000L, "PC/Case", "China", "Window", "Intel Core i5 10400", 240, "8 GB", "Vietnam"));
        computers.add(new Computer("PC3", "PC E-Power Office 03", 340, 2590000L, "PC/Case", "China", "Window", "Ryzen 7 5800H", 512, "8 GB", "Vietnam"));
        computers.add(new Computer("PC4", "PC Gaming E-Power G1650", 542, 8999000L, "PC/Case", "China", "Window", "Intel Core i5 11400H", 512, "16 GB", "Vietnam"));
        computers.add(new Computer("PC5", "PC E-Power Office 06", 320, 9199000L, "PC/Case", "China", "Window", "Intel Core i5 11400H", 240, "16 GB", "Vietnam"));
        computers.add(new Computer("PC6", "PC Acer Aspire AS-XC7C07", 530, 11120000L, "PC/Case", "China", "Window", "Intel Core i5 7400", 1000, "4 GB", "Vietnam"));

    }

    public boolean setQuanlityByProductCode(String productCode, int qualityBought) {
        for (int i = 0; i < this.computers.size(); i++) {
            if (computers.get(i).equalProductCode(productCode)) {
                int qualityNow = computers.get(i).getQuality();
                computers.get(i).setQuality(qualityNow - qualityBought);
                return true;
            }

        }
        return false;
    }

    public ArrayList<Computer> getAll() {
        return computers;
    }

    public ArrayList<Computer> findByName(String name) {

        ArrayList<Computer> computersFound = (ArrayList<Computer>) computers.stream()
                .filter(computer -> computer.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());


        return computersFound;
    }

    private void sortByName(int begin, int end) {
        String pivot = this.computers.get((end + begin) / 2).getName();
        int l = begin;
        int r = end;
        do {
            while (this.computers.get(l).getName().compareTo(pivot) < 0) {
                l++;
            }
            while (this.computers.get(r).getName().compareTo(pivot) > 0) {
                r--;
            }
            if (l <= r) {
                Computer that = computers.get(l);
                computers.set(l, computers.get(r));
                computers.set(r, that);
                l++;
                r--;
            }

        } while (l <= r);

        if (l < end) {
            sortByName(l, end);
        }
        if (begin < r) {
            sortByName(begin, r);
        }
    }

    private void sortByPrice(int begin, int end) {
        Long pivot = this.computers.get((end + begin) / 2).getPrice();
        int l = begin;
        int r = end;
        do {
            while (this.computers.get(l).getPrice() < pivot) {
                l++;
            }
            while (this.computers.get(r).getPrice() > pivot) {
                r--;
            }
            if (l <= r) {
                Computer that = computers.get(l);
                computers.set(l, computers.get(r));
                computers.set(r, that);
                l++;
                r--;
            }

        } while (l <= r);

        if (l < end) {
            sortByPrice(l, end);
        }
        if (begin < r) {
            sortByPrice(begin, r);
        }
    }

    private void sortByRam(int begin, int end) {
        String pivot = this.computers.get((end + begin) / 2).getRam();
        int l = begin;
        int r = end;
        do {
            while (this.computers.get(l).getRam().compareTo(pivot) < 0) {
                l++;
            }
            while (this.computers.get(r).getRam().compareTo(pivot) > 0) {
                r--;
            }
            if (l <= r) {
                Computer that = computers.get(l);
                computers.set(l, computers.get(r));
                computers.set(r, that);
                l++;
                r--;
            }

        } while (l <= r);

        if (l < end) {
            sortByRam(l, end);
        }
        if (begin < r) {
            sortByRam(begin, r);
        }
    }

    public void sortByRam() {
        this.sortByRam(0, this.computers.size() - 1);
    }


    public void sortByName() {
        this.sortByName(0, this.computers.size() - 1);
    }

    public void sortByPrice() {
        this.sortByPrice(0, this.computers.size() - 1);
    }

    public boolean add(Computer computer) {
        for (Computer foundComputer : this.findByName(computer.getName())) {
            if (foundComputer.equals(foundComputer)) {
                return false;
            }
        }
        this.computers.add(computer);
        return true;
    }

    private void printAll() {
        for (Computer c : this.computers)
            System.out.println(c);
    }

    public static void main(String[] args) {
        ComputerManager c = new ComputerManager();
        ExcelConfig excelConfig = new ExcelConfig();
//        excelConfig.readExcelFile("E:\\ComputerManagementSystem\\ComputerManagementSystem\\Computer2024-10-03--03-58-53.xlsx");
        excelConfig.createFileExcel(c.getAll());
        c.printAll();
    }


}
