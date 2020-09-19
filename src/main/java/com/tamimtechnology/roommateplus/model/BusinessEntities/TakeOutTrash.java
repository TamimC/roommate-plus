package com.tamimtechnology.roommateplus.model.BusinessEntities;

import com.tamimtechnology.roommateplus.model.BusinessEntities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class TakeOutTrash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User takeoutUser;
    private Date takeoutTime;

    public TakeOutTrash(User takeoutUser, Date takeoutTime) {
        this.takeoutUser = takeoutUser;
        this.takeoutTime = takeoutTime;
    }
}
