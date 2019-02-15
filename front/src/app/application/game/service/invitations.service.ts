import {Injectable} from "@angular/core";
import {AppService} from "../../../services/app.service";
import {Observable} from "rxjs";
import {PageModel} from "../../../models/application/page.model";

@Injectable({
    providedIn: 'root'
})
export class InvitationsService {
    public path = '/invitations';

    constructor(private appService: AppService) {
    }

    public getInvitations(page: number, listSize: number): Observable<PageModel> {
        let paging = this.appService.getPaging(page, listSize);
        return this.appService.get(this.path + paging);
    }

    public checkNick(nick:string): Observable<any> {
        return this.appService.getText(this.path + "/check/" + nick);
    }

    public sendInvitation(id: number, nick: string): Observable<any> {
        return this.appService.post(this.path + "/send/" + id + "/" + nick);
    }

    public accept(id: number): Observable<any> {
        return this.appService.post(this.path + '/accept/' + id);
    }

    public decline(id: number): Observable<any> {
        return this.appService.post(this.path + '/decline/' + id);

    }
}