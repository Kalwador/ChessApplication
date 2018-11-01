export class Move {
    source: number;
    destination: number;
    type: string;

    constructor(source: number, destination: number, type: string){
        this.source = source;
        this.destination = destination;
        this.type = type;
    }
}