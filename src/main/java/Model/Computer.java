package Model;

import lombok.*;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Computer {

    private String productCode;

    private String name;

    private int quality;

    private Long price;

    private String style;

    private String brand;

    private String operatingSystem;

    private String cpu;

    private int memory;

    private String ram;

    private String madeIn;

    public static String PRODUCT_CODE ="ProductCode";
    public static String NAME ="Name";
    public static String QUALITY ="Quality";
    public static String PRICE ="Price";
    public static String STYLE ="Style";
    public static String BRAND ="Brand";
    public static String OPERATING_SYSTEM ="Operating System";
    public static String CPU ="Cpu";
    public static String MEMORY ="Memory";
    public static String RAM ="Ram";
    public static String MADE_IN="Made In";


    public boolean equalProductCode(String code){
        return this.productCode.equals(code);
    }

    public String[] getAllData(){
        return new String[]{this.productCode, this.name, String.valueOf(this.quality), String.valueOf(this.price), this.style, this.brand, this.operatingSystem,this.cpu,String.valueOf(memory), this.ram, this.madeIn};
    }
}
