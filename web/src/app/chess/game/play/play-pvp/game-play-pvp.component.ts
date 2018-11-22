import { Component, OnInit } from '@angular/core';


declare function startSocket(): any;

@Component({
  selector: 'app-play-pvp',
  templateUrl: './game-play-pvp.component.html',
  styleUrls: ['./game-play-pvp.component.css']
})
export class GamePlayPvpComponent implements OnInit {

    constructor(){ }

    ngOnInit() {
        startSocket();
    }

    sendMessage() {

    }

}
