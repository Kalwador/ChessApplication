import {Game} from "./game.model";

export class GamePve extends Game{
    color: string;
    level: number;

    constructor (color: string, level: number) {
        super();
        this.color = color;
        this.level = level;
    }
}