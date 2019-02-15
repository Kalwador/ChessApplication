import {GameModel} from "./game.model";

export class GamePveModel extends GameModel{
    color: string;
    level: number;

    constructor (color: string, level: number) {
        super();
        this.color = color;
        this.level = level;
    }
}