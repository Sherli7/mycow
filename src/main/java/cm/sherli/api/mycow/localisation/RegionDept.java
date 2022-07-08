package cm.sherli.api.mycow.localisation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Table(name="region")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionDept {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "region_generator")
    private int id;
    private String name;
    private String code;

    /*
    Adamaoua("Djérem", "Faro-et-Déo", "Mayo-Banyo", "Mbéré", "Vina"),
    Centre("Haute-Sanaga","Lekié","Mbam-et-Inoubou","Mbam-et-Kim","Méfou-et-Afamba","Méfou-et-Akono","Mfoundi","Nyong-et-Kéllé","Nyong-et-Mfoumou","Nyong-et-So'o"),
    Est("Boumba-et-Ngoko","Haut-Nyong","Kadey","Lom-et-Djérem"),
    Extrême_Nord,
    Littoral,
    Nord,
    Nord_Ouest,
    Ouest,
    Sud,
    Sud_Ouest,
    ;
    private final List<String> values;


    RegionDept(String ...values) {
        this.values = Arrays.asList(values);
    }

    public List<String> getRegDeptValues() {
        return values;
    }*/
}
