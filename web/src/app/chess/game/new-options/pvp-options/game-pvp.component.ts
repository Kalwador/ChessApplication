import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
    selector: 'app-game-pvp',
    templateUrl: './game-pvp.component.html',
    styleUrls: ['./game-pvp.component.css']
})
export class GamePvpComponent implements OnInit {

    constructor(private router: Router) {
    }

    ngOnInit() {
    }

    submit() {
        this.router.navigate(['/game/play/pvp', 0]);
    }
}
