import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {GameService} from "../service/game.service";
import {NotificationService} from "../../notifications/notification.service";
import {GamePvP} from "../../../models/game/game-pvp";
import {GamePvE} from "../../../models/game/game-pv-e";

@Component({
    selector: 'app-games',
    templateUrl: './game-list.component.html',
    styleUrls: ['./game-list.component.scss']
})
export class GameListComponent implements OnInit {


    gamesPvPList: Array<GamePvP>;
    gamesPvEList: Array<GamePvE>;

    currentPvPPage: number = 0;
    currentPvEPage: number = 0;

    defaultSize: number = 3;

    displayedColumns: string[] = ['position', 'name', 'weight', 'symbol'];
    dataSource = ELEMENT_DATA;

    constructor(private router: Router,
                private service: GameService,
                private notif: NotificationService) {

        this.loadPvPGamesLists(this.currentPvPPage);
        this.loadPvEGamesLists(this.currentPvEPage);

    }

    ngOnInit() {
    }

    newGame() {
        this.router.navigate(['/game/new-options']);
    }

    loadPvPGamesLists(page: number) {
        this.service.getPvPList(page, this.defaultSize).subscribe(data => {
            this.gamesPvPList = data.content;
            console.log(this.gamesPvPList);
        }, error => {
            this.notif.trace(error);
        });
    }

    loadPvEGamesLists(page: number) {
        this.service.getPvEList(page, this.defaultSize).subscribe(data => {
            this.gamesPvEList = data.content;
            console.log(this.gamesPvEList);
        }, error => {
            this.notif.trace(error);
        });
    }


}

const ELEMENT_DATA: PeriodicElement[] = [
    {position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
    {position: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
    {position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
    {position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
    {position: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
    {position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
    {position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
    {position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
    {position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
    {position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
];


export interface PeriodicElement {
    name: string;
    position: number;
    weight: number;
    symbol: string;
}