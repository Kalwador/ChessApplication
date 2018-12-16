export class Move {
    source: number;
    destination: number;
    type: string;
    status: string;
    moveLog: string;
    isInCheck: boolean;

    constructor(source: number, destination: number, type: string, status: string, moveLog: string, isInCheck: boolean) {
        this.source = source;
        this.destination = destination;
        this.type = type;
        this.status = status;
        this.moveLog = moveLog;
        this.isInCheck = isInCheck;
    }
}
