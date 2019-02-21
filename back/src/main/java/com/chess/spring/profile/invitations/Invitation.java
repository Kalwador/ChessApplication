package com.chess.spring.profile.invitations;

import com.chess.spring.game.pvp.GamePvP;
import com.chess.spring.profile.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invitations")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private GamePvP game;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @PreRemove
    private void onRemove(){
        this.game.setInvitation(null);
        this.game = null;
        for(Invitation inv : this.account.getInvitations()){
            if (inv.id == id){
                this.account.getInvitations().remove(inv);
            }
        }
        this.account = null;
    }
}
