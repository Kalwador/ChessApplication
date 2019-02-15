import {GameModel} from "./game.model";
import {AccountModel} from "../../profile/account.model";

export class GamePvpModel extends GameModel {
    white: string;
    whitePlayer: AccountModel;
    black: string;
    blackPlayer: AccountModel;
}