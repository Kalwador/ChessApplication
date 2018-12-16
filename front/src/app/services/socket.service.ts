import {Injectable, OnInit} from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class SocketService implements OnInit {

    constructor() {
    }

    ngOnInit() {
    }
}


// <!--TODO
// service would be initialized on enter,
// and listen to chat for all
//
// on login action, service would start to subscribe:
// - direct messages,
// - user moves in pvp-games,
// - invitations
//
// service must contains:
// - list of actual subscribed sockets ,
// - methods to subscribe to new games & unsubscribe2
//
// also pvp-component need rework to use this service
// -->
