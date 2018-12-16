import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TimeType} from "../../../../../models/chess/time-type.enum";
import {Observable} from "rxjs";

@Component({
    selector: 'app-time-block',
    templateUrl: './time-block.component.html',
    styleUrls: ['./time-block.component.scss']
})
export class TimeBlockComponent implements OnInit {

    @Input() timeType: TimeType = null;
    @Input() activeBlockReciver: Observable<TimeType>;
    @Output() emiter: EventEmitter<TimeType> = new EventEmitter();
    isActive: boolean = false;
    name: string = '';

    constructor() {
    }

    ngOnInit() {
        this.activeBlockReciver.subscribe((block: TimeType) => this.collectActive(block))
        this.name = this.getNameForTimeType(this.timeType);
        if(this.timeType === TimeType.NON) {
            this.isActive = true;
        }
    }

    onClick(){
        // this.isActive = true;
        // this.emiter.emit(this.timeType);
    }

    private getNameForTimeType(timeType: TimeType) {
        switch (timeType) {
            case TimeType.M1: {
                return '1 min';
            }
            case TimeType.M2: {
                return '2 min';
            }
            case TimeType.M3: {
                return '3 min';
            }
            case TimeType.M5: {
                return '5 min';
            }
            case TimeType.M10: {
                return '10 min';
            }
            case TimeType.M15: {
                return '15 min';
            }
            case TimeType.M30: {
                return '30 min';
            }
            case TimeType.H1: {
                return '1 godz';
            }
            case TimeType.H3: {
                return '3 godz';
            }
            case TimeType.D1: {
                return '1 dzień';
            }
            case TimeType.D3: {
                return '3 dni';
            }
            case TimeType.NON: {
                return 'non';
            }
            default: {
                console.log('!!! BARDZO ZLE - nic nie pasuje, time type = ' + timeType);
                return '';
            }
        }
    }

    private collectActive(block: TimeType) {
        if(this.timeType !== block){
            this.isActive = false;
        }
    }
}
