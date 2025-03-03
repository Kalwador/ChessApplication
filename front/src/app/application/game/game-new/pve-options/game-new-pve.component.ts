import {Component, OnInit} from '@angular/core';
import {GameService} from '../../service/game.service';
import {Router} from '@angular/router';
import {coerceNumberProperty} from '@angular/cdk/coercion';
import {GamePveModel} from "../../../../models/chess/game/game-pve-model";

@Component({
    selector: 'app-game-new-pve',
    templateUrl: './game-new-pve.component.html',
    styleUrls: ['./game-new-pve.component.scss']
})
export class GameNewPveComponent {

    colors: Array<string>;
    choosedColor = 'RANDOM';

    autoTicks = false;
    disabled = false;
    invert = false;
    max = 5;
    min = 1;
    showTicks = true;
    step = 1;
    thumbLabel = true;
    value = 2;
    vertical = false;
    private _tickInterval = 1;

    constructor(private gameService: GameService,
                private router: Router) {

        this.colors = ['WHITE', 'BLACK', 'RANDOM'];
        this.choosedColor = 'RANDOM';
    }

    submit() {
        this.gameService.newPvE(new GamePveModel(this.choosedColor, this.value)).subscribe(value => {
            this.router.navigate(['/game/play/pve', value]);
        });
    }

    public chooseColor(color: string) {
        this.choosedColor = color;
    }

    /**
     * Used in handle slider ticks
     */
    get tickInterval(): number | 'auto' {
        return this.showTicks ? (this.autoTicks ? 'auto' : this._tickInterval) : 0;
    }

    set tickInterval(value) {
        this._tickInterval = coerceNumberProperty(value);
    }
}
