import {GamePvpModel} from "../chess/game/game-pvp-model";
import {AccountModel} from "../profile/account.model";

export class InvitationModel {
    game: GamePvpModel;
    account: AccountModel;
}