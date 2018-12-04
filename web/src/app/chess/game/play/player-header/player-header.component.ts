import {Component, Input, OnInit} from '@angular/core';

@Component({
    selector: 'player-header',
    templateUrl: './player-header.component.html',
    styleUrls: ['./player-header.component.scss']
})
export class PlayerHeaderComponent implements OnInit {

    @Input() data: string;

    constructor() {
    }

    ngOnInit() {
    }

}
