package Model;

import lombok.*;

import java.util.Date;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
public class OderDetail {
    private int idUser;
    private String productName;
    private int quality;
    private boolean isPayment;
    private Date oderDate;
    private Date shipDate;

    public OderDetail(int idUser, String productName, int quality, boolean isPayment, Date oderDate, Date shipDate) {
        this.idUser = idUser;
        this.productName = productName;
        this.quality = quality;
        this.isPayment = isPayment;
        this.oderDate = oderDate;
        this.shipDate = shipDate;
    }
}
