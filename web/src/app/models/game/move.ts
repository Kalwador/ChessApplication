export class Move {
    source: number;
    destination: number;
    type: string;
    moveLog: string;

    constructor(source: number, destination: number, type: string, moveLog: string) {
        this.source = source;
        this.destination = destination;
        this.type = type;
        this.moveLog = moveLog;
    }
}