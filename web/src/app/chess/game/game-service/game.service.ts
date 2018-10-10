import {Injectable} from '@angular/core';
import {BaseService} from '../../../services/base.service';
import {map} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class GameService {

    pathPvE = '/game/pve';

    constructor(private baseService: BaseService) {
    }

    newPvE(color: number, level: number) {
        // this.baseService.get(this.pathPvE + '/new/' + color + '/' + level).pipe(map(response => {
        //     response.json().color
        // ));

    }
}
