export class GamePvE {
    gameId: number;
    color: string;
    level: number;
    board: string;
    moves: string;
    status: string;

    constructor (color: string, level: number) {
        this.color = color;
        this.level = level;
    }
}